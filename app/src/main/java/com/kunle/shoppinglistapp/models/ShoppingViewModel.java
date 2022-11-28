package com.kunle.shoppinglistapp.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kunle.shoppinglistapp.data.ShoppingRepository;

import java.util.List;


//holds all of the data needed for the UI
public class ShoppingViewModel extends AndroidViewModel {

    public static ShoppingRepository respository;
    public final LiveData<List<Meal>> allMeals;

    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        respository = new ShoppingRepository(application);
        allMeals = respository.getAllData();
    }

    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public static void insert(Meal meal) {
        respository.insert(meal);
    }
}

