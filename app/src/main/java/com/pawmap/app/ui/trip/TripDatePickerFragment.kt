package com.pawmap.app.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.pawmap.app.R
import com.pawmap.app.databinding.CalendarDayLayoutBinding
import com.pawmap.app.databinding.CalendarMonthHeaderBinding
import com.pawmap.app.databinding.FragmentTripDatePickerBinding
import com.pawmap.app.util.DateUtils
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class TripDatePickerFragment : Fragment() {

    private var _binding: FragmentTripDatePickerBinding? = null
    private val binding get() = _binding!!

    private var selectionStart: LocalDate? = null
    private var selectionEnd: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripDatePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        val currentMonth = YearMonth.now()
        binding.calendarView.setup(currentMonth, currentMonth.plusMonths(18), DayOfWeek.SUNDAY)
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.bind(data, this@TripDatePickerFragment)
            }
        }

        binding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    container.binding.tvMonth.text =
                        "${data.yearMonth.year}년 ${data.yearMonth.monthValue}월"
                }
            }

        binding.btnConfirm.setOnClickListener {
            val start = selectionStart ?: return@setOnClickListener
            val end = selectionEnd ?: start
            findNavController().navigate(
                R.id.action_datePicker_to_tripName,
                bundleOf(
                    "startDate" to DateUtils.startOfDay(start),
                    "endDate" to DateUtils.startOfDay(end)
                )
            )
        }

        updateRangeText()
    }

    fun onDayClicked(day: LocalDate) {
        val start = selectionStart
        val end = selectionEnd
        when {
            start == null || end != null -> {
                selectionStart = day
                selectionEnd = null
            }
            day.isBefore(start) -> {
                selectionStart = day
                selectionEnd = null
            }
            else -> {
                selectionEnd = day
            }
        }
        binding.calendarView.notifyCalendarChanged()
        updateRangeText()
    }

    fun isInSelection(day: LocalDate): Boolean {
        val start = selectionStart ?: return false
        val end = selectionEnd ?: return day == start
        return !day.isBefore(start) && !day.isAfter(end)
    }

    fun isRangeEdge(day: LocalDate): Boolean = day == selectionStart || day == selectionEnd

    private fun updateRangeText() {
        val start = selectionStart
        val end = selectionEnd
        binding.tvRange.text = when {
            start == null -> getString(R.string.pick_dates_hint)
            end == null -> DateUtils.formatDayWithWeekday(DateUtils.startOfDay(start))
            else -> DateUtils.formatDayWithWeekday(DateUtils.startOfDay(start)) + " – " +
                    DateUtils.formatDayWithWeekday(DateUtils.startOfDay(end))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class DayViewContainer(view: View) : ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)

    fun bind(data: CalendarDay, fragment: TripDatePickerFragment) {
        val context = binding.root.context

        if (data.position == DayPosition.MonthDate) {
            binding.tvDay.text = data.date.dayOfMonth.toString()
            binding.tvDay.visibility = View.VISIBLE

            val selected = fragment.isRangeEdge(data.date)
            val inRange = fragment.isInSelection(data.date)

            binding.tvDay.setBackgroundResource(
                if (selected) R.drawable.bg_calendar_day_selected else 0
            )
            binding.tvDay.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (selected) R.color.white else R.color.text_primary
                )
            )
            binding.rangeBackground.visibility = if (inRange) View.VISIBLE else View.INVISIBLE

            binding.root.setOnClickListener { fragment.onDayClicked(data.date) }
        } else {
            // Out-of-month cell: keep blank, matching the reference design.
            binding.tvDay.text = ""
            binding.rangeBackground.visibility = View.INVISIBLE
            binding.root.setOnClickListener(null)
        }
    }
}

class MonthViewContainer(view: View) : ViewContainer(view) {
    val binding = CalendarMonthHeaderBinding.bind(view)
}