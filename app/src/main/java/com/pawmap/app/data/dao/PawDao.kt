package com.pawmap.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.pawmap.app.data.entity.JournalEntity
import com.pawmap.app.data.entity.ListPlaceCrossRef
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.data.entity.PlaceListEntity
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.data.entity.TripPlaceEntity
import kotlinx.coroutines.flow.Flow

/** Projection: a saved-place list plus how many places it holds. */
data class ListWithCount(
    val id: Long,
    val name: String,
    val iconType: String,
    val isDefault: Boolean,
    val sortOrder: Int,
    val placeCount: Int
)

@Dao
interface PawDao {

    // ---- Places ----
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<PlaceEntity>)

    @Query("SELECT * FROM places WHERE id = :id")
    suspend fun getPlace(id: Long): PlaceEntity?

    @Query("SELECT * FROM places WHERE id IN (:ids)")
    suspend fun getPlacesByIds(ids: List<Long>): List<PlaceEntity>

    @Query("SELECT * FROM places")
    suspend fun getAllPlaces(): List<PlaceEntity>

    @Query(
        "SELECT * FROM places WHERE " +
                "name LIKE '%' || :q || '%' OR category LIKE '%' || :q || '%' " +
                "OR oneLiner LIKE '%' || :q || '%' OR region LIKE '%' || :q || '%'"
    )
    suspend fun search(q: String): List<PlaceEntity>

    @Query("SELECT * FROM places WHERE categoryType = :type")
    suspend fun getPlacesByCategoryType(type: String): List<PlaceEntity>

    @Query("SELECT COUNT(*) FROM places")
    suspend fun placeCount(): Int

    // ---- Lists ----
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: PlaceListEntity): Long

    @Insert
    suspend fun insertLists(lists: List<PlaceListEntity>)

    @Query(
        "SELECT l.id AS id, l.name AS name, l.iconType AS iconType, " +
                "l.isDefault AS isDefault, l.sortOrder AS sortOrder, " +
                "(SELECT COUNT(*) FROM list_place_cross_ref x WHERE x.listId = l.id) AS placeCount " +
                "FROM place_lists l ORDER BY l.sortOrder ASC, l.id ASC"
    )
    fun observeListsWithCount(): Flow<List<ListWithCount>>

    @Query("SELECT * FROM place_lists WHERE id = :id")
    suspend fun getList(id: Long): PlaceListEntity?

    @Query("SELECT COUNT(*) FROM place_lists WHERE name = :name")
    suspend fun listNameCount(name: String): Int

    @Query("DELETE FROM place_lists WHERE id = :id AND isDefault = 0")
    suspend fun deleteList(id: Long)

    @Query("SELECT MAX(sortOrder) FROM place_lists")
    suspend fun maxListOrder(): Int?

    // ---- List membership ----
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToList(ref: ListPlaceCrossRef)

    @Query("DELETE FROM list_place_cross_ref WHERE listId = :listId AND placeId = :placeId")
    suspend fun removeFromList(listId: Long, placeId: Long)

    @Query(
        "SELECT p.* FROM places p " +
                "INNER JOIN list_place_cross_ref x ON p.id = x.placeId " +
                "WHERE x.listId = :listId ORDER BY x.addedAt ASC"
    )
    fun observePlacesInList(listId: Long): Flow<List<PlaceEntity>>

    @Query("SELECT COUNT(*) FROM list_place_cross_ref WHERE listId = :listId AND placeId = :placeId")
    suspend fun isInList(listId: Long, placeId: Long): Int

    // ---- Trips ----
    @Insert
    suspend fun insertTrip(trip: TripEntity): Long

    @Update
    suspend fun updateTrip(trip: TripEntity)

    @Query("SELECT * FROM trips WHERE id = :id")
    suspend fun getTrip(id: Long): TripEntity?

    @Query("SELECT * FROM trips WHERE endDate >= :todayStart ORDER BY startDate ASC LIMIT 1")
    suspend fun getOngoingTrip(todayStart: Long): TripEntity?

    @Query("SELECT * FROM trips WHERE endDate >= :todayStart ORDER BY startDate ASC LIMIT 1")
    fun observeOngoingTrip(todayStart: Long): Flow<TripEntity?>

    @Query("SELECT * FROM trips WHERE endDate < :todayStart ORDER BY startDate DESC")
    fun observePastTrips(todayStart: Long): Flow<List<TripEntity>>

    // ---- Trip places ----
    @Insert
    suspend fun insertTripPlace(tp: TripPlaceEntity): Long

    @Query("DELETE FROM trip_places WHERE id = :id")
    suspend fun deleteTripPlace(id: Long)

    @Query("SELECT * FROM trip_places WHERE tripId = :tripId ORDER BY dayIndex, orderIndex")
    suspend fun getTripPlaces(tripId: Long): List<TripPlaceEntity>

    @Query("SELECT * FROM trip_places WHERE tripId = :tripId ORDER BY dayIndex, orderIndex")
    fun observeTripPlaces(tripId: Long): Flow<List<TripPlaceEntity>>

    @Query("SELECT MAX(orderIndex) FROM trip_places WHERE tripId = :tripId AND dayIndex = :dayIndex")
    suspend fun maxOrderIndex(tripId: Long, dayIndex: Int): Int?

    // ---- Journals ----
    @Upsert
    suspend fun upsertJournal(journal: JournalEntity)

    @Query("SELECT * FROM journals WHERE tripId = :tripId AND dayIndex = :dayIndex LIMIT 1")
    suspend fun getJournal(tripId: Long, dayIndex: Int): JournalEntity?

    @Query("SELECT * FROM journals WHERE tripId = :tripId")
    suspend fun getJournals(tripId: Long): List<JournalEntity>
}