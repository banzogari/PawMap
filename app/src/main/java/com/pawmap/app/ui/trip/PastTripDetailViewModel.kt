package com.pawmap.app.ui.trip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pawmap.app.data.PawRepository
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.util.DateUtils
import kotlinx.coroutines.launch

data class DaySection(
    val dayIndex: Int,
    val dateLabel: String,
    val photoUri: String?,
    val memo: String?
)

class PastTripDetailViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PawRepository.get(app)

    val trip = MutableLiveData<TripEntity?>()
    val sections = MutableLiveData<List<DaySection>>()

    fun load(id: Long) {
        viewModelScope.launch {
            val t = repo.getTrip(id)
            trip.value = t
            if (t != null) {
                val journals = repo.getJournals(id).associateBy { it.dayIndex }
                val count = DateUtils.dayCount(t.startDate, t.endDate)
                sections.value = (0 until count).map { d ->
                    val label = "Day ${d + 1} · " +
                        DateUtils.formatDayWithWeekday(DateUtils.dayStart(t.startDate, d))
                    val j = journals[d]
                    DaySection(d, label, j?.photoUri, j?.memo)
                }
            }
        }
    }
}
