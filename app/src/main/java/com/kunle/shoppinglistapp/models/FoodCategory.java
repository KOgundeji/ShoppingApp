package com.kunle.shoppinglistapp.models;

import java.util.ArrayList;

public class FoodCategory {

    private final String categoryName;
    private final ArrayList<Food> itemList;

    public FoodCategory(String categoryName, ArrayList<Food> itemList) {
        this.categoryName = categoryName;
        this.itemList = itemList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<Food> getItemList() {
        return itemList;
    }
}
