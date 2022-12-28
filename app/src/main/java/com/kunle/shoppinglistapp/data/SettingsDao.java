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

    @Delete
    void deleteSettings(Settings settings);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateSettings(Settings settings);

    @Query("DELETE FROM settings")
    void deleteAllSettings();

    @Query("SELECT EXISTS(SELECT * FROM settings WHERE settingsName = :name)")
    LiveData<Integer> checkSettingsExists(String name);

    @Query("SELECT value FROM settings WHERE settingsName = :name")
    LiveData<Integer> checkSetting(String name);

    @Query("SELECT * FROM settings")
    LiveData<List<Settings>> getAllSettings();
}
