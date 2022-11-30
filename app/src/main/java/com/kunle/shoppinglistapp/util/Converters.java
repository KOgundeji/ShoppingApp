package com.kunle.shoppinglistapp.util;


import androidx.lifecycle.LiveData;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromFoodArrayList(ArrayList<Food> foods) {
        if (foods == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Food>>() {
        }.getType();
        String json = gson.toJson(foods,type);
        return json;
    }

    @TypeConverter
    public ArrayList<Food> toFoodArrayList(String foodString) {
        if (foodString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        ArrayList<Food> foodArrayList = gson.fromJson(foodString,type);
        return foodArrayList;
    }

    @TypeConverter
    public String fromMealArrayList(ArrayList<Meal> meals) {
        if (meals == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Meal>>() {}.getType();
        String json = gson.toJson(meals,type);
        return json;
    }

    @TypeConverter
    public ArrayList<Meal> toMealArrayList(String mealString) {
        if (mealString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Meal>>() {}.getType();
        ArrayList<Meal> mealArrayList = gson.fromJson(mealString,type);
        return mealArrayList;
    }
}
