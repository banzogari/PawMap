package com.pawmap.app.ui.trip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.data.entity.TripPlaceEntity
import kotlinx.coroutines.launch

data class DayPlace(val tripPlaceId: Long, val number: Int, val place: PlaceEntity)

class TripDetailViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PawRepository.get(app)

    private val tripIdLive = MutableLiveData<Long>()
    val trip = MutableLiveData<TripEntity?>()
    val selectedDay = MutableLiveData(0)

    private var placeMap: Map<Long, PlaceEntity> = emptyMap()

    private val rawPlaces: LiveData<List<TripPlaceEntity>> =
        tripIdLive.switchMap { repo.observeTripPlaces(it).asLiveData() }

    val dayPlaces = MediatorLiveData<List<DayPlace>>()

    init {
        dayPlaces.addSource(rawPlaces) { recompute() }
        dayPlaces.addSource(selectedDay) { recompute() }
    }

    fun load(id: Long) {
        tripIdLive.value = id
        viewModelScope.launch {
            trip.value = repo.getTrip(id)
            placeMap = repo.getAllPlaces().associateBy { it.id }
            recompute()
        }
    }

    private fun recompute() {
        val day = selectedDay.value ?: 0
        val raw = rawPlaces.value ?: emptyList()
        dayPlaces.value = raw.filter { it.dayIndex == day }
            .sortedBy { it.orderIndex }
            .mapIndexedNotNull { idx, tp ->
                placeMap[tp.placeId]?.let { DayPlace(tp.id, idx + 1, it) }
            }
    }

    fun setDay(index: Int) {
        if (selectedDay.value != index) selectedDay.value = index
    }

    fun removePlace(tripPlaceId: Long) {
        viewModelScope.launch { repo.removeTripPlace(tripPlaceId) }
    }

    fun rename(newName: String) {
        viewModelScope.launch {
            trip.value?.let {
                repo.renameTrip(it, newName)
                trip.value = repo.getTrip(it.id)
            }
        }
    }

    fun deleteTrip(onDone: () -> Unit) {
        viewModelScope.launch {
            trip.value?.let { repo.deleteTrip(it.id) }
            onDone()
        }
    }
}