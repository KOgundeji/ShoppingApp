package com.kunle.shoppinglistapp.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.data.ShoppingRepository;

import java.util.List;


//holds all of the data needed for the UI
public class ShoppingViewModel extends AndroidViewModel {

    public static ShoppingRepository repository;
    public final LiveData<List<MealWithIngredients>> allMeals;

    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingRepository(application);
        allMeals = repository.getAllMealsWithIngredients();
    }

    public LiveData<List<MealWithIngredients>> getAllMeals() {
        return allMeals;
    }

    public static void insertMeal(Meal meal) {
        repository.insertMeal(meal);
    }

    public static void deleteMeal(Meal meal) {
        repository.deleteMeal(meal);
    }


}

