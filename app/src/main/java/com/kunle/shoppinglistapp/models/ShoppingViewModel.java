package com.kunle.shoppinglistapp.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.data.ShoppingRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//holds all of the data needed for the UI
public class ShoppingViewModel extends AndroidViewModel {

    private static ShoppingRepository repository;
    private final LiveData<List<MealWithIngredients>> allMealsWithIngredients;
    private final LiveData<List<Meal>> allMeals;
    private final LiveData<List<Food>> allFood;
    private final LiveData<List<GroceryList>> allGroceries;
    private final LiveData<List<Settings>> allSettings;
    private final LiveData<List<Category>> allCategories;
    public static ArrayList<Food> temp_food_list = new ArrayList<>();
    public static HashMap<String,String> temp_category_map = new HashMap<>();
    public static MutableLiveData<ArrayList<Food>> live_food = new MutableLiveData<>(temp_food_list);


    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingRepository(application);
        allMealsWithIngredients = repository.getAllMealsWithIngredients();
        allMeals = repository.getAllMeals();
        allFood = repository.getAllFood();
        allGroceries = repository.getAllGroceries();
        allSettings = repository.getAllSettings();
        allCategories = repository.getAllCategories();
    }

    public static String[] getFoodCategories() {
        return repository.getFoodCategory();
    }

    //ALL QUERIES NEED AN OBJECT!!! Therefore they can't be static

    public static MealWithIngredients getMealsFoodList(Long mealId) {
        return repository.getSpecificMealsFoodList(mealId);
    }

    //    LiveData Methods ----------------------->

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<Food>> getAllFood() {
        return allFood;
    }

    public LiveData<List<GroceryList>> getAllGroceries() {
        return allGroceries;
    }

    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public static LiveData<List<MealWithIngredients>> getAllMealsWithIngredients() {
        return repository.getAllMealsWithIngredients();
    }

    public LiveData<Integer> checkSettingsExist(String name) {
        return repository.checkSettingsExists(name);
    }

    public LiveData<Integer> checkSetting(String name) {
        return repository.checkSetting(name);
    }

    public LiveData<List<Settings>> getAllSettings() {
        return allSettings;
    }

    //    Regular CRUD operations ----------------------->

    public static void insertCategory(Category category) {
        repository.insertCategory(category);
    }

    public static Long insertFood(Food food) {
        return repository.insertFood(food);
    }

    public static Long insertGrocery(GroceryList item) {
        return repository.insertGroceries(item);
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



    public static void deleteCategory(Category category) {
        repository.deleteCategory(category);
    }

    public static void deleteFood(Food food) {
        repository.deleteFood(food);
    }

    public static void deleteGrocery(GroceryList item) {
        repository.deleteGroceries(item);
    }

    public static void deleteMeal(Meal meal) {
        repository.deleteMeal(meal);
    }

    public static void deletePair(MealFoodMap crossRef) {
        repository.deletePair(crossRef);
    }

    public static void deleteMealWithIngredients(long mealId) {
        repository.deleteMealWithIngredients(mealId);
    }

    public static void deleteSettings(Settings settings) {
        repository.deleteSetting(settings);
    }



    public static void updateCategory(Category category) {
        repository.updateCategory(category);
    }

    public static void updateGrocery(GroceryList item) {
        repository.updateGroceries(item);
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



    public void deleteAllCategories() {
        repository.deleteAllCategories();
    }

    public void deleteAllFood() {
        repository.deleteAllFood();
    }

    public void deleteAllGroceries() {
        repository.deleteAllGroceries();
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



    public static String getCategory(String name) {
        return repository.getCategory(name);
    }

    public static Food getFood(Long id) {
        return repository.getFood(id);
    }

    public static void getMeal(Long id) {
        repository.getMeal(id);
    }

}

