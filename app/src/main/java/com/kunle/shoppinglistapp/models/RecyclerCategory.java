package com.kunle.shoppinglistapp.models;

import java.util.ArrayList;

public class RecyclerCategory {

    private final String categoryName;
    private final ArrayList<RecyclerItem> itemList;

    public RecyclerCategory(String categoryName, ArrayList<RecyclerItem> itemList) {
        this.categoryName = categoryName;
        this.itemList = itemList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<RecyclerItem> getItemList() {
        return itemList;
    }
}
