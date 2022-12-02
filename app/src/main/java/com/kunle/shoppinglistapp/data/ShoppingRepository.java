package com.kunle.shoppinglistapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.Settings;
import com.kunle.shoppinglistapp.util.ShoppingRoomDB;

import java.util.List;

//this is a helper class. It creates a central repository to fetch DB data. All the data you need is here.
//Its not absolutely necessary, you could fetch the data within the Activity, but organizationally,this is easier.

public class ShoppingRepository {
    private MealDao mealDao;
    private FoodDao foodDao;
    private MealWithIngredientsDao mealWithIngredientsDao;
    private GroceryListDao groceryDao;
    private SettingsDao settingsDao;
    private LiveData<List<MealWithIngredients>> allMealsWithIngredients;
    private LiveData<List<Meal>> allMeals;
    private LiveData<List<Food>> allFood;
    private LiveData<List<GroceryList>> allGroceries;
    private LiveData<List<Settings>> allSettings;

    public ShoppingRepository(Application application) {
        ShoppingRoomDB db = ShoppingRoomDB.getDatabase(application);
        mealDao = db.mealDao();
        foodDao = db.foodDao();
        mealWithIngredientsDao = db.mealWithIngredientsDao();
        groceryDao = db.groceryListDao();
        settingsDao = db.settingsDao();

        allMealsWithIngredients = mealWithIngredientsDao.getAllMeals();
        allMeals = mealDao.getAllMeals();
        allFood = foodDao.getAllFood();
        allGroceries = groceryDao.getAllGroceries();
        allSettings = settingsDao.getAllSettings();
    }

    public LiveData<List<MealWithIngredients>> getAllMealsWithIngredients() {
        return allMealsWithIngredients;
    }

    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public LiveData<List<Food>> getAllFood() {
        return allFood;
    }

    public LiveData<List<GroceryList>> getAllGroceries() {
        return allGroceries;
    }

    public LiveData<List<Settings>> getAllSettings() {
        return allSettings;
    }

    public boolean checkSettingsExists(String name) {
            return settingsDao.checkSettingsExists(name);
    }

    public boolean checkSetting(String name) {
        return settingsDao.checkSetting(name);
    }


    //    CRUD operations ----------------------->
    public void insertMeal(Meal meal) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealDao.insertMeal(meal));
    }

    public void updateMeal(Meal meal) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealDao.updateMeal(meal));
    }

    public void deleteMeal(Meal meal) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealDao.deleteMeal(meal));
    }

    public void insertFood(Food food) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> {
            foodDao.insertFood(food);
        });
    }

    public void updateFood(Food food) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> foodDao.updateFood(food));
    }

    public void deleteFood(Food food) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> foodDao.deleteFood(food));
    }

    public void deleteAllFood() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> foodDao.deleteAllFood());
    }

    public void insertGroceries(GroceryList item) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> {
            groceryDao.insertGroceryItem(item);
        });
    }

    public void updateGroceries(GroceryList item) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.updateGroceryItem(item));
    }

    public void deleteGroceries(GroceryList item) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.deleteGroceryItem(item));
    }

    public void deleteAllGroceries() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.deleteAllGroceries());
    }

    public void insertSetting(Settings settings) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> {
            settingsDao.insertSettings(settings);
        });
    }

    public void updateSetting(Settings settings) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> settingsDao.updateSettings(settings));
    }

    public void deleteSetting(Settings settings) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> settingsDao.deleteSettings(settings));
    }

    public void deleteAllSettings() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> settingsDao.deleteAllSettings());
    }






}
