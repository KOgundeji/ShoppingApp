package com.kunle.shoppinglistapp.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.data.ShoppingRepository;

import java.util.ArrayList;
import java.util.List;


//holds all of the data needed for the UI
public class ShoppingViewModel extends AndroidViewModel {

    private static ShoppingRepository repository;
    private final ArrayList<MealWithIngredients> allMealsWithIngredients;
    private final LiveData<List<Meal>> allMeals;
    private final ArrayList<Food> allFood;
    private final LiveData<List<GroceryList>> allGroceries;

    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingRepository(application);
        allMealsWithIngredients = repository.getAllMealsWithIngredients();
        allMeals = repository.getAllMeals();
        allFood = repository.getAllFood();
        allGroceries = repository.getAllGroceries();
    }

    public ArrayList<MealWithIngredients> getAllMealsWithIngredients() {
        return allMealsWithIngredients;
    }

    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public ArrayList<Food> getAllFood() {
        return allFood;
    }

    public LiveData<List<GroceryList>> getAllGroceries() {
        return allGroceries;
    }

    public static void insertMeal(Meal meal) {
        repository.insertMeal(meal);
    }

    public static void updateMeal(Meal meal) {
        repository.updateMeal(meal);
    }

    public static void deleteMeal(Meal meal) {
        repository.deleteMeal(meal);
    }

    public static void insertFood(Food food) {
        repository.insertFood(food);
    }

    public static void updateFood(Food food) {
        repository.updateFood(food);
    }

    public static void deleteFood(Food food) {
        repository.deleteFood(food);
    }

    public static void insertGroceries(GroceryList item) {
        repository.insertGroceries(item);
    }

    public static void updateGroceries(GroceryList item) {
        repository.updateGroceries(item);
    }

    public static void deleteGroceries(GroceryList item) {
        repository.deleteGroceries(item);
    }

    public static void deleteAllGroceries() {
        repository.deleteAllGroceries();
    }
}

