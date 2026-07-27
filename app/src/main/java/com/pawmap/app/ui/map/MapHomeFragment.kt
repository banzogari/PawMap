package com.pawmap.app.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
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
import com.pawmap.app.ui.search.SearchResultAdapter
import com.naver.maps.map.overlay.OverlayImage

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

        // Tapping the search bar opens the dedicated search screen.
        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_map_to_search)
        }

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

    // Category-chip filtering shows its results inline in the bottom sheet.
    private fun runSearch(query: String) {
        pendingFit = true
        vm.search(query)
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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
                // Figma Location Picker 핀 (카테고리별 아이콘 포함)
                icon = OverlayImage.fromResource(p.category().markerRes)
                width = dp(34)
                height = dp(46)
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