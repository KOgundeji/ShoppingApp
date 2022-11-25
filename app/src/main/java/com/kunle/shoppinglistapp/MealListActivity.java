package com.kunle.shoppinglistapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.internal.NavigationMenu;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.RecyclerCategory;
import com.kunle.shoppinglistapp.util.CategoryAdapter;
import com.kunle.shoppinglistapp.util.MealAdapter;

import java.util.ArrayList;

public class MealListActivity extends AppCompatActivity {

    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;
    private DrawerLayout left_nav;
    private ActionBarDrawerToggle actionBar;
    private TextView meal_toolbar_textview;
    private Toolbar meal_toolbar;
    private RecyclerView mealRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        mealRecyclerView = findViewById(R.id.meal_recyclerView);
        meal_toolbar = findViewById(R.id.meal_toolbar);
        mealList = new ArrayList<>();
        foodList = new ArrayList<>();
//
//        left_nav = findViewById(R.id.my_drawer_layout);
//        actionBar = new ActionBarDrawerToggle(this, left_nav, R.string.nav_open, R.string.nav_close);
//        left_nav.addDrawerListener(actionBar);
//        actionBar.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        foodList.add(new Food());
        foodList.add(new Food());
        foodList.add(new Food());

        mealList.add(new Meal("Pumpkin Pie",foodList));
        setAdapter(mealList);

//        getSupportActionBar().setTitle("Meal List");


    }

    private void setAdapter(ArrayList<Meal> mealList) {
        mealAdapter = new MealAdapter(this, mealList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mealRecyclerView.setLayoutManager(manager);
        mealRecyclerView.setAdapter(mealAdapter);
    }
}