package com.pawmap.app.util

import java.util.Calendar

/**
 * Date helpers built on [Calendar] so we stay compatible with minSdk 24
 * without needing core-library desugaring for java.time.
 */
object DateUtils {

    private val WEEKDAYS_KO = arrayOf("일", "월", "화", "수", "목", "금", "토")

    /** Millis at 00:00 of the given date. month is 1-based. */
    fun startOfDay(year: Int, month: Int, day: Int): Long {
        val c = Calendar.getInstance()
        c.clear()
        c.set(year, month - 1, day, 0, 0, 0)
        return c.timeInMillis
    }

    /** Millis at 00:00 today. */
    fun todayStart(): Long {
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.timeInMillis
    }

    private fun cal(millis: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = millis }

    /** Number of days in the trip, inclusive of both ends. */
    fun dayCount(startMillis: Long, endMillis: Long): Int {
        val dayMs = 24L * 60 * 60 * 1000
        val diff = (endMillis - startMillis) / dayMs
        return (diff + 1).toInt().coerceAtLeast(1)
    }

    /** Millis for the start of day N (0-based) of a trip. */
    fun dayStart(startMillis: Long, dayIndex: Int): Long {
        val c = cal(startMillis)
        c.add(Calendar.DAY_OF_MONTH, dayIndex)
        return c.timeInMillis
    }

    /** "2026.7.27" */
    fun formatDot(millis: Long): String {
        val c = cal(millis)
        return "${c.get(Calendar.YEAR)}.${c.get(Calendar.MONTH) + 1}.${c.get(Calendar.DAY_OF_MONTH)}"
    }

    /** "2026.7.27 - 7.29" */
    fun formatRange(startMillis: Long, endMillis: Long): String {
        val s = cal(startMillis)
        val e = cal(endMillis)
        val start = "${s.get(Calendar.YEAR)}.${s.get(Calendar.MONTH) + 1}.${s.get(Calendar.DAY_OF_MONTH)}"
        val end = if (s.get(Calendar.YEAR) == e.get(Calendar.YEAR)) {
            "${e.get(Calendar.MONTH) + 1}.${e.get(Calendar.DAY_OF_MONTH)}"
        } else {
            "${e.get(Calendar.YEAR)}.${e.get(Calendar.MONTH) + 1}.${e.get(Calendar.DAY_OF_MONTH)}"
        }
        return "$start - $end"
    }

    /**
     * MaterialDatePicker returns selections as UTC midnight; convert to the
     * equivalent local start-of-day so day math lines up with the rest of the app.
     */
    fun utcToLocalStartOfDay(utcMillis: Long): Long {
        val c = Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"))
        c.timeInMillis = utcMillis
        return startOfDay(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH))
    }

    /** Bridges the calendar picker's java.time.LocalDate to our millis-based day math. */
    fun startOfDay(date: java.time.LocalDate): Long =
        startOfDay(date.year, date.monthValue, date.dayOfMonth)

    /** "7.27 (월)" */
    fun formatDayWithWeekday(millis: Long): String {
        val c = cal(millis)
        val wd = WEEKDAYS_KO[c.get(Calendar.DAY_OF_WEEK) - 1]
        return "${c.get(Calendar.MONTH) + 1}.${c.get(Calendar.DAY_OF_MONTH)} ($wd)"
    }
}
