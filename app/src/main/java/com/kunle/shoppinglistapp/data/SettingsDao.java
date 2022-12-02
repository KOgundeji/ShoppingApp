package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kunle.shoppinglistapp.models.Settings;

import java.util.List;

@Dao
public interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSettings(Settings settings);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateSettings(Settings settings);

    @Delete
    void deleteSettings(Settings settings);

    @Query("SELECT EXISTS(SELECT * FROM settings WHERE settingsName = :name)")
    boolean checkSettingsExists(String name);

    @Query("SELECT value FROM settings WHERE settingsName = :name Limit 1")
    boolean checkSetting(String name);

    @Query("DELETE FROM settings")
    void deleteAllSettings();

    @Query("SELECT * FROM settings")
    LiveData<List<Settings>> getAllSettings();
}
