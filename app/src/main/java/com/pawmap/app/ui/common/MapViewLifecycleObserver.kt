package com.pawmap.app.ui.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.naver.maps.map.MapView
import com.naver.maps.map.OnMapReadyCallback

/**
 * Forwards a Fragment's view lifecycle to a Naver [MapView].
 *
 * IMPORTANT: pass [onMapReady] here instead of calling mapView.getMapAsync(...)
 * yourself. getMapAsync must run AFTER MapView.onCreate(), otherwise the ready
 * callback can be dropped and you never receive the NaverMap. This observer
 * calls getMapAsync right after onCreate to guarantee the correct order.
 *
 *   viewLifecycleOwner.lifecycle.addObserver(
 *       MapViewLifecycleObserver(binding.mapView) { map -> ... }
 *   )
 */
class MapViewLifecycleObserver(
    private val mapView: MapView,
    private val onMapReady: OnMapReadyCallback? = null
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        mapView.onCreate(null)
        onMapReady?.let { mapView.getMapAsync(it) }
    }
    override fun onStart(owner: LifecycleOwner) = mapView.onStart()
    override fun onResume(owner: LifecycleOwner) = mapView.onResume()
    override fun onPause(owner: LifecycleOwner) = mapView.onPause()
    override fun onStop(owner: LifecycleOwner) = mapView.onStop()
    override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
}
