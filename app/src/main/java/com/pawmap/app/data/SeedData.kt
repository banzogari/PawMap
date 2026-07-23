package com.pawmap.app.data

import com.pawmap.app.data.dao.PawDao
import com.pawmap.app.data.entity.JournalEntity
import com.pawmap.app.data.entity.ListPlaceCrossRef
import com.pawmap.app.data.entity.PlaceEntity
import com.pawmap.app.data.entity.PlaceListEntity
import com.pawmap.app.data.entity.TripEntity
import com.pawmap.app.data.entity.TripPlaceEntity
import com.pawmap.app.util.DateUtils

/** Populates the database with sample data the first time it is created. */
object SeedData {

    val places = listOf(
        PlaceEntity(
            id = 1, name = "우드테일 애견카페", category = "카페", categoryType = "CAFE",
            region = "서울 마포구 성산동", address = "서울 마포구 성산로 128 1층",
            phone = "02-1234-5678", oneLiner = "넓은 마당과 실내석을 함께 갖춘 애견동반 카페",
            openNow = true, hoursText = "21:00에 영업 종료",
            speciesBadge = "종 제한 없음", speciesBadgeType = "POSITIVE",
            sizeBadge = "대형견 가능", sizeBadgeType = "POSITIVE",
            xFraction = 0.28f, yFraction = 0.36f, lat = 37.5667, lng = 126.9108,
            animalTypes = "강아지,고양이",
            sizeSmall = true, sizeMedium = true, sizeLarge = false,
            indoorText = "실내 동반 가능 (테라스석 별도 운영)",
            extraFeeText = "1마리당 5,000원 (음료 1잔 포함)",
            restrictionsText = "목줄 필수 착용, 케이지 지참 권장. 짖음이 심하거나 통제가 어려운 경우 입장이 제한될 수 있습니다.",
            facilitiesText = "배변봉투 비치, 급수대, 반려동물 전용석"
        ),
        PlaceEntity(
            id = 2, name = "멍멍이네 마당", category = "카페 · 베이커리", categoryType = "CAFE",
            region = "서울 마포구 연남동", address = "서울 마포구 연남로 45",
            phone = "02-2222-3333", oneLiner = "강아지 놀이터가 딸린 마당 있는 카페",
            openNow = false, hoursText = "10:00에 영업 시작",
            speciesBadge = "소형견만 가능", speciesBadgeType = "WARN",
            sizeBadge = null, sizeBadgeType = null,
            xFraction = 0.66f, yFraction = 0.30f, lat = 37.5623, lng = 126.9250,
            animalTypes = "강아지",
            sizeSmall = true, sizeMedium = false, sizeLarge = false,
            indoorText = "실외 마당 중심 운영 (실내 일부 동반 가능)",
            extraFeeText = "1마리당 3,000원",
            restrictionsText = "소형견만 입장 가능합니다. 리드줄을 항상 착용해주세요.",
            facilitiesText = "놀이터, 배변봉투, 급수대"
        ),
        PlaceEntity(
            id = 3, name = "강아지 고양이편의점 이문점", category = "애완동물용품점", categoryType = "SHOP",
            region = "서울 동대문구 이문동", address = "서울 동대문구 이문로 77",
            phone = "02-4444-5555", oneLiner = "반려동물 간식·용품을 파는 24시 편의점",
            openNow = true, hoursText = "24시간 영업",
            speciesBadge = "종 제한 없음", speciesBadgeType = "POSITIVE",
            sizeBadge = "대형견 가능", sizeBadgeType = "POSITIVE",
            xFraction = 0.45f, yFraction = 0.62f, lat = 37.5983, lng = 127.0630,
            animalTypes = "강아지,고양이",
            sizeSmall = true, sizeMedium = true, sizeLarge = true,
            indoorText = "실내 동반 가능",
            extraFeeText = null,
            restrictionsText = "입장 시 리드줄 착용 권장.",
            facilitiesText = "반려동물 용품, 급수대"
        ),
        PlaceEntity(
            id = 4, name = "댕댕뮤지엄", category = "박물관", categoryType = "MUSEUM",
            region = "서울 종로구 삼청동", address = "서울 종로구 삼청로 22",
            phone = "02-6666-7777", oneLiner = "반려견과 함께 관람하는 이색 전시 공간",
            openNow = true, hoursText = "18:00에 영업 종료",
            speciesBadge = "종 제한 없음", speciesBadgeType = "POSITIVE",
            sizeBadge = "중형견 가능", sizeBadgeType = "POSITIVE",
            xFraction = 0.80f, yFraction = 0.20f, lat = 37.5825, lng = 126.9820,
            animalTypes = "강아지",
            sizeSmall = true, sizeMedium = true, sizeLarge = false,
            indoorText = "실내 관람 (전 구역 동반 가능)",
            extraFeeText = "1마리당 4,000원",
            restrictionsText = "전시물 보호를 위해 리드줄 1.5m 이내 착용.",
            facilitiesText = "배변봉투, 반려동물 포토존"
        ),
        PlaceEntity(
            id = 5, name = "경포해변", category = "여행지", categoryType = "TRAVEL",
            region = "강원 강릉시 강문동", address = "강원 강릉시 창해로 514",
            phone = null, oneLiner = "반려견과 산책하기 좋은 넓은 백사장",
            openNow = true, hoursText = "상시 개방",
            speciesBadge = "종 제한 없음", speciesBadgeType = "POSITIVE",
            sizeBadge = "대형견 가능", sizeBadgeType = "POSITIVE",
            xFraction = 0.35f, yFraction = 0.48f, lat = 37.8055, lng = 128.9070,
            animalTypes = "강아지,고양이",
            sizeSmall = true, sizeMedium = true, sizeLarge = true,
            indoorText = "실외 개방 공간",
            extraFeeText = null,
            restrictionsText = "배변 처리는 반드시 보호자가 해주세요. 물놀이 구역은 시즌에 따라 제한될 수 있습니다.",
            facilitiesText = "샤워장, 주차장, 배변봉투함"
        ),
        PlaceEntity(
            id = 6, name = "오션뷰 펫텔", category = "숙소", categoryType = "STAY",
            region = "강원 속초시 대포동", address = "강원 속초시 대포항길 12",
            phone = "033-111-2222", oneLiner = "전 객실 반려동물 동반 가능한 오션뷰 펜션",
            openNow = true, hoursText = "체크인 15:00",
            speciesBadge = "종 제한 없음", speciesBadgeType = "POSITIVE",
            sizeBadge = "대형견 가능", sizeBadgeType = "POSITIVE",
            xFraction = 0.60f, yFraction = 0.55f, lat = 38.1780, lng = 128.6000,
            animalTypes = "강아지,고양이",
            sizeSmall = true, sizeMedium = true, sizeLarge = true,
            indoorText = "객실 내 동반 가능",
            extraFeeText = "1마리당 20,000원 / 박",
            restrictionsText = "최대 2마리까지 동반 가능. 침대 위 배변 시 추가 청소비가 발생할 수 있습니다.",
            facilitiesText = "반려동물 어메니티, 마당, 급수대"
        ),
        PlaceEntity(
            id = 7, name = "바다뷰 애견동반 맛집", category = "맛집", categoryType = "FOOD",
            region = "강원 속초시 영랑동", address = "강원 속초시 영랑해안길 5",
            phone = "033-333-4444", oneLiner = "테라스에서 반려견과 함께 식사 가능한 해산물 식당",
            openNow = true, hoursText = "21:00에 영업 종료",
            speciesBadge = "종 제한 없음", speciesBadgeType = "POSITIVE",
            sizeBadge = "중형견 가능", sizeBadgeType = "POSITIVE",
            xFraction = 0.50f, yFraction = 0.38f, lat = 38.2110, lng = 128.5990,
            animalTypes = "강아지",
            sizeSmall = true, sizeMedium = true, sizeLarge = false,
            indoorText = "테라스석만 동반 가능",
            extraFeeText = null,
            restrictionsText = "실내홀은 동반 불가. 테라스석 이용 시 리드줄 착용.",
            facilitiesText = "테라스 전용석, 급수대"
        ),
        PlaceEntity(
            id = 8, name = "속초 중앙시장", category = "여행지", categoryType = "TRAVEL",
            region = "강원 속초시 중앙동", address = "강원 속초시 중앙로147번길 12",
            phone = null, oneLiner = "간식 구경하기 좋은 활기찬 전통시장",
            openNow = true, hoursText = "20:00에 영업 종료",
            speciesBadge = "소형견만 가능", speciesBadgeType = "WARN",
            sizeBadge = null, sizeBadgeType = null,
            xFraction = 0.20f, yFraction = 0.70f, lat = 38.2070, lng = 128.5918,
            animalTypes = "강아지",
            sizeSmall = true, sizeMedium = false, sizeLarge = false,
            indoorText = "실외 통로 중심",
            extraFeeText = null,
            restrictionsText = "혼잡 시간대에는 반려견 안전을 위해 안거나 이동가방 이용을 권장합니다.",
            facilitiesText = "주차장"
        ),
        PlaceEntity(
            id = 9, name = "강릉 커피거리", category = "카페", categoryType = "CAFE",
            region = "강원 강릉시 견소동", address = "강원 강릉시 창해로 17",
            phone = null, oneLiner = "바다를 보며 커피 한 잔, 반려견 동반 카페 밀집 거리",
            openNow = true, hoursText = "22:00에 영업 종료",
            speciesBadge = "종 제한 없음", speciesBadgeType = "POSITIVE",
            sizeBadge = "대형견 가능", sizeBadgeType = "POSITIVE",
            xFraction = 0.72f, yFraction = 0.72f, lat = 37.7713, lng = 128.9470,
            animalTypes = "강아지,고양이",
            sizeSmall = true, sizeMedium = true, sizeLarge = true,
            indoorText = "매장별 상이 (다수 테라스 동반 가능)",
            extraFeeText = null,
            restrictionsText = "매장마다 동반 정책이 다르니 방문 전 확인하세요.",
            facilitiesText = "해변 산책로, 급수대"
        )
    )

    val lists = listOf(
        PlaceListEntity(id = 1, name = "가고 싶은 장소", iconType = "FLAG", isDefault = true, sortOrder = 0),
        PlaceListEntity(id = 2, name = "즐겨찾기", iconType = "HEART", isDefault = true, sortOrder = 1),
        PlaceListEntity(id = 3, name = "저장됨", iconType = "BOOKMARK", isDefault = true, sortOrder = 2)
    )

    /** "가고 싶은 장소" holds 4 places (matches the mockup). */
    val crossRefs = listOf(
        ListPlaceCrossRef(listId = 1, placeId = 1, addedAt = 1L),
        ListPlaceCrossRef(listId = 1, placeId = 3, addedAt = 2L),
        ListPlaceCrossRef(listId = 1, placeId = 2, addedAt = 3L),
        ListPlaceCrossRef(listId = 1, placeId = 4, addedAt = 4L)
    )

    suspend fun apply(dao: PawDao) {
        dao.insertPlaces(places)
        dao.insertLists(lists)
        crossRefs.forEach { dao.addToList(it) }
        seedPastTrips(dao)
    }

    private suspend fun seedPastTrips(dao: PawDao) {
        // 오사카 여행: 2026.7.20 ~ 7.22 (3 days) · 초코
        val osakaStart = DateUtils.startOfDay(2026, 7, 20)
        val osakaEnd = DateUtils.startOfDay(2026, 7, 22)
        val osakaId = dao.insertTrip(
            TripEntity(name = "오사카 여행", startDate = osakaStart, endDate = osakaEnd, petNames = "초코", createdAt = osakaStart)
        )
        dao.upsertJournal(
            JournalEntity(
                tripId = osakaId, dayIndex = 0, photoUri = null,
                memo = "간사이 국제공항에 도착. 초코가 비행기 이동장 안에서도 얌전히 잘 견뎌줬다. 난바역까지 리무진 버스로 이동."
            )
        )
        dao.upsertJournal(JournalEntity(tripId = osakaId, dayIndex = 1, photoUri = null, memo = null))
        dao.upsertJournal(
            JournalEntity(
                tripId = osakaId, dayIndex = 2, photoUri = null,
                memo = "도톤보리 산책. 사람이 너무 많아서 초코가 살짝 긴장했지만 금방 적응. 구로몬 시장에서 간식 구경."
            )
        )

        // 강릉·속초 여행 (지난): 2026.6.5 ~ 6.7 · 초코
        val gsStart = DateUtils.startOfDay(2026, 6, 5)
        val gsEnd = DateUtils.startOfDay(2026, 6, 7)
        val gsId = dao.insertTrip(
            TripEntity(name = "강릉·속초 여행", startDate = gsStart, endDate = gsEnd, petNames = "초코", createdAt = gsStart)
        )
        dao.upsertJournal(JournalEntity(tripId = gsId, dayIndex = 0, photoUri = null, memo = "경포해변에서 아침 산책. 파도 소리에 신난 초코."))
        dao.upsertJournal(JournalEntity(tripId = gsId, dayIndex = 2, photoUri = null, memo = "속초 중앙시장 구경 후 오션뷰 펫텔에서 마무리."))

        // 경주 여행: 2026.3.1 ~ 3.3 · 초코, 보리
        val gjStart = DateUtils.startOfDay(2026, 3, 1)
        val gjEnd = DateUtils.startOfDay(2026, 3, 3)
        val gjId = dao.insertTrip(
            TripEntity(name = "경주 여행", startDate = gjStart, endDate = gjEnd, petNames = "초코,보리", createdAt = gjStart)
        )
        dao.upsertJournal(JournalEntity(tripId = gjId, dayIndex = 0, photoUri = null, memo = "첨성대 앞에서 인증샷. 두 마리 다 잘 걸어줬다."))
    }
}
