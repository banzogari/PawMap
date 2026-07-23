package com.pawmap.app.data.model

import androidx.annotation.DrawableRes
import com.pawmap.app.R

/** High-level category used for marker color + icon + the home filter chips. */
enum class CategoryType(
    val filterLabel: String?,
    @DrawableRes val iconRes: Int,
    val colorRes: Int
) {
    CAFE("카페", R.drawable.ic_cafe, R.color.cat_cafe),
    FOOD("맛집", R.drawable.ic_restaurant, R.color.cat_food),
    STAY("숙소", R.drawable.ic_hotel, R.color.cat_stay),
    TRAVEL("여행지", R.drawable.ic_park, R.color.cat_travel),
    SHOP(null, R.drawable.ic_store, R.color.cat_default),
    MUSEUM(null, R.drawable.ic_store, R.color.cat_default),
    OTHER(null, R.drawable.ic_place, R.color.cat_default);

    companion object {
        fun fromName(name: String?): CategoryType =
            entries.firstOrNull { it.name == name } ?: OTHER
    }
}

/** Visual tone of a badge shown in lists / detail. */
enum class BadgeType { POSITIVE, WARN, NEGATIVE, NEUTRAL }

/** Icon shown next to a saved-place list. */
enum class ListIconType(@DrawableRes val iconRes: Int, val tintRes: Int) {
    FLAG(R.drawable.ic_flag, R.color.cat_travel),
    HEART(R.drawable.ic_favorite, R.color.negative),
    BOOKMARK(R.drawable.ic_bookmark, R.color.accent),
    CUSTOM(R.drawable.ic_bookmark, R.color.text_secondary);

    companion object {
        fun fromName(name: String?): ListIconType =
            entries.firstOrNull { it.name == name } ?: CUSTOM
    }
}
