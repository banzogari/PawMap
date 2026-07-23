package com.pawmap.app.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.pawmap.app.data.dao.PawDao;
import com.pawmap.app.data.dao.PawDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PawDao _pawDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `places` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `categoryType` TEXT NOT NULL, `region` TEXT NOT NULL, `address` TEXT NOT NULL, `phone` TEXT, `oneLiner` TEXT, `openNow` INTEGER NOT NULL, `hoursText` TEXT, `speciesBadge` TEXT, `speciesBadgeType` TEXT, `sizeBadge` TEXT, `sizeBadgeType` TEXT, `xFraction` REAL NOT NULL, `yFraction` REAL NOT NULL, `lat` REAL NOT NULL, `lng` REAL NOT NULL, `animalTypes` TEXT NOT NULL, `sizeSmall` INTEGER NOT NULL, `sizeMedium` INTEGER NOT NULL, `sizeLarge` INTEGER NOT NULL, `indoorText` TEXT, `extraFeeText` TEXT, `restrictionsText` TEXT, `facilitiesText` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `place_lists` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `iconType` TEXT NOT NULL, `isDefault` INTEGER NOT NULL, `sortOrder` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `list_place_cross_ref` (`listId` INTEGER NOT NULL, `placeId` INTEGER NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`listId`, `placeId`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_list_place_cross_ref_placeId` ON `list_place_cross_ref` (`placeId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trips` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER NOT NULL, `petNames` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trip_places` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tripId` INTEGER NOT NULL, `dayIndex` INTEGER NOT NULL, `placeId` INTEGER NOT NULL, `orderIndex` INTEGER NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_trip_places_tripId` ON `trip_places` (`tripId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `journals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tripId` INTEGER NOT NULL, `dayIndex` INTEGER NOT NULL, `photoUri` TEXT, `memo` TEXT)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_journals_tripId_dayIndex` ON `journals` (`tripId`, `dayIndex`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '19dd2158105fb5b273eb2d0b1eb33b05')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `places`");
        db.execSQL("DROP TABLE IF EXISTS `place_lists`");
        db.execSQL("DROP TABLE IF EXISTS `list_place_cross_ref`");
        db.execSQL("DROP TABLE IF EXISTS `trips`");
        db.execSQL("DROP TABLE IF EXISTS `trip_places`");
        db.execSQL("DROP TABLE IF EXISTS `journals`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPlaces = new HashMap<String, TableInfo.Column>(26);
        _columnsPlaces.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("categoryType", new TableInfo.Column("categoryType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("region", new TableInfo.Column("region", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("oneLiner", new TableInfo.Column("oneLiner", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("openNow", new TableInfo.Column("openNow", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("hoursText", new TableInfo.Column("hoursText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("speciesBadge", new TableInfo.Column("speciesBadge", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("speciesBadgeType", new TableInfo.Column("speciesBadgeType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("sizeBadge", new TableInfo.Column("sizeBadge", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("sizeBadgeType", new TableInfo.Column("sizeBadgeType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("xFraction", new TableInfo.Column("xFraction", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("yFraction", new TableInfo.Column("yFraction", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("lat", new TableInfo.Column("lat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("lng", new TableInfo.Column("lng", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("animalTypes", new TableInfo.Column("animalTypes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("sizeSmall", new TableInfo.Column("sizeSmall", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("sizeMedium", new TableInfo.Column("sizeMedium", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("sizeLarge", new TableInfo.Column("sizeLarge", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("indoorText", new TableInfo.Column("indoorText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("extraFeeText", new TableInfo.Column("extraFeeText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("restrictionsText", new TableInfo.Column("restrictionsText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("facilitiesText", new TableInfo.Column("facilitiesText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaces = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaces = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaces = new TableInfo("places", _columnsPlaces, _foreignKeysPlaces, _indicesPlaces);
        final TableInfo _existingPlaces = TableInfo.read(db, "places");
        if (!_infoPlaces.equals(_existingPlaces)) {
          return new RoomOpenHelper.ValidationResult(false, "places(com.pawmap.app.data.entity.PlaceEntity).\n"
                  + " Expected:\n" + _infoPlaces + "\n"
                  + " Found:\n" + _existingPlaces);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaceLists = new HashMap<String, TableInfo.Column>(5);
        _columnsPlaceLists.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaceLists.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaceLists.put("iconType", new TableInfo.Column("iconType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaceLists.put("isDefault", new TableInfo.Column("isDefault", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaceLists.put("sortOrder", new TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaceLists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaceLists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaceLists = new TableInfo("place_lists", _columnsPlaceLists, _foreignKeysPlaceLists, _indicesPlaceLists);
        final TableInfo _existingPlaceLists = TableInfo.read(db, "place_lists");
        if (!_infoPlaceLists.equals(_existingPlaceLists)) {
          return new RoomOpenHelper.ValidationResult(false, "place_lists(com.pawmap.app.data.entity.PlaceListEntity).\n"
                  + " Expected:\n" + _infoPlaceLists + "\n"
                  + " Found:\n" + _existingPlaceLists);
        }
        final HashMap<String, TableInfo.Column> _columnsListPlaceCrossRef = new HashMap<String, TableInfo.Column>(3);
        _columnsListPlaceCrossRef.put("listId", new TableInfo.Column("listId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListPlaceCrossRef.put("placeId", new TableInfo.Column("placeId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListPlaceCrossRef.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListPlaceCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesListPlaceCrossRef = new HashSet<TableInfo.Index>(1);
        _indicesListPlaceCrossRef.add(new TableInfo.Index("index_list_place_cross_ref_placeId", false, Arrays.asList("placeId"), Arrays.asList("ASC")));
        final TableInfo _infoListPlaceCrossRef = new TableInfo("list_place_cross_ref", _columnsListPlaceCrossRef, _foreignKeysListPlaceCrossRef, _indicesListPlaceCrossRef);
        final TableInfo _existingListPlaceCrossRef = TableInfo.read(db, "list_place_cross_ref");
        if (!_infoListPlaceCrossRef.equals(_existingListPlaceCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "list_place_cross_ref(com.pawmap.app.data.entity.ListPlaceCrossRef).\n"
                  + " Expected:\n" + _infoListPlaceCrossRef + "\n"
                  + " Found:\n" + _existingListPlaceCrossRef);
        }
        final HashMap<String, TableInfo.Column> _columnsTrips = new HashMap<String, TableInfo.Column>(6);
        _columnsTrips.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endDate", new TableInfo.Column("endDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("petNames", new TableInfo.Column("petNames", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrips = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrips = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrips = new TableInfo("trips", _columnsTrips, _foreignKeysTrips, _indicesTrips);
        final TableInfo _existingTrips = TableInfo.read(db, "trips");
        if (!_infoTrips.equals(_existingTrips)) {
          return new RoomOpenHelper.ValidationResult(false, "trips(com.pawmap.app.data.entity.TripEntity).\n"
                  + " Expected:\n" + _infoTrips + "\n"
                  + " Found:\n" + _existingTrips);
        }
        final HashMap<String, TableInfo.Column> _columnsTripPlaces = new HashMap<String, TableInfo.Column>(5);
        _columnsTripPlaces.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripPlaces.put("tripId", new TableInfo.Column("tripId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripPlaces.put("dayIndex", new TableInfo.Column("dayIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripPlaces.put("placeId", new TableInfo.Column("placeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripPlaces.put("orderIndex", new TableInfo.Column("orderIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTripPlaces = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTripPlaces = new HashSet<TableInfo.Index>(1);
        _indicesTripPlaces.add(new TableInfo.Index("index_trip_places_tripId", false, Arrays.asList("tripId"), Arrays.asList("ASC")));
        final TableInfo _infoTripPlaces = new TableInfo("trip_places", _columnsTripPlaces, _foreignKeysTripPlaces, _indicesTripPlaces);
        final TableInfo _existingTripPlaces = TableInfo.read(db, "trip_places");
        if (!_infoTripPlaces.equals(_existingTripPlaces)) {
          return new RoomOpenHelper.ValidationResult(false, "trip_places(com.pawmap.app.data.entity.TripPlaceEntity).\n"
                  + " Expected:\n" + _infoTripPlaces + "\n"
                  + " Found:\n" + _existingTripPlaces);
        }
        final HashMap<String, TableInfo.Column> _columnsJournals = new HashMap<String, TableInfo.Column>(5);
        _columnsJournals.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournals.put("tripId", new TableInfo.Column("tripId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournals.put("dayIndex", new TableInfo.Column("dayIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournals.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournals.put("memo", new TableInfo.Column("memo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysJournals = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesJournals = new HashSet<TableInfo.Index>(1);
        _indicesJournals.add(new TableInfo.Index("index_journals_tripId_dayIndex", true, Arrays.asList("tripId", "dayIndex"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoJournals = new TableInfo("journals", _columnsJournals, _foreignKeysJournals, _indicesJournals);
        final TableInfo _existingJournals = TableInfo.read(db, "journals");
        if (!_infoJournals.equals(_existingJournals)) {
          return new RoomOpenHelper.ValidationResult(false, "journals(com.pawmap.app.data.entity.JournalEntity).\n"
                  + " Expected:\n" + _infoJournals + "\n"
                  + " Found:\n" + _existingJournals);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "19dd2158105fb5b273eb2d0b1eb33b05", "f0173c81ee74168309376f70d65621fd");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "places","place_lists","list_place_cross_ref","trips","trip_places","journals");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `places`");
      _db.execSQL("DELETE FROM `place_lists`");
      _db.execSQL("DELETE FROM `list_place_cross_ref`");
      _db.execSQL("DELETE FROM `trips`");
      _db.execSQL("DELETE FROM `trip_places`");
      _db.execSQL("DELETE FROM `journals`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PawDao.class, PawDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PawDao pawDao() {
    if (_pawDao != null) {
      return _pawDao;
    } else {
      synchronized(this) {
        if(_pawDao == null) {
          _pawDao = new PawDao_Impl(this);
        }
        return _pawDao;
      }
    }
  }
}
