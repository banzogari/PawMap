package com.pawmap.app.ui.trip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import com.pawmap.app.data.entity.JournalEntity
import kotlinx.coroutines.launch

class JournalEditViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PawRepository.get(app)

    private val _journal = MutableLiveData<JournalEntity?>()
    val journal: LiveData<JournalEntity?> = _journal

    fun load(tripId: Long, dayIndex: Int) {
        viewModelScope.launch { _journal.value = repo.getJournal(tripId, dayIndex) }
    }

    fun save(tripId: Long, dayIndex: Int, photoUri: String?, memo: String?, onSaved: () -> Unit) {
        viewModelScope.launch {
            repo.saveJournal(
                tripId, dayIndex,
                photoUri,
                memo?.trim()?.takeIf { it.isNotEmpty() }
            )
            onSaved()
        }
    }
}
