package com.kunle.shoppinglistapp.util;

import java.util.List;

public class Util {

    //Database related items
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shoppingDB";
    public static final String TABLE_NAME_ITEM = "itemList";
    public static final String TABLE_NAME_FOOD = "foodList";
    public static final String TABLE_NAME_MEAL = "mealList";
    public static final String TABLE_NAME_MEASUREMENT = "measurementList"; //needs an id before name

    //Item table column names
    public static final String ITEM_KEY_ID = "id";
    public static final String ITEM_KEY_NAME = "name";
    public static final String ITEM_KEY_LIST_TYPE = "list_type";
    public static final String ITEM_KEY_QUANTITY = "quantity";
    public static final String ITEM_KEY_MEASUREMENT = "measurement";
    public static final String ITEM_KEY_CATEGORY = "category";

    //Food table column names
    public static final String FOOD_KEY_ID = "id";
    public static final String FOOD_KEY_NAME = "name";
    public static final String FOOD_KEY_QUANTITY = "quantity";
    public static final String FOOD_KEY_MEASUREMENT = "measurement";

    //Measurement table column names
    public static final String MEASUREMENT_KEY_ID = "id";
    public static final String MEASUREMENT_KEY_MEASUREMENT = "measurement";

    //Meal table column names
    public static final String MEAL_KEY_ID = "id";
    public static final String MEAL_KEY_NAME = "food_name";
    public static final String MEAL_KEY_QUANTITY = "quantity";
    public static final String MEAL_KEY_MEASUREMENT = "measurement";

}
