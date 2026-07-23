package com.pawmap.app.ui.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.naver.maps.map.MapView

/**
 * Forwards a Fragment's view lifecycle to a Naver [MapView] so hosting screens
 * don't have to override every lifecycle method by hand.
 *
 * Add it in onViewCreated with the view lifecycle owner:
 *   viewLifecycleOwner.lifecycle.addObserver(MapViewLifecycleObserver(mapView))
 */
class MapViewLifecycleObserver(private val mapView: MapView) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) = mapView.onCreate(null)
    override fun onStart(owner: LifecycleOwner) = mapView.onStart()
    override fun onResume(owner: LifecycleOwner) = mapView.onResume()
    override fun onPause(owner: LifecycleOwner) = mapView.onPause()
    override fun onStop(owner: LifecycleOwner) = mapView.onStop()
    override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
}
