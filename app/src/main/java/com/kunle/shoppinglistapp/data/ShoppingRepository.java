package com.kunle.shoppinglistapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kunle.shoppinglistapp.models.Category;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.MealFoodMap;
import com.kunle.shoppinglistapp.models.Settings;
import com.kunle.shoppinglistapp.util.ShoppingRoomDB;

import java.util.Arrays;
import java.util.List;

//this is a helper class. It creates a central repository to fetch DB data. All the data you need is here.
//Its not absolutely necessary, you could fetch the data within the Activity, but organizationally,this is easier.

public class ShoppingRepository {
    private MealDao mealDao;
    private FoodDao foodDao;
    private MealWithIngredientsDao mealWithIngredientsDao;
    private GroceryListDao groceryDao;
    private SettingsDao settingsDao;
    private CategoryDao categoryDao;
    private LiveData<List<MealWithIngredients>> allMealsWithIngredients;
    private LiveData<List<Meal>> allMeals;
    private LiveData<List<Food>> allFood;
    private LiveData<List<GroceryList>> allGroceries;
    private LiveData<List<Settings>> allSettings;
    private LiveData<List<Category>> allCategories;
    private final String[] categoryNames = {"Produce", "Fruit", "Meat/Fish", "Condiments", "Beverages", "Snacks",
            "Pet Supplies", "Baking/Spices", "Bread/Grains", "Dairy", "Frozen Food", "Canned Goods", "For the Home",
            "Toiletries", "Uncategorized"};

    public ShoppingRepository(Application application) {
        ShoppingRoomDB db = ShoppingRoomDB.getDatabase(application);
        mealDao = db.mealDao();
        foodDao = db.foodDao();
        mealWithIngredientsDao = db.mealWithIngredientsDao();
        groceryDao = db.groceryListDao();
        settingsDao = db.settingsDao();
        categoryDao = db.foodCategoryDao();

        allMealsWithIngredients = mealWithIngredientsDao.getAllMealsWithIngredients();
        allMeals = mealDao.getAllMeals();
        allFood = foodDao.getAllFood();
        allGroceries = groceryDao.getAllGroceries();
        allSettings = settingsDao.getAllSettings();
        allCategories = categoryDao.getAllCategories();

        Arrays.sort(categoryNames);
    }

    public String[] getFoodCategory() {
        return categoryNames;
    }

    //    LiveData methods ----------------------->

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<Food>> getAllFood() {
        return allFood;
    }

    public LiveData<List<GroceryList>> getAllGroceries() {
        return allGroceries;
    }

    public LiveData<List<MealWithIngredients>> getAllMealsWithIngredients() {
        return allMealsWithIngredients;
    }

    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public LiveData<List<Settings>> getAllSettings() {
        return allSettings;
    }

    public LiveData<Integer> checkSettingsExists(String name) {
        return settingsDao.checkSettingsExists(name);
    }

    public LiveData<Integer> checkSetting(String name) {
        return settingsDao.checkSetting(name);
    }

    //    Methods that have parameters and return classes (ie methods that have return specific instances)

    public String getCategory(String name) {
        return categoryDao.getCategory(name);
    }

    public Food getFood(Long id) {
        return foodDao.getFood(id);
    }

    public Meal getMeal(Long id) {
        return mealDao.getMeal(id);
    }

    public MealWithIngredients getSpecificMealsFoodList(Long mealId) {
        return mealWithIngredientsDao.getSpecificMealWithIngredients(mealId);
    }

    //    Regular CRUD operations ----------------------->

    public void insertCategory(Category category) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> {
            categoryDao.insertCategory(category);
        });
    }

    public long insertFood(Food food) {
        return foodDao.insertFood(food);
    }

    public long insertGroceries(GroceryList item) {
        return groceryDao.insertGroceryItem(item);
    }

    public long insertMeal(Meal meal) {
        return mealDao.insertMeal(meal);
    }

    public long insertPair(MealFoodMap crossRef) {
        return mealWithIngredientsDao.insertPair(crossRef);
    }

    public void insertSetting(Settings settings) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> settingsDao.insertSettings(settings));
    }




    public void deleteCategory(Category category) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> categoryDao.deleteCategory(category));
    }

    public void deleteGroceries(GroceryList item) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.deleteGroceryItem(item));
    }

    public void deleteFood(Food food) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> foodDao.deleteFood(food));
    }

    public void deleteMeal(Meal meal) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealDao.deleteMeal(meal));
    }

    public void deletePair(MealFoodMap crossRef) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealWithIngredientsDao.deletePair(crossRef));
    }

    public void deleteMealWithIngredients (long mealId) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealWithIngredientsDao.deleteSpecificMealIngredients(mealId));
    }

    public void deleteSetting(Settings settings) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> settingsDao.deleteSettings(settings));
    }




    public void deleteAllCategories() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> categoryDao.deleteAllCategories());
    }

    public void deleteAllFood() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> foodDao.deleteAllFood());
    }

    public void deleteAllGroceries() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.deleteAllGroceries());
    }

    public void deleteAllMeals() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealDao.deleteAllMeals());
    }

    public void deleteAllMealsWithIngredients() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealWithIngredientsDao.deleteAllMealWithIngredients());
    }

    public void deleteAllSettings() {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> settingsDao.deleteAllSettings());
    }




    public void updateCategory(Category category) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> categoryDao.updateCategory(category));
    }

    public void updateFood(Food food) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> foodDao.updateFood(food));
    }

    public void updateGroceries(GroceryList item) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> groceryDao.updateGroceryItem(item));
    }

    public void updateMeal(Meal meal) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealDao.updateMeal(meal));
    }

    public void updatePair(MealFoodMap crossRef) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> mealWithIngredientsDao.updatePair(crossRef));
    }

    public void updateSetting(Settings settings) {
        ShoppingRoomDB.databaseWriteExecutor.execute(() -> settingsDao.updateSettings(settings));
    }
}
