package com.pawmap.app.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.pawmap.app.R
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.databinding.FragmentTripDetailBinding
import com.pawmap.app.databinding.ItemTripPlaceBinding
import com.pawmap.app.ui.common.MapViewLifecycleObserver
import com.pawmap.app.ui.common.category
import com.pawmap.app.util.DateUtils
import android.widget.Toast

class TripDetailFragment : Fragment() {

    private var _binding: FragmentTripDetailBinding? = null
    private val binding get() = _binding!!
    private val vm: TripDetailViewModel by viewModels()

    private var tripId = -1L
    private var tabsBuilt = false

    private var naverMap: NaverMap? = null
    private val mapMarkers = mutableListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripDetailBinding.inflate(inflater, container, false)
        // View가 재생성될 때(다른 화면 갔다 복귀 등)마다 Day 탭을 다시 그리도록 리셋
        tabsBuilt = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tripId = arguments?.getLong("tripId") ?: -1L
        vm.load(tripId)

        // getMapAsync must run after MapView.onCreate — the observer guarantees that.
        viewLifecycleOwner.lifecycle.addObserver(
            MapViewLifecycleObserver(binding.mapView) { map ->
                naverMap = map
                // Mini-map: no gestures so the surrounding scroll view isn't hijacked.
                map.uiSettings.apply {
                    isScrollGesturesEnabled = false
                    isZoomGesturesEnabled = false
                    isTiltGesturesEnabled = false
                    isRotateGesturesEnabled = false
                    isStopGesturesEnabled = false
                    isZoomControlEnabled = false
                    isLocationButtonEnabled = false
                }
                map.moveCamera(CameraUpdate.scrollTo(LatLng(37.5666, 126.9784)))
                renderMapMarkers(vm.dayPlaces.value ?: emptyList())
            }
        )

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnSave.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.trip_saved), Toast.LENGTH_SHORT).show()
        }

        binding.btnDeleteTrip.setOnClickListener { confirmDeleteTrip() }

        binding.btnAddPlace.setOnClickListener {
            findNavController().navigate(
                R.id.action_tripDetail_to_addPlace,
                bundleOf("tripId" to tripId, "dayIndex" to (vm.selectedDay.value ?: 0))
            )
        }

        vm.trip.observe(viewLifecycleOwner) { trip -> trip?.let { bindTrip(it) } }
        vm.selectedDay.observe(viewLifecycleOwner) { updateDayDate(it) }
        vm.dayPlaces.observe(viewLifecycleOwner) { renderDayPlaces(it) }
    }

    private fun bindTrip(trip: TripEntity) {
        binding.tvTitle.text = trip.name
        binding.tvDates.text = DateUtils.formatRange(trip.startDate, trip.endDate)
        val pets = trip.petNames.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        binding.tvPets.text = if (pets.isEmpty()) "" else pets.joinToString(", ") + "와"
        binding.tvPets.visibility = if (pets.isEmpty()) View.GONE else View.VISIBLE
        buildDayTabs(trip)
        updateDayDate(vm.selectedDay.value ?: 0)
    }

    private fun buildDayTabs(trip: TripEntity) {
        if (tabsBuilt) return
        tabsBuilt = true
        val count = DateUtils.dayCount(trip.startDate, trip.endDate)
        val tabs = binding.dayTabs
        tabs.removeAllTabs()
        for (i in 1..count) {
            tabs.addTab(tabs.newTab().setText("Day $i"))
        }
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) = vm.setDay(tab.position)
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun updateDayDate(dayIndex: Int) {
        val trip = vm.trip.value ?: return
        binding.tvDayDate.text = DateUtils.formatDayWithWeekday(
            DateUtils.dayStart(trip.startDate, dayIndex)
        )
    }

    private fun renderDayPlaces(items: List<DayPlace>) {
        binding.dayContainer.removeAllViews()
        binding.emptyCard.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

        for (item in items) {
            val ib = ItemTripPlaceBinding.inflate(layoutInflater, binding.dayContainer, false)
            ib.tvNumber.text = item.number.toString()
            ib.tvName.text = item.place.name
            ib.tvCategory.text = item.place.category
            // Card tap -> place detail; long-press to remove from the day.
            ib.card.setOnClickListener {
                findNavController().navigate(
                    R.id.placeDetailFragment, bundleOf("placeId" to item.place.id)
                )
            }
            ib.card.setOnLongClickListener {
                confirmRemove(item)
                true
            }
            ib.btnJournal.setOnClickListener {
                openJournal(vm.selectedDay.value ?: 0, item.place.name)
            }
            binding.dayContainer.addView(ib.root)
        }

        renderMapMarkers(items)
    }

    private fun renderMapMarkers(items: List<DayPlace>) {
        val map = naverMap ?: return
        mapMarkers.forEach { it.map = null }
        mapMarkers.clear()
        if (items.isEmpty()) return

        val boundsBuilder = LatLngBounds.Builder()
        for (dp in items) {
            val pos = LatLng(dp.place.lat, dp.place.lng)
            val marker = Marker().apply {
                position = pos
                // 카테고리별 핀 (지도 홈과 동일한 아이콘 세트)
                icon = OverlayImage.fromResource(dp.place.category().markerRes)
                width = dp(34)
                height = dp(46)
                captionText = dp.number.toString()
                this.map = map
            }
            mapMarkers.add(marker)
            boundsBuilder.include(pos)
        }
        if (items.size == 1) {
            map.moveCamera(CameraUpdate.scrollAndZoomTo(LatLng(items[0].place.lat, items[0].place.lng), 12.0))
        } else {
            map.moveCamera(CameraUpdate.fitBounds(boundsBuilder.build(), dp(40)))
        }
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    private fun openJournal(dayIndex: Int, title: String) {
        val trip = vm.trip.value
        val dateLabel = if (trip != null) {
            "Day ${dayIndex + 1} · " + DateUtils.formatDayWithWeekday(
                DateUtils.dayStart(trip.startDate, dayIndex)
            )
        } else "Day ${dayIndex + 1}"
        JournalEditDialogFragment.newInstance(tripId, dayIndex, title, dateLabel)
            .show(childFragmentManager, "journal")
    }

    private fun confirmRemove(item: DayPlace) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("장소 삭제")
            .setMessage("‘${item.place.name}’을(를) 일정에서 뺄까요?")
            .setPositiveButton(getString(R.string.delete)) { _, _ -> vm.removePlace(item.tripPlaceId) }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun confirmDeleteTrip() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_trip))
            .setMessage(getString(R.string.delete_trip_confirm))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                vm.deleteTrip {
                    Toast.makeText(requireContext(), getString(R.string.trip_deleted), Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack(R.id.tripMainFragment, false)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showRenameDialog() {
        val input = EditText(requireContext()).apply {
            setText(vm.trip.value?.name ?: "")
            setPadding(48, 32, 48, 32)
        }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("여행 이름 변경")
            .setView(input)
            .setPositiveButton(getString(R.string.done)) { _, _ ->
                vm.rename(input.text?.toString().orEmpty())
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapMarkers.clear()
        naverMap = null
        _binding = null
    }
}