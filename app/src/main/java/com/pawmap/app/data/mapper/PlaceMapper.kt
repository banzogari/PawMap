package com.pawmap.app.data.mapper

object PlaceMapper {

    // places.category(한글) → PlaceEntity.categoryType
    fun mapCategoryType(categoryLabel: String?): String {
        return when (categoryLabel) {
            "카페" -> "CAFE"
            "맛집" -> "FOOD"
            "숙소" -> "STAY"
            "쇼핑" -> "SHOP"
            "여행지" -> "TRAVEL"
            else -> "TRAVEL"
        }
    }

    // place_detail.pet_possible 텍스트 기반 사이즈 추정
    fun parsePetSize(petPossible: String?): Triple<Boolean, Boolean, Boolean> {
        val text = petPossible.orEmpty()
        val large = text.contains("대형") || text.contains("전 견종")
        val medium = text.contains("중형") || text.contains("전 견종")
        val small = text.contains("소형") || text.contains("전 견종")
        return Triple(small, medium, large)
    }

    fun sizeBadgeFrom(petPossible: String?): Pair<String?, String?> {
        val text = petPossible.orEmpty()
        return when {
            text.contains("맹견") -> "맹견 제한" to "WARN"
            text.contains("소형견만") -> "소형견만 가능" to "WARN"
            text.contains("전 견종") -> "대형견 가능" to "POSITIVE"
            else -> null to null
        }
    }
}