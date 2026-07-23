package com.pawmap.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pawmap.app.data.entity.JournalEntity;
import com.pawmap.app.data.entity.ListPlaceCrossRef;
import com.pawmap.app.data.entity.PlaceEntity;
import com.pawmap.app.data.entity.PlaceListEntity;
import com.pawmap.app.data.entity.TripEntity;
import com.pawmap.app.data.entity.TripPlaceEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PawDao_Impl implements PawDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlaceEntity> __insertionAdapterOfPlaceEntity;

  private final EntityInsertionAdapter<PlaceListEntity> __insertionAdapterOfPlaceListEntity;

  private final EntityInsertionAdapter<PlaceListEntity> __insertionAdapterOfPlaceListEntity_1;

  private final EntityInsertionAdapter<ListPlaceCrossRef> __insertionAdapterOfListPlaceCrossRef;

  private final EntityInsertionAdapter<TripEntity> __insertionAdapterOfTripEntity;

  private final EntityInsertionAdapter<TripPlaceEntity> __insertionAdapterOfTripPlaceEntity;

  private final EntityDeletionOrUpdateAdapter<TripEntity> __updateAdapterOfTripEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteList;

  private final SharedSQLiteStatement __preparedStmtOfRemoveFromList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTripPlace;

  private final EntityUpsertionAdapter<JournalEntity> __upsertionAdapterOfJournalEntity;

  public PawDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlaceEntity = new EntityInsertionAdapter<PlaceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `places` (`id`,`name`,`category`,`categoryType`,`region`,`address`,`phone`,`oneLiner`,`openNow`,`hoursText`,`speciesBadge`,`speciesBadgeType`,`sizeBadge`,`sizeBadgeType`,`xFraction`,`yFraction`,`lat`,`lng`,`animalTypes`,`sizeSmall`,`sizeMedium`,`sizeLarge`,`indoorText`,`extraFeeText`,`restrictionsText`,`facilitiesText`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaceEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getCategory());
        statement.bindString(4, entity.getCategoryType());
        statement.bindString(5, entity.getRegion());
        statement.bindString(6, entity.getAddress());
        if (entity.getPhone() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPhone());
        }
        if (entity.getOneLiner() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getOneLiner());
        }
        final int _tmp = entity.getOpenNow() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getHoursText() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getHoursText());
        }
        if (entity.getSpeciesBadge() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getSpeciesBadge());
        }
        if (entity.getSpeciesBadgeType() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getSpeciesBadgeType());
        }
        if (entity.getSizeBadge() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getSizeBadge());
        }
        if (entity.getSizeBadgeType() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getSizeBadgeType());
        }
        statement.bindDouble(15, entity.getXFraction());
        statement.bindDouble(16, entity.getYFraction());
        statement.bindDouble(17, entity.getLat());
        statement.bindDouble(18, entity.getLng());
        statement.bindString(19, entity.getAnimalTypes());
        final int _tmp_1 = entity.getSizeSmall() ? 1 : 0;
        statement.bindLong(20, _tmp_1);
        final int _tmp_2 = entity.getSizeMedium() ? 1 : 0;
        statement.bindLong(21, _tmp_2);
        final int _tmp_3 = entity.getSizeLarge() ? 1 : 0;
        statement.bindLong(22, _tmp_3);
        if (entity.getIndoorText() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getIndoorText());
        }
        if (entity.getExtraFeeText() == null) {
          statement.bindNull(24);
        } else {
          statement.bindString(24, entity.getExtraFeeText());
        }
        if (entity.getRestrictionsText() == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, entity.getRestrictionsText());
        }
        if (entity.getFacilitiesText() == null) {
          statement.bindNull(26);
        } else {
          statement.bindString(26, entity.getFacilitiesText());
        }
      }
    };
    this.__insertionAdapterOfPlaceListEntity = new EntityInsertionAdapter<PlaceListEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `place_lists` (`id`,`name`,`iconType`,`isDefault`,`sortOrder`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaceListEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getIconType());
        final int _tmp = entity.isDefault() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getSortOrder());
      }
    };
    this.__insertionAdapterOfPlaceListEntity_1 = new EntityInsertionAdapter<PlaceListEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `place_lists` (`id`,`name`,`iconType`,`isDefault`,`sortOrder`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaceListEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getIconType());
        final int _tmp = entity.isDefault() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getSortOrder());
      }
    };
    this.__insertionAdapterOfListPlaceCrossRef = new EntityInsertionAdapter<ListPlaceCrossRef>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `list_place_cross_ref` (`listId`,`placeId`,`addedAt`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ListPlaceCrossRef entity) {
        statement.bindLong(1, entity.getListId());
        statement.bindLong(2, entity.getPlaceId());
        statement.bindLong(3, entity.getAddedAt());
      }
    };
    this.__insertionAdapterOfTripEntity = new EntityInsertionAdapter<TripEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `trips` (`id`,`name`,`startDate`,`endDate`,`petNames`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TripEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getStartDate());
        statement.bindLong(4, entity.getEndDate());
        statement.bindString(5, entity.getPetNames());
        statement.bindLong(6, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfTripPlaceEntity = new EntityInsertionAdapter<TripPlaceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `trip_places` (`id`,`tripId`,`dayIndex`,`placeId`,`orderIndex`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TripPlaceEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTripId());
        statement.bindLong(3, entity.getDayIndex());
        statement.bindLong(4, entity.getPlaceId());
        statement.bindLong(5, entity.getOrderIndex());
      }
    };
    this.__updateAdapterOfTripEntity = new EntityDeletionOrUpdateAdapter<TripEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `trips` SET `id` = ?,`name` = ?,`startDate` = ?,`endDate` = ?,`petNames` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TripEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getStartDate());
        statement.bindLong(4, entity.getEndDate());
        statement.bindString(5, entity.getPetNames());
        statement.bindLong(6, entity.getCreatedAt());
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteList = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM place_lists WHERE id = ? AND isDefault = 0";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveFromList = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM list_place_cross_ref WHERE listId = ? AND placeId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteTripPlace = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM trip_places WHERE id = ?";
        return _query;
      }
    };
    this.__upsertionAdapterOfJournalEntity = new EntityUpsertionAdapter<JournalEntity>(new EntityInsertionAdapter<JournalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `journals` (`id`,`tripId`,`dayIndex`,`photoUri`,`memo`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final JournalEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTripId());
        statement.bindLong(3, entity.getDayIndex());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoUri());
        }
        if (entity.getMemo() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getMemo());
        }
      }
    }, new EntityDeletionOrUpdateAdapter<JournalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `journals` SET `id` = ?,`tripId` = ?,`dayIndex` = ?,`photoUri` = ?,`memo` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final JournalEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTripId());
        statement.bindLong(3, entity.getDayIndex());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoUri());
        }
        if (entity.getMemo() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getMemo());
        }
        statement.bindLong(6, entity.getId());
      }
    });
  }

  @Override
  public Object insertPlaces(final List<PlaceEntity> places,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlaceEntity.insert(places);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertList(final PlaceListEntity list,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPlaceListEntity.insertAndReturnId(list);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertLists(final List<PlaceListEntity> lists,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlaceListEntity_1.insert(lists);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object addToList(final ListPlaceCrossRef ref,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfListPlaceCrossRef.insert(ref);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTrip(final TripEntity trip, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTripEntity.insertAndReturnId(trip);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTripPlace(final TripPlaceEntity tp,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTripPlaceEntity.insertAndReturnId(tp);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTrip(final TripEntity trip, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTripEntity.handle(trip);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteList(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteList.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteList.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object removeFromList(final long listId, final long placeId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfRemoveFromList.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, listId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, placeId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfRemoveFromList.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTripPlace(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTripPlace.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteTripPlace.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertJournal(final JournalEntity journal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfJournalEntity.upsert(journal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlace(final long id, final Continuation<? super PlaceEntity> $completion) {
    final String _sql = "SELECT * FROM places WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlaceEntity>() {
      @Override
      @Nullable
      public PlaceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCategoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryType");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfOneLiner = CursorUtil.getColumnIndexOrThrow(_cursor, "oneLiner");
          final int _cursorIndexOfOpenNow = CursorUtil.getColumnIndexOrThrow(_cursor, "openNow");
          final int _cursorIndexOfHoursText = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursText");
          final int _cursorIndexOfSpeciesBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadge");
          final int _cursorIndexOfSpeciesBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadgeType");
          final int _cursorIndexOfSizeBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadge");
          final int _cursorIndexOfSizeBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadgeType");
          final int _cursorIndexOfXFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "xFraction");
          final int _cursorIndexOfYFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "yFraction");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLng = CursorUtil.getColumnIndexOrThrow(_cursor, "lng");
          final int _cursorIndexOfAnimalTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "animalTypes");
          final int _cursorIndexOfSizeSmall = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeSmall");
          final int _cursorIndexOfSizeMedium = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeMedium");
          final int _cursorIndexOfSizeLarge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeLarge");
          final int _cursorIndexOfIndoorText = CursorUtil.getColumnIndexOrThrow(_cursor, "indoorText");
          final int _cursorIndexOfExtraFeeText = CursorUtil.getColumnIndexOrThrow(_cursor, "extraFeeText");
          final int _cursorIndexOfRestrictionsText = CursorUtil.getColumnIndexOrThrow(_cursor, "restrictionsText");
          final int _cursorIndexOfFacilitiesText = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesText");
          final PlaceEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpCategoryType;
            _tmpCategoryType = _cursor.getString(_cursorIndexOfCategoryType);
            final String _tmpRegion;
            _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpOneLiner;
            if (_cursor.isNull(_cursorIndexOfOneLiner)) {
              _tmpOneLiner = null;
            } else {
              _tmpOneLiner = _cursor.getString(_cursorIndexOfOneLiner);
            }
            final boolean _tmpOpenNow;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOpenNow);
            _tmpOpenNow = _tmp != 0;
            final String _tmpHoursText;
            if (_cursor.isNull(_cursorIndexOfHoursText)) {
              _tmpHoursText = null;
            } else {
              _tmpHoursText = _cursor.getString(_cursorIndexOfHoursText);
            }
            final String _tmpSpeciesBadge;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadge)) {
              _tmpSpeciesBadge = null;
            } else {
              _tmpSpeciesBadge = _cursor.getString(_cursorIndexOfSpeciesBadge);
            }
            final String _tmpSpeciesBadgeType;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadgeType)) {
              _tmpSpeciesBadgeType = null;
            } else {
              _tmpSpeciesBadgeType = _cursor.getString(_cursorIndexOfSpeciesBadgeType);
            }
            final String _tmpSizeBadge;
            if (_cursor.isNull(_cursorIndexOfSizeBadge)) {
              _tmpSizeBadge = null;
            } else {
              _tmpSizeBadge = _cursor.getString(_cursorIndexOfSizeBadge);
            }
            final String _tmpSizeBadgeType;
            if (_cursor.isNull(_cursorIndexOfSizeBadgeType)) {
              _tmpSizeBadgeType = null;
            } else {
              _tmpSizeBadgeType = _cursor.getString(_cursorIndexOfSizeBadgeType);
            }
            final float _tmpXFraction;
            _tmpXFraction = _cursor.getFloat(_cursorIndexOfXFraction);
            final float _tmpYFraction;
            _tmpYFraction = _cursor.getFloat(_cursorIndexOfYFraction);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            final double _tmpLng;
            _tmpLng = _cursor.getDouble(_cursorIndexOfLng);
            final String _tmpAnimalTypes;
            _tmpAnimalTypes = _cursor.getString(_cursorIndexOfAnimalTypes);
            final boolean _tmpSizeSmall;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfSizeSmall);
            _tmpSizeSmall = _tmp_1 != 0;
            final boolean _tmpSizeMedium;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfSizeMedium);
            _tmpSizeMedium = _tmp_2 != 0;
            final boolean _tmpSizeLarge;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfSizeLarge);
            _tmpSizeLarge = _tmp_3 != 0;
            final String _tmpIndoorText;
            if (_cursor.isNull(_cursorIndexOfIndoorText)) {
              _tmpIndoorText = null;
            } else {
              _tmpIndoorText = _cursor.getString(_cursorIndexOfIndoorText);
            }
            final String _tmpExtraFeeText;
            if (_cursor.isNull(_cursorIndexOfExtraFeeText)) {
              _tmpExtraFeeText = null;
            } else {
              _tmpExtraFeeText = _cursor.getString(_cursorIndexOfExtraFeeText);
            }
            final String _tmpRestrictionsText;
            if (_cursor.isNull(_cursorIndexOfRestrictionsText)) {
              _tmpRestrictionsText = null;
            } else {
              _tmpRestrictionsText = _cursor.getString(_cursorIndexOfRestrictionsText);
            }
            final String _tmpFacilitiesText;
            if (_cursor.isNull(_cursorIndexOfFacilitiesText)) {
              _tmpFacilitiesText = null;
            } else {
              _tmpFacilitiesText = _cursor.getString(_cursorIndexOfFacilitiesText);
            }
            _result = new PlaceEntity(_tmpId,_tmpName,_tmpCategory,_tmpCategoryType,_tmpRegion,_tmpAddress,_tmpPhone,_tmpOneLiner,_tmpOpenNow,_tmpHoursText,_tmpSpeciesBadge,_tmpSpeciesBadgeType,_tmpSizeBadge,_tmpSizeBadgeType,_tmpXFraction,_tmpYFraction,_tmpLat,_tmpLng,_tmpAnimalTypes,_tmpSizeSmall,_tmpSizeMedium,_tmpSizeLarge,_tmpIndoorText,_tmpExtraFeeText,_tmpRestrictionsText,_tmpFacilitiesText);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlacesByIds(final List<Long> ids,
      final Continuation<? super List<PlaceEntity>> $completion) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM places WHERE id IN (");
    final int _inputSize = ids.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (long _item : ids) {
      _statement.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaceEntity>>() {
      @Override
      @NonNull
      public List<PlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCategoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryType");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfOneLiner = CursorUtil.getColumnIndexOrThrow(_cursor, "oneLiner");
          final int _cursorIndexOfOpenNow = CursorUtil.getColumnIndexOrThrow(_cursor, "openNow");
          final int _cursorIndexOfHoursText = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursText");
          final int _cursorIndexOfSpeciesBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadge");
          final int _cursorIndexOfSpeciesBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadgeType");
          final int _cursorIndexOfSizeBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadge");
          final int _cursorIndexOfSizeBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadgeType");
          final int _cursorIndexOfXFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "xFraction");
          final int _cursorIndexOfYFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "yFraction");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLng = CursorUtil.getColumnIndexOrThrow(_cursor, "lng");
          final int _cursorIndexOfAnimalTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "animalTypes");
          final int _cursorIndexOfSizeSmall = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeSmall");
          final int _cursorIndexOfSizeMedium = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeMedium");
          final int _cursorIndexOfSizeLarge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeLarge");
          final int _cursorIndexOfIndoorText = CursorUtil.getColumnIndexOrThrow(_cursor, "indoorText");
          final int _cursorIndexOfExtraFeeText = CursorUtil.getColumnIndexOrThrow(_cursor, "extraFeeText");
          final int _cursorIndexOfRestrictionsText = CursorUtil.getColumnIndexOrThrow(_cursor, "restrictionsText");
          final int _cursorIndexOfFacilitiesText = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesText");
          final List<PlaceEntity> _result = new ArrayList<PlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaceEntity _item_1;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpCategoryType;
            _tmpCategoryType = _cursor.getString(_cursorIndexOfCategoryType);
            final String _tmpRegion;
            _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpOneLiner;
            if (_cursor.isNull(_cursorIndexOfOneLiner)) {
              _tmpOneLiner = null;
            } else {
              _tmpOneLiner = _cursor.getString(_cursorIndexOfOneLiner);
            }
            final boolean _tmpOpenNow;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOpenNow);
            _tmpOpenNow = _tmp != 0;
            final String _tmpHoursText;
            if (_cursor.isNull(_cursorIndexOfHoursText)) {
              _tmpHoursText = null;
            } else {
              _tmpHoursText = _cursor.getString(_cursorIndexOfHoursText);
            }
            final String _tmpSpeciesBadge;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadge)) {
              _tmpSpeciesBadge = null;
            } else {
              _tmpSpeciesBadge = _cursor.getString(_cursorIndexOfSpeciesBadge);
            }
            final String _tmpSpeciesBadgeType;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadgeType)) {
              _tmpSpeciesBadgeType = null;
            } else {
              _tmpSpeciesBadgeType = _cursor.getString(_cursorIndexOfSpeciesBadgeType);
            }
            final String _tmpSizeBadge;
            if (_cursor.isNull(_cursorIndexOfSizeBadge)) {
              _tmpSizeBadge = null;
            } else {
              _tmpSizeBadge = _cursor.getString(_cursorIndexOfSizeBadge);
            }
            final String _tmpSizeBadgeType;
            if (_cursor.isNull(_cursorIndexOfSizeBadgeType)) {
              _tmpSizeBadgeType = null;
            } else {
              _tmpSizeBadgeType = _cursor.getString(_cursorIndexOfSizeBadgeType);
            }
            final float _tmpXFraction;
            _tmpXFraction = _cursor.getFloat(_cursorIndexOfXFraction);
            final float _tmpYFraction;
            _tmpYFraction = _cursor.getFloat(_cursorIndexOfYFraction);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            final double _tmpLng;
            _tmpLng = _cursor.getDouble(_cursorIndexOfLng);
            final String _tmpAnimalTypes;
            _tmpAnimalTypes = _cursor.getString(_cursorIndexOfAnimalTypes);
            final boolean _tmpSizeSmall;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfSizeSmall);
            _tmpSizeSmall = _tmp_1 != 0;
            final boolean _tmpSizeMedium;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfSizeMedium);
            _tmpSizeMedium = _tmp_2 != 0;
            final boolean _tmpSizeLarge;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfSizeLarge);
            _tmpSizeLarge = _tmp_3 != 0;
            final String _tmpIndoorText;
            if (_cursor.isNull(_cursorIndexOfIndoorText)) {
              _tmpIndoorText = null;
            } else {
              _tmpIndoorText = _cursor.getString(_cursorIndexOfIndoorText);
            }
            final String _tmpExtraFeeText;
            if (_cursor.isNull(_cursorIndexOfExtraFeeText)) {
              _tmpExtraFeeText = null;
            } else {
              _tmpExtraFeeText = _cursor.getString(_cursorIndexOfExtraFeeText);
            }
            final String _tmpRestrictionsText;
            if (_cursor.isNull(_cursorIndexOfRestrictionsText)) {
              _tmpRestrictionsText = null;
            } else {
              _tmpRestrictionsText = _cursor.getString(_cursorIndexOfRestrictionsText);
            }
            final String _tmpFacilitiesText;
            if (_cursor.isNull(_cursorIndexOfFacilitiesText)) {
              _tmpFacilitiesText = null;
            } else {
              _tmpFacilitiesText = _cursor.getString(_cursorIndexOfFacilitiesText);
            }
            _item_1 = new PlaceEntity(_tmpId,_tmpName,_tmpCategory,_tmpCategoryType,_tmpRegion,_tmpAddress,_tmpPhone,_tmpOneLiner,_tmpOpenNow,_tmpHoursText,_tmpSpeciesBadge,_tmpSpeciesBadgeType,_tmpSizeBadge,_tmpSizeBadgeType,_tmpXFraction,_tmpYFraction,_tmpLat,_tmpLng,_tmpAnimalTypes,_tmpSizeSmall,_tmpSizeMedium,_tmpSizeLarge,_tmpIndoorText,_tmpExtraFeeText,_tmpRestrictionsText,_tmpFacilitiesText);
            _result.add(_item_1);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllPlaces(final Continuation<? super List<PlaceEntity>> $completion) {
    final String _sql = "SELECT * FROM places";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaceEntity>>() {
      @Override
      @NonNull
      public List<PlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCategoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryType");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfOneLiner = CursorUtil.getColumnIndexOrThrow(_cursor, "oneLiner");
          final int _cursorIndexOfOpenNow = CursorUtil.getColumnIndexOrThrow(_cursor, "openNow");
          final int _cursorIndexOfHoursText = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursText");
          final int _cursorIndexOfSpeciesBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadge");
          final int _cursorIndexOfSpeciesBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadgeType");
          final int _cursorIndexOfSizeBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadge");
          final int _cursorIndexOfSizeBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadgeType");
          final int _cursorIndexOfXFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "xFraction");
          final int _cursorIndexOfYFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "yFraction");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLng = CursorUtil.getColumnIndexOrThrow(_cursor, "lng");
          final int _cursorIndexOfAnimalTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "animalTypes");
          final int _cursorIndexOfSizeSmall = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeSmall");
          final int _cursorIndexOfSizeMedium = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeMedium");
          final int _cursorIndexOfSizeLarge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeLarge");
          final int _cursorIndexOfIndoorText = CursorUtil.getColumnIndexOrThrow(_cursor, "indoorText");
          final int _cursorIndexOfExtraFeeText = CursorUtil.getColumnIndexOrThrow(_cursor, "extraFeeText");
          final int _cursorIndexOfRestrictionsText = CursorUtil.getColumnIndexOrThrow(_cursor, "restrictionsText");
          final int _cursorIndexOfFacilitiesText = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesText");
          final List<PlaceEntity> _result = new ArrayList<PlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaceEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpCategoryType;
            _tmpCategoryType = _cursor.getString(_cursorIndexOfCategoryType);
            final String _tmpRegion;
            _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpOneLiner;
            if (_cursor.isNull(_cursorIndexOfOneLiner)) {
              _tmpOneLiner = null;
            } else {
              _tmpOneLiner = _cursor.getString(_cursorIndexOfOneLiner);
            }
            final boolean _tmpOpenNow;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOpenNow);
            _tmpOpenNow = _tmp != 0;
            final String _tmpHoursText;
            if (_cursor.isNull(_cursorIndexOfHoursText)) {
              _tmpHoursText = null;
            } else {
              _tmpHoursText = _cursor.getString(_cursorIndexOfHoursText);
            }
            final String _tmpSpeciesBadge;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadge)) {
              _tmpSpeciesBadge = null;
            } else {
              _tmpSpeciesBadge = _cursor.getString(_cursorIndexOfSpeciesBadge);
            }
            final String _tmpSpeciesBadgeType;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadgeType)) {
              _tmpSpeciesBadgeType = null;
            } else {
              _tmpSpeciesBadgeType = _cursor.getString(_cursorIndexOfSpeciesBadgeType);
            }
            final String _tmpSizeBadge;
            if (_cursor.isNull(_cursorIndexOfSizeBadge)) {
              _tmpSizeBadge = null;
            } else {
              _tmpSizeBadge = _cursor.getString(_cursorIndexOfSizeBadge);
            }
            final String _tmpSizeBadgeType;
            if (_cursor.isNull(_cursorIndexOfSizeBadgeType)) {
              _tmpSizeBadgeType = null;
            } else {
              _tmpSizeBadgeType = _cursor.getString(_cursorIndexOfSizeBadgeType);
            }
            final float _tmpXFraction;
            _tmpXFraction = _cursor.getFloat(_cursorIndexOfXFraction);
            final float _tmpYFraction;
            _tmpYFraction = _cursor.getFloat(_cursorIndexOfYFraction);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            final double _tmpLng;
            _tmpLng = _cursor.getDouble(_cursorIndexOfLng);
            final String _tmpAnimalTypes;
            _tmpAnimalTypes = _cursor.getString(_cursorIndexOfAnimalTypes);
            final boolean _tmpSizeSmall;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfSizeSmall);
            _tmpSizeSmall = _tmp_1 != 0;
            final boolean _tmpSizeMedium;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfSizeMedium);
            _tmpSizeMedium = _tmp_2 != 0;
            final boolean _tmpSizeLarge;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfSizeLarge);
            _tmpSizeLarge = _tmp_3 != 0;
            final String _tmpIndoorText;
            if (_cursor.isNull(_cursorIndexOfIndoorText)) {
              _tmpIndoorText = null;
            } else {
              _tmpIndoorText = _cursor.getString(_cursorIndexOfIndoorText);
            }
            final String _tmpExtraFeeText;
            if (_cursor.isNull(_cursorIndexOfExtraFeeText)) {
              _tmpExtraFeeText = null;
            } else {
              _tmpExtraFeeText = _cursor.getString(_cursorIndexOfExtraFeeText);
            }
            final String _tmpRestrictionsText;
            if (_cursor.isNull(_cursorIndexOfRestrictionsText)) {
              _tmpRestrictionsText = null;
            } else {
              _tmpRestrictionsText = _cursor.getString(_cursorIndexOfRestrictionsText);
            }
            final String _tmpFacilitiesText;
            if (_cursor.isNull(_cursorIndexOfFacilitiesText)) {
              _tmpFacilitiesText = null;
            } else {
              _tmpFacilitiesText = _cursor.getString(_cursorIndexOfFacilitiesText);
            }
            _item = new PlaceEntity(_tmpId,_tmpName,_tmpCategory,_tmpCategoryType,_tmpRegion,_tmpAddress,_tmpPhone,_tmpOneLiner,_tmpOpenNow,_tmpHoursText,_tmpSpeciesBadge,_tmpSpeciesBadgeType,_tmpSizeBadge,_tmpSizeBadgeType,_tmpXFraction,_tmpYFraction,_tmpLat,_tmpLng,_tmpAnimalTypes,_tmpSizeSmall,_tmpSizeMedium,_tmpSizeLarge,_tmpIndoorText,_tmpExtraFeeText,_tmpRestrictionsText,_tmpFacilitiesText);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object search(final String q, final Continuation<? super List<PlaceEntity>> $completion) {
    final String _sql = "SELECT * FROM places WHERE name LIKE '%' || ? || '%' OR category LIKE '%' || ? || '%' OR oneLiner LIKE '%' || ? || '%' OR region LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, q);
    _argIndex = 2;
    _statement.bindString(_argIndex, q);
    _argIndex = 3;
    _statement.bindString(_argIndex, q);
    _argIndex = 4;
    _statement.bindString(_argIndex, q);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaceEntity>>() {
      @Override
      @NonNull
      public List<PlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCategoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryType");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfOneLiner = CursorUtil.getColumnIndexOrThrow(_cursor, "oneLiner");
          final int _cursorIndexOfOpenNow = CursorUtil.getColumnIndexOrThrow(_cursor, "openNow");
          final int _cursorIndexOfHoursText = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursText");
          final int _cursorIndexOfSpeciesBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadge");
          final int _cursorIndexOfSpeciesBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadgeType");
          final int _cursorIndexOfSizeBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadge");
          final int _cursorIndexOfSizeBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadgeType");
          final int _cursorIndexOfXFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "xFraction");
          final int _cursorIndexOfYFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "yFraction");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLng = CursorUtil.getColumnIndexOrThrow(_cursor, "lng");
          final int _cursorIndexOfAnimalTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "animalTypes");
          final int _cursorIndexOfSizeSmall = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeSmall");
          final int _cursorIndexOfSizeMedium = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeMedium");
          final int _cursorIndexOfSizeLarge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeLarge");
          final int _cursorIndexOfIndoorText = CursorUtil.getColumnIndexOrThrow(_cursor, "indoorText");
          final int _cursorIndexOfExtraFeeText = CursorUtil.getColumnIndexOrThrow(_cursor, "extraFeeText");
          final int _cursorIndexOfRestrictionsText = CursorUtil.getColumnIndexOrThrow(_cursor, "restrictionsText");
          final int _cursorIndexOfFacilitiesText = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesText");
          final List<PlaceEntity> _result = new ArrayList<PlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaceEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpCategoryType;
            _tmpCategoryType = _cursor.getString(_cursorIndexOfCategoryType);
            final String _tmpRegion;
            _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpOneLiner;
            if (_cursor.isNull(_cursorIndexOfOneLiner)) {
              _tmpOneLiner = null;
            } else {
              _tmpOneLiner = _cursor.getString(_cursorIndexOfOneLiner);
            }
            final boolean _tmpOpenNow;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOpenNow);
            _tmpOpenNow = _tmp != 0;
            final String _tmpHoursText;
            if (_cursor.isNull(_cursorIndexOfHoursText)) {
              _tmpHoursText = null;
            } else {
              _tmpHoursText = _cursor.getString(_cursorIndexOfHoursText);
            }
            final String _tmpSpeciesBadge;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadge)) {
              _tmpSpeciesBadge = null;
            } else {
              _tmpSpeciesBadge = _cursor.getString(_cursorIndexOfSpeciesBadge);
            }
            final String _tmpSpeciesBadgeType;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadgeType)) {
              _tmpSpeciesBadgeType = null;
            } else {
              _tmpSpeciesBadgeType = _cursor.getString(_cursorIndexOfSpeciesBadgeType);
            }
            final String _tmpSizeBadge;
            if (_cursor.isNull(_cursorIndexOfSizeBadge)) {
              _tmpSizeBadge = null;
            } else {
              _tmpSizeBadge = _cursor.getString(_cursorIndexOfSizeBadge);
            }
            final String _tmpSizeBadgeType;
            if (_cursor.isNull(_cursorIndexOfSizeBadgeType)) {
              _tmpSizeBadgeType = null;
            } else {
              _tmpSizeBadgeType = _cursor.getString(_cursorIndexOfSizeBadgeType);
            }
            final float _tmpXFraction;
            _tmpXFraction = _cursor.getFloat(_cursorIndexOfXFraction);
            final float _tmpYFraction;
            _tmpYFraction = _cursor.getFloat(_cursorIndexOfYFraction);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            final double _tmpLng;
            _tmpLng = _cursor.getDouble(_cursorIndexOfLng);
            final String _tmpAnimalTypes;
            _tmpAnimalTypes = _cursor.getString(_cursorIndexOfAnimalTypes);
            final boolean _tmpSizeSmall;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfSizeSmall);
            _tmpSizeSmall = _tmp_1 != 0;
            final boolean _tmpSizeMedium;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfSizeMedium);
            _tmpSizeMedium = _tmp_2 != 0;
            final boolean _tmpSizeLarge;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfSizeLarge);
            _tmpSizeLarge = _tmp_3 != 0;
            final String _tmpIndoorText;
            if (_cursor.isNull(_cursorIndexOfIndoorText)) {
              _tmpIndoorText = null;
            } else {
              _tmpIndoorText = _cursor.getString(_cursorIndexOfIndoorText);
            }
            final String _tmpExtraFeeText;
            if (_cursor.isNull(_cursorIndexOfExtraFeeText)) {
              _tmpExtraFeeText = null;
            } else {
              _tmpExtraFeeText = _cursor.getString(_cursorIndexOfExtraFeeText);
            }
            final String _tmpRestrictionsText;
            if (_cursor.isNull(_cursorIndexOfRestrictionsText)) {
              _tmpRestrictionsText = null;
            } else {
              _tmpRestrictionsText = _cursor.getString(_cursorIndexOfRestrictionsText);
            }
            final String _tmpFacilitiesText;
            if (_cursor.isNull(_cursorIndexOfFacilitiesText)) {
              _tmpFacilitiesText = null;
            } else {
              _tmpFacilitiesText = _cursor.getString(_cursorIndexOfFacilitiesText);
            }
            _item = new PlaceEntity(_tmpId,_tmpName,_tmpCategory,_tmpCategoryType,_tmpRegion,_tmpAddress,_tmpPhone,_tmpOneLiner,_tmpOpenNow,_tmpHoursText,_tmpSpeciesBadge,_tmpSpeciesBadgeType,_tmpSizeBadge,_tmpSizeBadgeType,_tmpXFraction,_tmpYFraction,_tmpLat,_tmpLng,_tmpAnimalTypes,_tmpSizeSmall,_tmpSizeMedium,_tmpSizeLarge,_tmpIndoorText,_tmpExtraFeeText,_tmpRestrictionsText,_tmpFacilitiesText);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object placeCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM places";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ListWithCount>> observeListsWithCount() {
    final String _sql = "SELECT l.id AS id, l.name AS name, l.iconType AS iconType, l.isDefault AS isDefault, l.sortOrder AS sortOrder, (SELECT COUNT(*) FROM list_place_cross_ref x WHERE x.listId = l.id) AS placeCount FROM place_lists l ORDER BY l.sortOrder ASC, l.id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"list_place_cross_ref",
        "place_lists"}, new Callable<List<ListWithCount>>() {
      @Override
      @NonNull
      public List<ListWithCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfIconType = 2;
          final int _cursorIndexOfIsDefault = 3;
          final int _cursorIndexOfSortOrder = 4;
          final int _cursorIndexOfPlaceCount = 5;
          final List<ListWithCount> _result = new ArrayList<ListWithCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ListWithCount _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpIconType;
            _tmpIconType = _cursor.getString(_cursorIndexOfIconType);
            final boolean _tmpIsDefault;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDefault);
            _tmpIsDefault = _tmp != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final int _tmpPlaceCount;
            _tmpPlaceCount = _cursor.getInt(_cursorIndexOfPlaceCount);
            _item = new ListWithCount(_tmpId,_tmpName,_tmpIconType,_tmpIsDefault,_tmpSortOrder,_tmpPlaceCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getList(final long id, final Continuation<? super PlaceListEntity> $completion) {
    final String _sql = "SELECT * FROM place_lists WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlaceListEntity>() {
      @Override
      @Nullable
      public PlaceListEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconType = CursorUtil.getColumnIndexOrThrow(_cursor, "iconType");
          final int _cursorIndexOfIsDefault = CursorUtil.getColumnIndexOrThrow(_cursor, "isDefault");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final PlaceListEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpIconType;
            _tmpIconType = _cursor.getString(_cursorIndexOfIconType);
            final boolean _tmpIsDefault;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDefault);
            _tmpIsDefault = _tmp != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _result = new PlaceListEntity(_tmpId,_tmpName,_tmpIconType,_tmpIsDefault,_tmpSortOrder);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object listNameCount(final String name, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM place_lists WHERE name = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, name);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object maxListOrder(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(sortOrder) FROM place_lists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PlaceEntity>> observePlacesInList(final long listId) {
    final String _sql = "SELECT p.* FROM places p INNER JOIN list_place_cross_ref x ON p.id = x.placeId WHERE x.listId = ? ORDER BY x.addedAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"places",
        "list_place_cross_ref"}, new Callable<List<PlaceEntity>>() {
      @Override
      @NonNull
      public List<PlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCategoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryType");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfOneLiner = CursorUtil.getColumnIndexOrThrow(_cursor, "oneLiner");
          final int _cursorIndexOfOpenNow = CursorUtil.getColumnIndexOrThrow(_cursor, "openNow");
          final int _cursorIndexOfHoursText = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursText");
          final int _cursorIndexOfSpeciesBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadge");
          final int _cursorIndexOfSpeciesBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "speciesBadgeType");
          final int _cursorIndexOfSizeBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadge");
          final int _cursorIndexOfSizeBadgeType = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBadgeType");
          final int _cursorIndexOfXFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "xFraction");
          final int _cursorIndexOfYFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "yFraction");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLng = CursorUtil.getColumnIndexOrThrow(_cursor, "lng");
          final int _cursorIndexOfAnimalTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "animalTypes");
          final int _cursorIndexOfSizeSmall = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeSmall");
          final int _cursorIndexOfSizeMedium = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeMedium");
          final int _cursorIndexOfSizeLarge = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeLarge");
          final int _cursorIndexOfIndoorText = CursorUtil.getColumnIndexOrThrow(_cursor, "indoorText");
          final int _cursorIndexOfExtraFeeText = CursorUtil.getColumnIndexOrThrow(_cursor, "extraFeeText");
          final int _cursorIndexOfRestrictionsText = CursorUtil.getColumnIndexOrThrow(_cursor, "restrictionsText");
          final int _cursorIndexOfFacilitiesText = CursorUtil.getColumnIndexOrThrow(_cursor, "facilitiesText");
          final List<PlaceEntity> _result = new ArrayList<PlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaceEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpCategoryType;
            _tmpCategoryType = _cursor.getString(_cursorIndexOfCategoryType);
            final String _tmpRegion;
            _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpOneLiner;
            if (_cursor.isNull(_cursorIndexOfOneLiner)) {
              _tmpOneLiner = null;
            } else {
              _tmpOneLiner = _cursor.getString(_cursorIndexOfOneLiner);
            }
            final boolean _tmpOpenNow;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOpenNow);
            _tmpOpenNow = _tmp != 0;
            final String _tmpHoursText;
            if (_cursor.isNull(_cursorIndexOfHoursText)) {
              _tmpHoursText = null;
            } else {
              _tmpHoursText = _cursor.getString(_cursorIndexOfHoursText);
            }
            final String _tmpSpeciesBadge;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadge)) {
              _tmpSpeciesBadge = null;
            } else {
              _tmpSpeciesBadge = _cursor.getString(_cursorIndexOfSpeciesBadge);
            }
            final String _tmpSpeciesBadgeType;
            if (_cursor.isNull(_cursorIndexOfSpeciesBadgeType)) {
              _tmpSpeciesBadgeType = null;
            } else {
              _tmpSpeciesBadgeType = _cursor.getString(_cursorIndexOfSpeciesBadgeType);
            }
            final String _tmpSizeBadge;
            if (_cursor.isNull(_cursorIndexOfSizeBadge)) {
              _tmpSizeBadge = null;
            } else {
              _tmpSizeBadge = _cursor.getString(_cursorIndexOfSizeBadge);
            }
            final String _tmpSizeBadgeType;
            if (_cursor.isNull(_cursorIndexOfSizeBadgeType)) {
              _tmpSizeBadgeType = null;
            } else {
              _tmpSizeBadgeType = _cursor.getString(_cursorIndexOfSizeBadgeType);
            }
            final float _tmpXFraction;
            _tmpXFraction = _cursor.getFloat(_cursorIndexOfXFraction);
            final float _tmpYFraction;
            _tmpYFraction = _cursor.getFloat(_cursorIndexOfYFraction);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            final double _tmpLng;
            _tmpLng = _cursor.getDouble(_cursorIndexOfLng);
            final String _tmpAnimalTypes;
            _tmpAnimalTypes = _cursor.getString(_cursorIndexOfAnimalTypes);
            final boolean _tmpSizeSmall;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfSizeSmall);
            _tmpSizeSmall = _tmp_1 != 0;
            final boolean _tmpSizeMedium;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfSizeMedium);
            _tmpSizeMedium = _tmp_2 != 0;
            final boolean _tmpSizeLarge;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfSizeLarge);
            _tmpSizeLarge = _tmp_3 != 0;
            final String _tmpIndoorText;
            if (_cursor.isNull(_cursorIndexOfIndoorText)) {
              _tmpIndoorText = null;
            } else {
              _tmpIndoorText = _cursor.getString(_cursorIndexOfIndoorText);
            }
            final String _tmpExtraFeeText;
            if (_cursor.isNull(_cursorIndexOfExtraFeeText)) {
              _tmpExtraFeeText = null;
            } else {
              _tmpExtraFeeText = _cursor.getString(_cursorIndexOfExtraFeeText);
            }
            final String _tmpRestrictionsText;
            if (_cursor.isNull(_cursorIndexOfRestrictionsText)) {
              _tmpRestrictionsText = null;
            } else {
              _tmpRestrictionsText = _cursor.getString(_cursorIndexOfRestrictionsText);
            }
            final String _tmpFacilitiesText;
            if (_cursor.isNull(_cursorIndexOfFacilitiesText)) {
              _tmpFacilitiesText = null;
            } else {
              _tmpFacilitiesText = _cursor.getString(_cursorIndexOfFacilitiesText);
            }
            _item = new PlaceEntity(_tmpId,_tmpName,_tmpCategory,_tmpCategoryType,_tmpRegion,_tmpAddress,_tmpPhone,_tmpOneLiner,_tmpOpenNow,_tmpHoursText,_tmpSpeciesBadge,_tmpSpeciesBadgeType,_tmpSizeBadge,_tmpSizeBadgeType,_tmpXFraction,_tmpYFraction,_tmpLat,_tmpLng,_tmpAnimalTypes,_tmpSizeSmall,_tmpSizeMedium,_tmpSizeLarge,_tmpIndoorText,_tmpExtraFeeText,_tmpRestrictionsText,_tmpFacilitiesText);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object isInList(final long listId, final long placeId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM list_place_cross_ref WHERE listId = ? AND placeId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, placeId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTrip(final long id, final Continuation<? super TripEntity> $completion) {
    final String _sql = "SELECT * FROM trips WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TripEntity>() {
      @Override
      @Nullable
      public TripEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfPetNames = CursorUtil.getColumnIndexOrThrow(_cursor, "petNames");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final TripEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpPetNames;
            _tmpPetNames = _cursor.getString(_cursorIndexOfPetNames);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new TripEntity(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpPetNames,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getOngoingTrip(final long todayStart,
      final Continuation<? super TripEntity> $completion) {
    final String _sql = "SELECT * FROM trips WHERE endDate >= ? ORDER BY startDate ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, todayStart);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TripEntity>() {
      @Override
      @Nullable
      public TripEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfPetNames = CursorUtil.getColumnIndexOrThrow(_cursor, "petNames");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final TripEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpPetNames;
            _tmpPetNames = _cursor.getString(_cursorIndexOfPetNames);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new TripEntity(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpPetNames,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<TripEntity> observeOngoingTrip(final long todayStart) {
    final String _sql = "SELECT * FROM trips WHERE endDate >= ? ORDER BY startDate ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, todayStart);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trips"}, new Callable<TripEntity>() {
      @Override
      @Nullable
      public TripEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfPetNames = CursorUtil.getColumnIndexOrThrow(_cursor, "petNames");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final TripEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpPetNames;
            _tmpPetNames = _cursor.getString(_cursorIndexOfPetNames);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new TripEntity(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpPetNames,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<TripEntity>> observePastTrips(final long todayStart) {
    final String _sql = "SELECT * FROM trips WHERE endDate < ? ORDER BY startDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, todayStart);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trips"}, new Callable<List<TripEntity>>() {
      @Override
      @NonNull
      public List<TripEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfPetNames = CursorUtil.getColumnIndexOrThrow(_cursor, "petNames");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<TripEntity> _result = new ArrayList<TripEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TripEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpPetNames;
            _tmpPetNames = _cursor.getString(_cursorIndexOfPetNames);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new TripEntity(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpPetNames,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTripPlaces(final long tripId,
      final Continuation<? super List<TripPlaceEntity>> $completion) {
    final String _sql = "SELECT * FROM trip_places WHERE tripId = ? ORDER BY dayIndex, orderIndex";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tripId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TripPlaceEntity>>() {
      @Override
      @NonNull
      public List<TripPlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfDayIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "dayIndex");
          final int _cursorIndexOfPlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "placeId");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final List<TripPlaceEntity> _result = new ArrayList<TripPlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TripPlaceEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTripId;
            _tmpTripId = _cursor.getLong(_cursorIndexOfTripId);
            final int _tmpDayIndex;
            _tmpDayIndex = _cursor.getInt(_cursorIndexOfDayIndex);
            final long _tmpPlaceId;
            _tmpPlaceId = _cursor.getLong(_cursorIndexOfPlaceId);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            _item = new TripPlaceEntity(_tmpId,_tmpTripId,_tmpDayIndex,_tmpPlaceId,_tmpOrderIndex);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TripPlaceEntity>> observeTripPlaces(final long tripId) {
    final String _sql = "SELECT * FROM trip_places WHERE tripId = ? ORDER BY dayIndex, orderIndex";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tripId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trip_places"}, new Callable<List<TripPlaceEntity>>() {
      @Override
      @NonNull
      public List<TripPlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfDayIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "dayIndex");
          final int _cursorIndexOfPlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "placeId");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final List<TripPlaceEntity> _result = new ArrayList<TripPlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TripPlaceEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTripId;
            _tmpTripId = _cursor.getLong(_cursorIndexOfTripId);
            final int _tmpDayIndex;
            _tmpDayIndex = _cursor.getInt(_cursorIndexOfDayIndex);
            final long _tmpPlaceId;
            _tmpPlaceId = _cursor.getLong(_cursorIndexOfPlaceId);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            _item = new TripPlaceEntity(_tmpId,_tmpTripId,_tmpDayIndex,_tmpPlaceId,_tmpOrderIndex);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object maxOrderIndex(final long tripId, final int dayIndex,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(orderIndex) FROM trip_places WHERE tripId = ? AND dayIndex = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tripId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, dayIndex);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getJournal(final long tripId, final int dayIndex,
      final Continuation<? super JournalEntity> $completion) {
    final String _sql = "SELECT * FROM journals WHERE tripId = ? AND dayIndex = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tripId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, dayIndex);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<JournalEntity>() {
      @Override
      @Nullable
      public JournalEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfDayIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "dayIndex");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final JournalEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTripId;
            _tmpTripId = _cursor.getLong(_cursorIndexOfTripId);
            final int _tmpDayIndex;
            _tmpDayIndex = _cursor.getInt(_cursorIndexOfDayIndex);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            _result = new JournalEntity(_tmpId,_tmpTripId,_tmpDayIndex,_tmpPhotoUri,_tmpMemo);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getJournals(final long tripId,
      final Continuation<? super List<JournalEntity>> $completion) {
    final String _sql = "SELECT * FROM journals WHERE tripId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tripId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<JournalEntity>>() {
      @Override
      @NonNull
      public List<JournalEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfDayIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "dayIndex");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final List<JournalEntity> _result = new ArrayList<JournalEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final JournalEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTripId;
            _tmpTripId = _cursor.getLong(_cursorIndexOfTripId);
            final int _tmpDayIndex;
            _tmpDayIndex = _cursor.getInt(_cursorIndexOfDayIndex);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            _item = new JournalEntity(_tmpId,_tmpTripId,_tmpDayIndex,_tmpPhotoUri,_tmpMemo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
