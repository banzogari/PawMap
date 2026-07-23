package com.pawmap.app.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.pawmap.app.R
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.databinding.FragmentMapHomeBinding
import com.pawmap.app.ui.common.MapViewLifecycleObserver
import com.pawmap.app.ui.common.category
import com.pawmap.app.ui.search.PlaceRow
import com.pawmap.app.ui.search.SearchResultAdapter

class MapHomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: MapHomeViewModel by viewModels()

    private var naverMap: NaverMap? = null
    private lateinit var locationSource: FusedLocationSource
    private lateinit var sheetBehavior: BottomSheetBehavior<*>
    private val markers = mutableListOf<Marker>()

    // Only recenter the camera when a new search runs, not on favorite toggles.
    private var pendingFit = false

    private val adapter = SearchResultAdapter(
        onClick = { placeId ->
            findNavController().navigate(R.id.action_map_to_detail, bundleOf("placeId" to placeId))
        },
        onFavorite = { placeId -> vm.toggleFavorite(placeId) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        viewLifecycleOwner.lifecycle.addObserver(MapViewLifecycleObserver(binding.mapView))
        binding.mapView.getMapAsync(this)

        NaverMapSdk.getInstance(requireContext()).setOnAuthFailedListener { ex ->
            Toast.makeText(
                requireContext(),
                "지도 인증 실패: local.properties의 NAVER_MAP_CLIENT_ID를 확인하세요\n(${ex.message})",
                Toast.LENGTH_LONG
            ).show()
        }

        setupSheet()

        vm.rows.observe(viewLifecycleOwner) { rows ->
            adapter.submitList(rows)
            binding.tvEmpty.visibility = if (rows.isEmpty()) View.VISIBLE else View.GONE
            renderMarkers(rows.map { it.place }, moveCamera = pendingFit)
            pendingFit = false
        }

        binding.etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                runSearch(v.text?.toString().orEmpty()); true
            } else false
        }
        binding.etSearch.doAfterTextChanged {
            binding.btnClear.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
        binding.btnClear.setOnClickListener { clearSearch() }

        binding.chipAll.setOnClickListener { runSearch("") }
        binding.chipCafe.setOnClickListener { runSearch(getString(R.string.cat_cafe)) }
        binding.chipFood.setOnClickListener { runSearch(getString(R.string.cat_food)) }
        binding.chipStay.setOnClickListener { runSearch(getString(R.string.cat_stay)) }
        binding.chipTravel.setOnClickListener { runSearch(getString(R.string.cat_travel)) }

        binding.btnLocation.setOnClickListener {
            naverMap?.locationTrackingMode = LocationTrackingMode.Follow
        }
    }

    private fun setupSheet() {
        binding.searchRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecycler.adapter = adapter
        sheetBehavior = BottomSheetBehavior.from(binding.searchSheet)
        sheetBehavior.isHideable = true
        // Rise to about half the screen; user can still drag it higher.
        sheetBehavior.peekHeight = resources.displayMetrics.heightPixels / 2
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun runSearch(query: String) {
        if (binding.etSearch.text?.toString() != query) binding.etSearch.setText(query)
        binding.etSearch.setSelection(binding.etSearch.text?.length ?: 0)
        hideKeyboard()
        pendingFit = true
        vm.search(query)
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun clearSearch() {
        binding.etSearch.setText("")
        binding.chipAll.isChecked = true
        hideKeyboard()
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        pendingFit = true
        vm.search("") // restore all markers
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        map.locationSource = locationSource
        map.uiSettings.isLocationButtonEnabled = false
        map.uiSettings.isZoomControlEnabled = false
        pendingFit = true
        renderMarkers(vm.rows.value?.map { it.place } ?: emptyList(), moveCamera = true)
        pendingFit = false
    }

    override fun onResume() {
        super.onResume()
        vm.refresh() // reflect favorite changes made on other screens
    }

    private fun renderMarkers(places: List<PlaceEntity>, moveCamera: Boolean) {
        val map = naverMap ?: return
        markers.forEach { it.map = null }
        markers.clear()
        if (places.isEmpty()) return

        val boundsBuilder = LatLngBounds.Builder()
        for (p in places) {
            val pos = LatLng(p.lat, p.lng)
            val marker = Marker().apply {
                position = pos
                iconTintColor = ContextCompat.getColor(requireContext(), p.category().colorRes)
                captionText = p.name
                tag = p.id
                this.map = map
                setOnClickListener {
                    findNavController().navigate(
                        R.id.action_map_to_detail, bundleOf("placeId" to p.id)
                    )
                    true
                }
            }
            markers.add(marker)
            boundsBuilder.include(pos)
        }
        if (moveCamera) {
            if (places.size == 1) {
                map.moveCamera(CameraUpdate.scrollAndZoomTo(LatLng(places[0].lat, places[0].lng), 13.0))
            } else {
                map.moveCamera(CameraUpdate.fitBounds(boundsBuilder.build(), dp(64)))
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        binding.etSearch.clearFocus()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap?.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        markers.clear()
        naverMap = null
        _binding = null
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
