package com.pawmap.app.ui.common

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.pawmap.app.R
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.data.model.BadgeType
import com.pawmap.app.data.model.CategoryType

/** Category helpers -------------------------------------------------------- */

fun PlaceEntity.category(): CategoryType = CategoryType.fromName(categoryType)

fun PlaceEntity.markerColor(view: View): Int =
    ContextCompat.getColor(view.context, category().colorRes)

/** Badge styling ----------------------------------------------------------- */

private fun badgeStyle(type: BadgeType): Pair<Int, Int> = when (type) {
    BadgeType.POSITIVE -> R.drawable.bg_badge_green to R.color.positive
    BadgeType.WARN -> R.drawable.bg_badge_orange to R.color.warning
    BadgeType.NEGATIVE -> R.drawable.bg_badge_red to R.color.negative
    BadgeType.NEUTRAL -> R.drawable.bg_badge_neutral to R.color.text_secondary
}

/** Shows [text] styled by [type], or hides the view when text is null/blank. */
fun TextView.bindBadge(text: String?, type: String?) {
    if (text.isNullOrBlank()) {
        visibility = View.GONE
        return
    }
    val badgeType = runCatching { BadgeType.valueOf(type ?: "NEUTRAL") }.getOrDefault(BadgeType.NEUTRAL)
    val (bg, color) = badgeStyle(badgeType)
    visibility = View.VISIBLE
    this.text = text
    setBackgroundResource(bg)
    setTextColor(ContextCompat.getColor(context, color))
}

/** Sets the size badge for the info tab: green "가능" or red "불가". */
fun TextView.bindSizeBadge(label: String, possible: Boolean) {
    text = if (possible) "$label 가능" else "$label 불가"
    val (bg, color) = if (possible) {
        R.drawable.bg_badge_green to R.color.positive
    } else {
        R.drawable.bg_badge_red to R.color.negative
    }
    setBackgroundResource(bg)
    setTextColor(ContextCompat.getColor(context, color))
}

/** Open-status text: green when open, red when closed. */
fun TextView.bindOpenStatus(openNow: Boolean, hoursText: String?) {
    val status = if (openNow) "영업 중" else "영업 종료"
    text = if (hoursText.isNullOrBlank()) status else "$status · $hoursText"
    val color = if (openNow) R.color.positive else R.color.negative
    setTextColor(ContextCompat.getColor(context, color))
}

fun View.visibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}
