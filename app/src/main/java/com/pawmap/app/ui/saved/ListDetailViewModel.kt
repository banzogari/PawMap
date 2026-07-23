package com.pawmap.app.ui.saved

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import com.pawmap.app.data.entity.PlaceEntity
import kotlinx.coroutines.launch

class ListDetailViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PawRepository.get(app)

    private val listId = MutableLiveData<Long>()
    val listName = MutableLiveData<String>()

    val places: LiveData<List<PlaceEntity>> = listId.switchMap { id ->
        repo.observePlacesInList(id).asLiveData()
    }

    fun load(id: Long) {
        listId.value = id
        viewModelScope.launch { listName.value = repo.getList(id)?.name ?: "" }
    }

    fun remove(placeId: Long) {
        val id = listId.value ?: return
        viewModelScope.launch { repo.removePlaceFromList(id, placeId) }
    }
}
