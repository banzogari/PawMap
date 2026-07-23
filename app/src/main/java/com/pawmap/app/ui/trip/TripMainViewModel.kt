package com.pawmap.app.ui.trip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.pawmap.app.data.PawRepository

class TripMainViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = PawRepository.get(app)
    val ongoingTrip = repo.observeOngoingTrip().asLiveData()
}
