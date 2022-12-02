package com.kunle.shoppinglistapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings")
public class Settings {

    @PrimaryKey
    @NonNull
    private String settingsName;
    @ColumnInfo
    private boolean value;

    public Settings(@NonNull String settingsName, boolean value) {
        this.settingsName = settingsName;
        this.value = value;
    }

    public String getSettingsName() {
        return settingsName;
    }

    public void setSettingsName(String settingsName) {
        this.settingsName = settingsName;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
