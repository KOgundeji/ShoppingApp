package com.kunle.shoppinglistapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "general_category_list")
public class GeneralCategoryList {

    @PrimaryKey
    @NonNull
    String category;

    public GeneralCategoryList() {
    }


}
