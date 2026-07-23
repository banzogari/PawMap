package com.pawmap.app.ui.trip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import com.pawmap.app.data.entity.PlaceEntity
import kotlinx.coroutines.launch

class AddPlaceViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PawRepository.get(app)

    private val _places = MutableLiveData<List<PlaceEntity>>()
    val places: LiveData<List<PlaceEntity>> = _places

    private var all: List<PlaceEntity> = emptyList()

    init {
        viewModelScope.launch {
            all = repo.getAllPlaces()
            _places.value = all
        }
    }

    fun filter(query: String) {
        val q = query.trim()
        _places.value = if (q.isEmpty()) all
        else all.filter { it.name.contains(q) || it.category.contains(q) || it.region.contains(q) }
    }

    fun addToTrip(tripId: Long, dayIndex: Int, placeId: Long, onDone: () -> Unit) {
        viewModelScope.launch {
            repo.addPlaceToTripDay(tripId, dayIndex, placeId)
            onDone()
        }
    }
}
