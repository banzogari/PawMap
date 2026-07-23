# PawMap 🐾

반려동물 동반 가능 장소를 지도에서 탐색하고, 저장하고, 여행 일정과 저널을 남기는 Android 앱.
기능명세서 v2를 기준으로 구현했습니다. (다크 테마 / 하단 탭 3개 / 로그인 없음 / 전 기능 로컬 저장)

## 기술 스택
- **Kotlin + XML View** (ViewBinding)
- **Navigation Component** (단일 Activity + Fragment)
- **Room** (로컬 DB, KSP) — 첫 실행 시 샘플 데이터 자동 시드
- **Lifecycle ViewModel / LiveData + Coroutines**
- **Naver Map SDK** (`map-sdk 3.23.3`) + **play-services-location** (현재 위치)
- **Coil** (저널 사진 로딩)
- **Material 3** (다크 전용 테마)
- AGP 8.12.3 / Kotlin 2.0.21 / Gradle 8.13 / compileSdk 36 / minSdk 24

## 지도 설정 (필수)
실제 지도가 보이려면 **NAVER Cloud Platform** 인증 키가 필요합니다.

1. https://www.ncloud.com 가입 → 콘솔 → **Services > Application Services > Maps** 이용 신청
2. **Application 등록** 시 Mobile Dynamic Map을 켜고, **Android 앱 패키지 이름**에 `com.pawmap.app` 등록
3. 발급된 **Client ID(= Key ID)** 를 프로젝트 루트 `local.properties` 에 입력:
   ```
   NAVER_MAP_CLIENT_ID=여기에_발급받은_키
   ```
4. Gradle Sync 후 실행. 키가 비어 있거나 틀리면 앱은 켜지지만 지도 영역에 인증 실패 안내가 뜹니다.

> 키는 `local.properties`(버전관리 제외)에서 읽어 매니페스트 `com.naver.maps.map.NCP_KEY_ID` 로 주입됩니다.

## 빌드 / 실행
Android Studio에서 이 폴더를 열고 `app`을 실행하면 됩니다. CLI 빌드:
```bash
./gradlew :app:assembleDebug
```
산출물: `app/build/outputs/apk/debug/app-debug.apk`

## 화면 구성 (명세서 매핑)
| 화면 | 명세 | 위치 |
| --- | --- | --- |
| 지도 홈 | S-01 | `ui/map/MapHomeFragment` |
| 키워드 검색결과 | S-02 | `ui/search/SearchResultFragment` |
| 장소 상세 (개요/정보) | S-03 | `ui/detail/PlaceDetailFragment` |
| 저장한 플레이스 | S-04 | `ui/saved/SavedPlacesFragment` |
| 목록 상세 | S-05 | `ui/saved/ListDetailFragment` |
| 여행 일정 메인 | S-06 | `ui/trip/TripMainFragment` |
| 날짜 선택 | S-07 | `MaterialDatePicker` (TripMain에서 호출) |
| 여행/반려동물 이름 입력 | S-08 | `ui/trip/TripNameFragment` |
| 여행 상세 (Day별 장소) | S-09/10 | `ui/trip/TripDetailFragment` |
| 지난 여행 목록 | S-11 | `ui/trip/PastTripsFragment` |
| 지난 여행 저널 | S-12 | `ui/trip/PastTripDetailFragment` |
| 사진 및 메모 입력 | S-13 | `ui/trip/JournalEditDialogFragment` |

## 데이터 계층
- 엔티티: `Place`, `PlaceList`, `ListPlaceCrossRef`, `Trip`, `TripPlace`, `Journal`
- 저널은 명세대로 **(여행, Day) 단위 1건**으로 저장 — 그 Day의 어느 장소 카드에서 열어도 같은 항목을 편집
- 지난 여행 판정: 종료일이 지나면 자동으로 지난 여행으로 분류 (쿼리에서 `endDate < 오늘`)
- 기본 목록 3개(가고 싶은 장소/즐겨찾기/저장됨)와 샘플 장소·지난 여행이 시드됨

## 명세 대비 구현 메모 (의도된 결정)
1. **지도는 Naver Map 연동** — 지도 홈(S-01)과 여행 상세 미니맵(S-09/10)에서 실제 지도 사용.
   장소는 위/경도(`PlaceEntity.lat/lng`)로 카테고리색 마커 표시, 마커 탭 → 상세 이동, 현재 위치
   버튼은 위치 권한 요청 + Follow 모드. 여행 미니맵은 제스처를 꺼 스크롤 충돌을 방지.
   (초기 프로토타입의 `PlaceholderMapView`는 키 없이 쓰는 대체용으로 소스에 남겨둠 — 현재 미사용.)
2. **날짜 선택(S-07)** 은 목업의 커스텀 인라인 캘린더 대신 Material `dateRangePicker` 사용(기능 동일).
3. **정보 탭 텍스트**는 시드 데이터 원문을 그대로 노출(재가공 없음).
4. 진행 중 여행이 없을 때 상세의 "여행 일정에 추가"는 안내 후 여행 탭으로 유도.
5. 여행 상세의 장소 카드: 탭 → 장소 상세, **길게 누르면** 일정에서 제거.

## 다음 라운드 후보 (명세 7절)
- S-13에서 이미 입력된 사진/메모의 개별 삭제 흐름
- 여행 일정 내 장소 순서 편집(드래그), 현재 위치 기반 주변 탐색(실지도 연동 시)
# PawMap
