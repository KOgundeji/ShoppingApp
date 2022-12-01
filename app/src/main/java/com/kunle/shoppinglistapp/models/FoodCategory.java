package com.kunle.shoppinglistapp.models;

import java.util.ArrayList;

public class FoodCategory {

    private final String categoryName;
    private final ArrayList<GroceryList> itemList;

    public FoodCategory(String categoryName, ArrayList<GroceryList> itemList) {
        this.categoryName = categoryName;
        this.itemList = itemList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<GroceryList> getItemList() {
        return itemList;
    }
}
