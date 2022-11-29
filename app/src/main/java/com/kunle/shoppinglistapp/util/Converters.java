package com.kunle.shoppinglistapp.util;


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
    public String fromFoodArrayList(List<Food> foods) {
        if (foods == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food>>() {
        }.getType();
        String json = gson.toJson(foods,type);
        return json;
    }

    @TypeConverter
    public List<Food> toFoodArrayList(String foodString) {
        if (foodString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food>>() {}.getType();
        List<Food> foodArrayList = gson.fromJson(foodString,type);
        return foodArrayList;
    }

    @TypeConverter
    public String fromMealArrayList(List<Meal> meals) {
        if (meals == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Meal>>() {}.getType();
        String json = gson.toJson(meals,type);
        return json;
    }

    @TypeConverter
    public List<Meal> toMealArrayList(String mealString) {
        if (mealString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Meal>>() {}.getType();
        List<Meal> mealArrayList = gson.fromJson(mealString,type);
        return mealArrayList;
    }
}
