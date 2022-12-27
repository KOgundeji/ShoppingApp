package com.kunle.shoppinglistapp.models;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kunle.shoppinglistapp.MainActivity;
import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.data.ShoppingRepository;
import com.kunle.shoppinglistapp.util.ShoppingRoomDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//holds all of the data needed for the UI
public class ShoppingViewModel extends AndroidViewModel {

    private static ShoppingRepository repository;
    public static ArrayList<Food> temp_food_list = new ArrayList<>();
    public static MutableLiveData<ArrayList<Food>> live_food = new MutableLiveData<>(temp_food_list);

    public static LiveData<List<Food>> mainFoodList;
    public static LiveData<List<Food>> mainGroceriesList;
    public static LiveData<List<Meal>> mainMealsList;
    public static LiveData<List<MealWithIngredients>> mainMealsWithIngredientsList;
    public static List<MealFoodMap> mainMealFoodMapList;

    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingRepository(application);

        mainFoodList = getAllFood();
        mainGroceriesList = getAllGroceries();
        mainMealsList = getAllMeals();
        mainMealsWithIngredientsList = getAllMealsWithIngredients();
        mainMealFoodMapList = new ArrayList<>();

    }

    public static String[] getFoodCategories() {
        return repository.getFoodCategory();
    }

    //ALL QUERIES NEED AN OBJECT!!! Therefore they can't be static

    public static MealWithIngredients getMealsFoodList(Long mealId) {
        return repository.getSpecificMealsFoodList(mealId);
    }

    //    LiveData Methods ----------------------->

    public LiveData<List<Food>> getAllFood() {
        return repository.getAllFood();
    }

    public LiveData<List<Food>> getAllGroceries() {
        return repository.getAllGroceries();
    }

    public LiveData<List<Meal>> getAllMeals() {
        return repository.getAllMeals();
    }

    public LiveData<List<MealWithIngredients>> getAllMealsWithIngredients() {
        return repository.getAllMealsWithIngredients();
    }

    public LiveData<List<Settings>> getAllSettings() {
        return repository.getAllSettings();
    }

    public LiveData<Integer> checkSettingsExist(String name) {
        return repository.checkSettingsExists(name);
    }

    public LiveData<Integer> checkSetting(String name) {
        return repository.checkSetting(name);
    }



    //    Regular CRUD operations ----------------------->

    public static Long insertFood(Food food) {
        return repository.insertFood(food);
    }

    public static Long insertMeal(Meal meal) {
        return repository.insertMeal(meal);
    }

    public static void insertSettings(Settings settings) {
        repository.insertSetting(settings);
    }

    public static Long insertPair(MealFoodMap crossRef) {
        return repository.insertPair(crossRef);
    }



    public static void deleteFood(Food food) {
        repository.deleteFood(food);
    }


    public static void deleteMeal(Meal meal) {
        repository.deleteMeal(meal);
    }

    public static void deletePair(MealFoodMap crossRef) {
        repository.deletePair(crossRef);
    }

    public static void deleteMealWithIngredients(Long mealId) {
        repository.deleteMealWithIngredients(mealId);
    }

    public static void deleteSettings(Settings settings) {
        repository.deleteSetting(settings);
    }



    public static void updateFood(Food food) {
        repository.updateFood(food);
    }

    public static void updateMeal(Meal meal) {
        repository.updateMeal(meal);
    }

    public static void updatePair(MealFoodMap crossRef) {
        repository.updatePair(crossRef);
    }

    public static void updateSettings(Settings settings) {
        repository.updateSetting(settings);
    }



    public void deleteAllFood() {
        repository.deleteAllFood();
    }


    public void deleteAllMeals() {
        repository.deleteAllMeals();
    }

    public void deleteAllMealsWithIngredients() {
        repository.deleteAllMealsWithIngredients();
    }

    public void deleteAllSettings() {
        repository.deleteAllSettings();
    }



    public static Food getFood(String name) {
        return repository.getFood(name);
    }

    public static void getMeal(String name) {
        repository.getMeal(name);
    }

}

