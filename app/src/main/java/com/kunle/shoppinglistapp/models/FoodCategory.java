package com.kunle.shoppinglistapp.models;

import java.util.ArrayList;
import java.util.List;

public class FoodCategory {

    private final String categoryName;
    private final List<GroceryList> itemList;

    public FoodCategory(String categoryName, ArrayList<GroceryList> itemList) {
        this.categoryName = categoryName;
        this.itemList = itemList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<GroceryList> getItemList() {
        return itemList;
    }
}
