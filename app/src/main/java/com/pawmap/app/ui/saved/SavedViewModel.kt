package com.pawmap.app.ui.saved

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import kotlinx.coroutines.launch

class SavedViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PawRepository.get(app)

    val lists = repo.observeLists().asLiveData()

    /** Returns false when the name is blank or already used. */
    fun createList(name: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(repo.createList(name))
        }
    }

    fun deleteList(id: Long) {
        viewModelScope.launch { repo.deleteList(id) }
    }
}
