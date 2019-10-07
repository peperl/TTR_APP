package com.health.openscale.core.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class AppDatabase_Impl extends AppDatabase {
  private volatile ScaleMeasurementDAO _scaleMeasurementDAO;

  private volatile ScaleUserDAO _scaleUserDAO;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `scaleMeasurements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `enabled` INTEGER NOT NULL, `datetime` INTEGER, `weight` REAL NOT NULL, `fat` REAL NOT NULL, `water` REAL NOT NULL, `muscle` REAL NOT NULL, `visceralFat` REAL NOT NULL, `lbm` REAL NOT NULL, `waist` REAL NOT NULL, `hip` REAL NOT NULL, `bone` REAL NOT NULL, `chest` REAL NOT NULL, `thigh` REAL NOT NULL, `biceps` REAL NOT NULL, `neck` REAL NOT NULL, `caliper1` REAL NOT NULL, `caliper2` REAL NOT NULL, `caliper3` REAL NOT NULL, `comment` TEXT, FOREIGN KEY(`userId`) REFERENCES `scaleUsers`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE UNIQUE INDEX `index_scaleMeasurements_userId_datetime` ON `scaleMeasurements` (`userId`, `datetime`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `scaleUsers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL, `birthday` INTEGER NOT NULL, `bodyHeight` REAL NOT NULL, `scaleUnit` INTEGER NOT NULL, `gender` INTEGER NOT NULL, `initialWeight` REAL NOT NULL, `goalWeight` REAL NOT NULL, `goalDate` INTEGER, `measureUnit` INTEGER NOT NULL, `activityLevel` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"974ad0a810bf389300cf67b40862bb75\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `scaleMeasurements`");
        _db.execSQL("DROP TABLE IF EXISTS `scaleUsers`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsScaleMeasurements = new HashMap<String, TableInfo.Column>(21);
        _columnsScaleMeasurements.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsScaleMeasurements.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0));
        _columnsScaleMeasurements.put("enabled", new TableInfo.Column("enabled", "INTEGER", true, 0));
        _columnsScaleMeasurements.put("datetime", new TableInfo.Column("datetime", "INTEGER", false, 0));
        _columnsScaleMeasurements.put("weight", new TableInfo.Column("weight", "REAL", true, 0));
        _columnsScaleMeasurements.put("fat", new TableInfo.Column("fat", "REAL", true, 0));
        _columnsScaleMeasurements.put("water", new TableInfo.Column("water", "REAL", true, 0));
        _columnsScaleMeasurements.put("muscle", new TableInfo.Column("muscle", "REAL", true, 0));
        _columnsScaleMeasurements.put("visceralFat", new TableInfo.Column("visceralFat", "REAL", true, 0));
        _columnsScaleMeasurements.put("lbm", new TableInfo.Column("lbm", "REAL", true, 0));
        _columnsScaleMeasurements.put("waist", new TableInfo.Column("waist", "REAL", true, 0));
        _columnsScaleMeasurements.put("hip", new TableInfo.Column("hip", "REAL", true, 0));
        _columnsScaleMeasurements.put("bone", new TableInfo.Column("bone", "REAL", true, 0));
        _columnsScaleMeasurements.put("chest", new TableInfo.Column("chest", "REAL", true, 0));
        _columnsScaleMeasurements.put("thigh", new TableInfo.Column("thigh", "REAL", true, 0));
        _columnsScaleMeasurements.put("biceps", new TableInfo.Column("biceps", "REAL", true, 0));
        _columnsScaleMeasurements.put("neck", new TableInfo.Column("neck", "REAL", true, 0));
        _columnsScaleMeasurements.put("caliper1", new TableInfo.Column("caliper1", "REAL", true, 0));
        _columnsScaleMeasurements.put("caliper2", new TableInfo.Column("caliper2", "REAL", true, 0));
        _columnsScaleMeasurements.put("caliper3", new TableInfo.Column("caliper3", "REAL", true, 0));
        _columnsScaleMeasurements.put("comment", new TableInfo.Column("comment", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScaleMeasurements = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysScaleMeasurements.add(new TableInfo.ForeignKey("scaleUsers", "CASCADE", "NO ACTION",Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesScaleMeasurements = new HashSet<TableInfo.Index>(1);
        _indicesScaleMeasurements.add(new TableInfo.Index("index_scaleMeasurements_userId_datetime", true, Arrays.asList("userId","datetime")));
        final TableInfo _infoScaleMeasurements = new TableInfo("scaleMeasurements", _columnsScaleMeasurements, _foreignKeysScaleMeasurements, _indicesScaleMeasurements);
        final TableInfo _existingScaleMeasurements = TableInfo.read(_db, "scaleMeasurements");
        if (! _infoScaleMeasurements.equals(_existingScaleMeasurements)) {
          throw new IllegalStateException("Migration didn't properly handle scaleMeasurements(com.health.openscale.core.datatypes.ScaleMeasurement).\n"
                  + " Expected:\n" + _infoScaleMeasurements + "\n"
                  + " Found:\n" + _existingScaleMeasurements);
        }
        final HashMap<String, TableInfo.Column> _columnsScaleUsers = new HashMap<String, TableInfo.Column>(11);
        _columnsScaleUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsScaleUsers.put("username", new TableInfo.Column("username", "TEXT", true, 0));
        _columnsScaleUsers.put("birthday", new TableInfo.Column("birthday", "INTEGER", true, 0));
        _columnsScaleUsers.put("bodyHeight", new TableInfo.Column("bodyHeight", "REAL", true, 0));
        _columnsScaleUsers.put("scaleUnit", new TableInfo.Column("scaleUnit", "INTEGER", true, 0));
        _columnsScaleUsers.put("gender", new TableInfo.Column("gender", "INTEGER", true, 0));
        _columnsScaleUsers.put("initialWeight", new TableInfo.Column("initialWeight", "REAL", true, 0));
        _columnsScaleUsers.put("goalWeight", new TableInfo.Column("goalWeight", "REAL", true, 0));
        _columnsScaleUsers.put("goalDate", new TableInfo.Column("goalDate", "INTEGER", false, 0));
        _columnsScaleUsers.put("measureUnit", new TableInfo.Column("measureUnit", "INTEGER", true, 0));
        _columnsScaleUsers.put("activityLevel", new TableInfo.Column("activityLevel", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScaleUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesScaleUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoScaleUsers = new TableInfo("scaleUsers", _columnsScaleUsers, _foreignKeysScaleUsers, _indicesScaleUsers);
        final TableInfo _existingScaleUsers = TableInfo.read(_db, "scaleUsers");
        if (! _infoScaleUsers.equals(_existingScaleUsers)) {
          throw new IllegalStateException("Migration didn't properly handle scaleUsers(com.health.openscale.core.datatypes.ScaleUser).\n"
                  + " Expected:\n" + _infoScaleUsers + "\n"
                  + " Found:\n" + _existingScaleUsers);
        }
      }
    }, "974ad0a810bf389300cf67b40862bb75");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "scaleMeasurements","scaleUsers");
  }

  @Override
  public ScaleMeasurementDAO measurementDAO() {
    if (_scaleMeasurementDAO != null) {
      return _scaleMeasurementDAO;
    } else {
      synchronized(this) {
        if(_scaleMeasurementDAO == null) {
          _scaleMeasurementDAO = new ScaleMeasurementDAO_Impl(this);
        }
        return _scaleMeasurementDAO;
      }
    }
  }

  @Override
  public ScaleUserDAO userDAO() {
    if (_scaleUserDAO != null) {
      return _scaleUserDAO;
    } else {
      synchronized(this) {
        if(_scaleUserDAO == null) {
          _scaleUserDAO = new ScaleUserDAO_Impl(this);
        }
        return _scaleUserDAO;
      }
    }
  }
}
