package com.kunle.shoppinglistapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.util.ShoppingRoomDB;

import java.util.ArrayList;
import java.util.List;

//this is a helper class. It creates a central repository to fetch DB data. All the data you need is here.
//Its not absolutely necessary, you could fetch the data within the Activity, but organizationally,this is easier.

public class ShoppingRepository {
    private MealDao mealDao;
    private FoodDao foodDao;
    private MealWithIngredientsDao mealWithIngredientsDao;
    private GroceryListDao groceryDao;
    private ArrayList<MealWithIngredients> allMealsWithIngredients;
    private LiveData<List<Meal>> allMeals;
    private ArrayList<Food> allFood;
    private LiveData<List<GroceryList>> allGroceries;

    public ShoppingRepository(Application application) {
        ShoppingRoomDB db = ShoppingRoomDB.getDatabase(application);
        mealDao = db.mealDao();
        foodDao = db.foodDao();
        mealWithIngredientsDao = db.mealWithIngredientsDao();
        groceryDao = db.groceryListDao();

        allMealsWithIngredients = new ArrayList<MealWithIngredients> (mealWithIngredientsDao.getAllMeals());
        allMeals = mealDao.getAllMeals();
        allFood = new ArrayList<Food>(foodDao.getAllFood());
        allGroceries = groceryDao.getAllGroceries();
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

    public void insertGroceries(GroceryList item) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> {
            groceryDao.insertGroceryItem(item);
        });
    }

    public void updateGroceries(GroceryList item) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.insertGroceryItem(item));
    }

    public void deleteGroceries(GroceryList item) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.deleteGroceryItem(item));
    }

    public void deleteAllGroceries() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.deleteAllGroceries());
    }



}
