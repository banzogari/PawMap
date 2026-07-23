package com.pawmap.app.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import com.pawmap.app.data.entity.PlaceEntity
import kotlinx.coroutines.launch

class PlaceDetailViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PawRepository.get(app)

    private val _place = MutableLiveData<PlaceEntity?>()
    val place: LiveData<PlaceEntity?> = _place

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    private var placeId: Long = -1

    fun load(id: Long) {
        placeId = id
        viewModelScope.launch {
            _place.value = repo.getPlace(id)
            _saved.value = repo.isSaved(id)
        }
    }

    fun toggleSaved() {
        viewModelScope.launch {
            _saved.value = repo.toggleSaved(placeId)
        }
    }

    sealed interface AddTripResult {
        data class Added(val tripName: String) : AddTripResult
        data object NoTrip : AddTripResult
    }

    fun addToOngoingTrip(onResult: (AddTripResult) -> Unit) {
        viewModelScope.launch {
            val trip = repo.getOngoingTrip()
            if (trip == null) {
                onResult(AddTripResult.NoTrip)
            } else {
                repo.addPlaceToTripDay(trip.id, 0, placeId)
                onResult(AddTripResult.Added(trip.name))
            }
        }
    }
}
