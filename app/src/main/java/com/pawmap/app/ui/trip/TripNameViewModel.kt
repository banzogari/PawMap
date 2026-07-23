package com.pawmap.app.ui.trip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import kotlinx.coroutines.launch

class TripNameViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = PawRepository.get(app)

    fun createTrip(name: String, start: Long, end: Long, pets: List<String>, onCreated: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repo.createTrip(name, start, end, pets)
            onCreated(id)
        }
    }
}
