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

        binding.chipAll.setOnClickListener { runSearch(null) }
        binding.chipCafe.setOnClickListener { runSearch("CAFE") }
        binding.chipFood.setOnClickListener { runSearch("FOOD") }
        binding.chipStay.setOnClickListener { runSearch("STAY") }
        binding.chipTravel.setOnClickListener { runSearch("TRAVEL") }
        binding.chipShop.setOnClickListener { runSearch("SHOP") }

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
    // null categoryType (전체) shows every place instead of filtering.
    private fun runSearch(categoryType: String?) {
        pendingFit = true
        vm.filterByCategory(categoryType)
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        map.locationSource = locationSource
        map.uiSettings.isLocationButtonEnabled = false
        map.uiSettings.isZoomControlEnabled = false
        // 항상 시청역 고정 위치로 시작 (저장된 장소 fitBounds 대신)
        map.moveCamera(CameraUpdate.scrollAndZoomTo(DEFAULT_CENTER, DEFAULT_ZOOM))
        renderMarkers(vm.rows.value?.map { it.place } ?: emptyList(), moveCamera = false)
    }

    override fun onResume() {
        super.onResume()
        vm.refresh() // reflect favorite changes made on other screens
        // 다른 화면 갔다가 돌아와도 항상 시청역으로 재고정
        naverMap?.moveCamera(CameraUpdate.scrollAndZoomTo(DEFAULT_CENTER, DEFAULT_ZOOM))
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
                // 줌 레벨 16 미만(광역 뷰)에서는 핀 숨김 — 핀 겹침 방지
                minZoom = 16.0
                isMinZoomInclusive = true
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
        // 서울시청역 좌표 — 화면 재진입 시 항상 이 위치로 복귀
        private val DEFAULT_CENTER = LatLng(37.5663, 126.9779)
        private const val DEFAULT_ZOOM = 15.0
    }
}