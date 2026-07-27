package com.pawmap.app.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.pawmap.app.data.dao.PawDao
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.data.mapper.PlaceMapper
import java.io.File
import java.io.FileOutputStream

object PlaceDataLoader {

    private const val ASSET_DB_NAME = "pawmap.db"

    suspend fun loadFromAssets(context: Context, dao: PawDao) {
        val rawDbFile = copyAssetDbIfNeeded(context)
        val rawDb = SQLiteDatabase.openDatabase(
            rawDbFile.path, null, SQLiteDatabase.OPEN_READONLY
        )

        val places = mutableListOf<PlaceEntity>()

        val cursor = rawDb.rawQuery(
            """
            SELECT p.contentid, p.title, p.city, p.addr1, p.tel,
                   p.mapx, p.mapy, p.category,
                   d.pet_possible, d.pet_acmpy_type, d.pet_need_matr,
                   d.pet_etc, d.usetime, d.parking, d.overview
            FROM places p
            LEFT JOIN place_detail d ON p.contentid = d.contentid
            """.trimIndent(),
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val petPossible = it.getString(it.getColumnIndexOrThrow("pet_possible"))
                val (small, medium, large) = PlaceMapper.parsePetSize(petPossible)
                val (sizeBadge, sizeBadgeType) = PlaceMapper.sizeBadgeFrom(petPossible)
                val categoryLabel = it.getString(it.getColumnIndexOrThrow("category"))
                val overview = it.getString(it.getColumnIndexOrThrow("overview"))

                places.add(
                    PlaceEntity(
                        id = it.getString(it.getColumnIndexOrThrow("contentid")).toLong(),
                        name = it.getString(it.getColumnIndexOrThrow("title")).orEmpty(),
                        category = categoryLabel.orEmpty(),
                        categoryType = PlaceMapper.mapCategoryType(categoryLabel),
                        region = it.getString(it.getColumnIndexOrThrow("city")).orEmpty(),
                        address = it.getString(it.getColumnIndexOrThrow("addr1")).orEmpty(),
                        phone = it.getString(it.getColumnIndexOrThrow("tel")).takeIf { t -> !t.isNullOrBlank() },
                        oneLiner = overview?.take(60),
                        openNow = true, // 실시간 계산 불가 — 기본값
                        hoursText = it.getString(it.getColumnIndexOrThrow("usetime")),
                        speciesBadge = null,
                        speciesBadgeType = null,
                        sizeBadge = sizeBadge,
                        sizeBadgeType = sizeBadgeType,
                        xFraction = 0.5f,
                        yFraction = 0.5f,
                        lat = it.getDouble(it.getColumnIndexOrThrow("mapy")),
                        lng = it.getDouble(it.getColumnIndexOrThrow("mapx")),
                        animalTypes = "강아지",
                        sizeSmall = small,
                        sizeMedium = medium,
                        sizeLarge = large,
                        indoorText = it.getString(it.getColumnIndexOrThrow("pet_acmpy_type")),
                        extraFeeText = null,
                        restrictionsText = listOfNotNull(
                            it.getString(it.getColumnIndexOrThrow("pet_need_matr")),
                            it.getString(it.getColumnIndexOrThrow("pet_etc"))
                        ).joinToString(" "),
                        facilitiesText = it.getString(it.getColumnIndexOrThrow("parking"))
                    )
                )
            }
        }
        rawDb.close()

        // 대량 데이터라 1000건씩 나눠서 insert
        places.chunked(1000).forEach { chunk ->
            dao.insertPlaces(chunk)
        }
    }

    private fun copyAssetDbIfNeeded(context: Context): File {
        val outFile = File(context.filesDir, ASSET_DB_NAME)
        if (!outFile.exists()) {
            context.assets.open(ASSET_DB_NAME).use { input ->
                FileOutputStream(outFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
        return outFile
    }
}