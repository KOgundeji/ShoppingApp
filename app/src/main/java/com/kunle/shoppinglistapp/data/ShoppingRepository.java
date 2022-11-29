package com.kunle.shoppinglistapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.util.ShoppingRoomDB;

import java.util.List;

//this is a helper class. It creates a central repository to fetch DB data. All the data you need is here.
//Its not absolutely necessary, you could fetch the data within the Activity, but organizationally,this is easier.

public class ShoppingRepository {
    private MealDao mealDao;
    private FoodDao foodDao;
    private MealWithIngredientsDao mealWithIngredientsDao;
    private LiveData<List<MealWithIngredients>> allMealsWithIngredients;
    private LiveData<List<Meal>> allMeals;
    private List<Food> allFood;

    public ShoppingRepository(Application application) {
        ShoppingRoomDB db = ShoppingRoomDB.getDatabase(application);
        mealDao = db.mealDao();
        foodDao = db.foodDao();
        mealWithIngredientsDao = db.mealWithIngredientsDao();

        allMealsWithIngredients = mealWithIngredientsDao.getAllMeals();
        allMeals = mealDao.getAllMeals();
    }

    public LiveData<List<MealWithIngredients>> getAllMealsWithIngredients() {
        return allMealsWithIngredients;
    }

    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public void insertMeal(Meal meal) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealDao.insertMeal(meal));
    }

    public void deleteMeal(Meal meal) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealDao.deleteMeal(meal));
    }

    public void insertFood(Food food) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> foodDao.insertFood(food));
    }

    public void deleteFood(Food food) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> foodDao.deleteFood(food));
    }

    public List<Food> getAllFood() {
        return allFood;
    }
}
