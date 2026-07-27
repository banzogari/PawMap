package com.pawmap.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pawmap.app.data.dao.PawDao
import com.pawmap.app.data.entity.JournalEntity
import com.pawmap.app.data.entity.ListPlaceCrossRef
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.data.entity.PlaceListEntity
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.data.entity.TripPlaceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        PlaceEntity::class,
        PlaceListEntity::class,
        ListPlaceCrossRef::class,
        TripEntity::class,
        TripPlaceEntity::class,
        JournalEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pawDao(): PawDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val appContext = context.applicationContext   // ← 추가
                val db = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "pawmap.db"
                )
                    .addCallback(buildSeedCallback(appContext))  // ← 함수 호출로 변경
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                db
            }
        }

        // ← SeedCallback을 context를 받는 함수로 변경
        private fun buildSeedCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val dao = INSTANCE?.pawDao() ?: return
                    CoroutineScope(Dispatchers.IO).launch {
                        SeedData.apply(dao)
                        PlaceDataLoader.loadFromAssets(context, dao)   // ← 추가
                    }
                }
            }
        }
    }
}