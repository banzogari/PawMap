package com.pawmap.app.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A pet-friendly place. All fields come from local sample data; text in the
 * "info" section is stored verbatim (the spec forbids re-processing it).
 */
@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val category: String,          // display label, e.g. "카페 · 베이커리"
    val categoryType: String,      // CategoryType enum name
    val region: String,            // "서울 마포구 성산동"
    val address: String,           // full address line
    val phone: String?,
    val oneLiner: String?,         // short description for the list row
    val openNow: Boolean,
    val hoursText: String?,        // "21:00에 영업 종료"
    // list badges
    val speciesBadge: String?,     // "종 제한 없음" / "소형견만 가능"
    val speciesBadgeType: String?, // BadgeType enum name
    val sizeBadge: String?,        // "대형견 가능"
    val sizeBadgeType: String?,
    // placeholder-map position (0..1) — kept for the fallback PlaceholderMapView
    val xFraction: Float,
    val yFraction: Float,
    // real-world coordinates for the map
    val lat: Double,
    val lng: Double,
    // info tab
    val animalTypes: String,       // comma list: "강아지,고양이"
    val sizeSmall: Boolean,
    val sizeMedium: Boolean,
    val sizeLarge: Boolean,
    val indoorText: String?,
    val extraFeeText: String?,
    val restrictionsText: String?,
    val facilitiesText: String?
)

@Entity(tableName = "place_lists")
data class PlaceListEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val iconType: String,          // ListIconType enum name
    val isDefault: Boolean,
    val sortOrder: Int
)

@Entity(
    tableName = "list_place_cross_ref",
    primaryKeys = ["listId", "placeId"],
    indices = [Index("placeId")]
)
data class ListPlaceCrossRef(
    val listId: Long,
    val placeId: Long,
    val addedAt: Long
)

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val startDate: Long,           // epoch millis (start of day)
    val endDate: Long,             // epoch millis (start of day)
    val petNames: String,          // comma list: "초코,보리"
    val createdAt: Long
)

@Entity(
    tableName = "trip_places",
    indices = [Index("tripId")]
)
data class TripPlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tripId: Long,
    val dayIndex: Int,             // 0-based day
    val placeId: Long,
    val orderIndex: Int
)

/** One journal entry per (trip, day). */
@Entity(
    tableName = "journals",
    indices = [Index(value = ["tripId", "dayIndex"], unique = true)]
)
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tripId: Long,
    val dayIndex: Int,
    val photoUri: String?,
    val memo: String?
)
