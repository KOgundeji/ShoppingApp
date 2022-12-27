package com.kunle.shoppinglistapp.models;

import java.util.ArrayList;
import java.util.List;

public class FoodCategory {
    //helper class NOT in database.
    //Used the Nested Recycler View "Category Adapter" to organize Grocery items by category

    //looking back, a hashmap could've done the exact same this as this POJO

    private final String categoryName;
    private final List<Food> itemList;

    public FoodCategory(String categoryName, ArrayList<Food> itemList) {
        this.categoryName = categoryName;
        this.itemList = itemList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Food> getItemList() {
        return itemList;
    }
}
