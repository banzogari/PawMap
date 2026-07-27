package com.pawmap.app.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import com.pawmap.app.ui.search.PlaceRow
import kotlinx.coroutines.launch

class MapHomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PawRepository.get(app)

    private val _rows = MutableLiveData<List<PlaceRow>>()
    val rows: LiveData<List<PlaceRow>> = _rows

    // Only one of these is active at a time; category filter (chips) takes
    // precedence over the free-text query when both are set.
    var query: String = ""
        private set
    private var activeCategoryType: String? = null

    init {
        search("") // populate markers with all places on first load
    }

    fun search(q: String) {
        query = q
        activeCategoryType = null
        refresh()
    }

    /** Filters by exact categoryType (e.g. "CAFE"), or clears the filter when [type] is null. */
    fun filterByCategory(type: String?) {
        activeCategoryType = type
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            val type = activeCategoryType
            val places = if (type != null) repo.searchByCategory(type) else repo.search(query)
            _rows.value = places.map { PlaceRow(it, repo.isFavorite(it.id)) }
        }
    }

    fun toggleFavorite(placeId: Long) {
        viewModelScope.launch {
            repo.toggleFavorite(placeId)
            refresh()
        }
    }
}