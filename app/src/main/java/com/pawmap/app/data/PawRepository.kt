package com.pawmap.app.data

import android.content.Context
import com.pawmap.app.data.dao.ListWithCount
import com.pawmap.app.data.dao.PawDao
import com.pawmap.app.data.entity.JournalEntity
import com.pawmap.app.data.entity.ListPlaceCrossRef
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.data.entity.PlaceListEntity
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.data.entity.TripPlaceEntity
import com.pawmap.app.util.DateUtils
import kotlinx.coroutines.flow.Flow

/** Single entry point for all data access. */
class PawRepository private constructor(private val dao: PawDao) {

    // ---- Places / search ----
    suspend fun getPlace(id: Long): PlaceEntity? = dao.getPlace(id)
    suspend fun getPlacesByIds(ids: List<Long>): List<PlaceEntity> =
        if (ids.isEmpty()) emptyList() else dao.getPlacesByIds(ids)
    suspend fun getAllPlaces(): List<PlaceEntity> = dao.getAllPlaces()

    suspend fun search(query: String): List<PlaceEntity> {
        val q = query.trim()
        return if (q.isEmpty()) dao.getAllPlaces() else dao.search(q)
    }

    /** Exact categoryType match, used by the map-home filter chips. */
    suspend fun searchByCategory(categoryType: String): List<PlaceEntity> =
        dao.getPlacesByCategoryType(categoryType)

    // ---- Saved-place lists ----
    fun observeLists(): Flow<List<ListWithCount>> = dao.observeListsWithCount()
    suspend fun getList(id: Long): PlaceListEntity? = dao.getList(id)
    fun observePlacesInList(listId: Long): Flow<List<PlaceEntity>> = dao.observePlacesInList(listId)

    /** Returns false if the name is blank or already used. */
    suspend fun createList(name: String): Boolean {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return false
        if (dao.listNameCount(trimmed) > 0) return false
        val order = (dao.maxListOrder() ?: 0) + 1
        dao.insertList(
            PlaceListEntity(name = trimmed, iconType = "CUSTOM", isDefault = false, sortOrder = order)
        )
        return true
    }

    suspend fun deleteList(id: Long) = dao.deleteList(id)

    suspend fun addPlaceToList(listId: Long, placeId: Long) =
        dao.addToList(ListPlaceCrossRef(listId, placeId, System.currentTimeMillis()))

    suspend fun removePlaceFromList(listId: Long, placeId: Long) =
        dao.removeFromList(listId, placeId)

    suspend fun isInList(listId: Long, placeId: Long): Boolean =
        dao.isInList(listId, placeId) > 0

    // ---- Favorite (the star in search results) toggles the 즐겨찾기 list ----
    suspend fun isFavorite(placeId: Long): Boolean = isInList(FAVORITE_LIST_ID, placeId)

    suspend fun toggleFavorite(placeId: Long): Boolean {
        return if (isFavorite(placeId)) {
            dao.removeFromList(FAVORITE_LIST_ID, placeId); false
        } else {
            addPlaceToList(FAVORITE_LIST_ID, placeId); true
        }
    }

    // ---- Quick save (the bookmark button on place detail) ----
    suspend fun isSaved(placeId: Long): Boolean = dao.isInList(SAVED_LIST_ID, placeId) > 0

    /** Toggles membership in the default "저장됨" list; returns the new saved state. */
    suspend fun toggleSaved(placeId: Long): Boolean {
        return if (isSaved(placeId)) {
            dao.removeFromList(SAVED_LIST_ID, placeId); false
        } else {
            addPlaceToList(SAVED_LIST_ID, placeId); true
        }
    }

    // ---- Trips ----
    fun observeOngoingTrip(): Flow<TripEntity?> = dao.observeOngoingTrip(DateUtils.todayStart())
    fun observePastTrips(): Flow<List<TripEntity>> = dao.observePastTrips(DateUtils.todayStart())
    suspend fun getOngoingTrip(): TripEntity? = dao.getOngoingTrip(DateUtils.todayStart())
    suspend fun getTrip(id: Long): TripEntity? = dao.getTrip(id)

    suspend fun createTrip(name: String, startDate: Long, endDate: Long, petNames: List<String>): Long {
        val safeName = name.trim().ifEmpty { "제목 없는 여행" }
        return dao.insertTrip(
            TripEntity(
                name = safeName,
                startDate = startDate,
                endDate = endDate,
                petNames = petNames.joinToString(","),
                createdAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun renameTrip(trip: TripEntity, newName: String) {
        dao.updateTrip(trip.copy(name = newName.trim().ifEmpty { trip.name }))
    }

    /** Deletes a trip and all its associated day-places and journal entries. */
    suspend fun deleteTrip(tripId: Long) {
        dao.deleteTripPlacesByTrip(tripId)
        dao.deleteJournalsByTrip(tripId)
        dao.deleteTripRow(tripId)
    }

    // ---- Trip places ----
    fun observeTripPlaces(tripId: Long): Flow<List<TripPlaceEntity>> = dao.observeTripPlaces(tripId)
    suspend fun getTripPlaces(tripId: Long): List<TripPlaceEntity> = dao.getTripPlaces(tripId)

    suspend fun addPlaceToTripDay(tripId: Long, dayIndex: Int, placeId: Long) {
        val next = (dao.maxOrderIndex(tripId, dayIndex) ?: -1) + 1
        dao.insertTripPlace(TripPlaceEntity(tripId = tripId, dayIndex = dayIndex, placeId = placeId, orderIndex = next))
    }

    suspend fun removeTripPlace(id: Long) = dao.deleteTripPlace(id)

    // ---- Journals ----
    suspend fun getJournal(tripId: Long, dayIndex: Int): JournalEntity? = dao.getJournal(tripId, dayIndex)
    suspend fun getJournals(tripId: Long): List<JournalEntity> = dao.getJournals(tripId)
    suspend fun saveJournal(tripId: Long, dayIndex: Int, photoUri: String?, memo: String?) {
        val existing = dao.getJournal(tripId, dayIndex)
        dao.upsertJournal(
            JournalEntity(
                id = existing?.id ?: 0,
                tripId = tripId,
                dayIndex = dayIndex,
                photoUri = photoUri,
                memo = memo
            )
        )
    }

    companion object {
        const val FAVORITE_LIST_ID = 2L
        const val SAVED_LIST_ID = 3L

        @Volatile
        private var INSTANCE: PawRepository? = null

        fun get(context: Context): PawRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PawRepository(AppDatabase.getInstance(context).pawDao()).also { INSTANCE = it }
            }
        }
    }
}