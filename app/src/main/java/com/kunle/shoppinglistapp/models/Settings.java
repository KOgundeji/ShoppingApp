package com.kunle.shoppinglistapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings")
public class Settings {

    public static final String DARK_MODE = "dark_mode";
    public static final String SCREEN_ON = "screen_on";
    public static final String NO_CATEGORIES = "remove_categories";

    @PrimaryKey
    @NonNull
    private String settingsName;
    @ColumnInfo
    private int value;

    public Settings(@NonNull String settingsName, int value) {
        this.settingsName = settingsName;
        this.value = value;
    }

    public String getSettingsName() {
        return settingsName;
    }

    public void setSettingsName(String settingsName) {
        this.settingsName = settingsName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
