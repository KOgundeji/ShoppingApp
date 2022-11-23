package com.kunle.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Item;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.util.ListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout left_nav;
    private ActionBarDrawerToggle actionBar;
    private AutoCompleteTextView add_item;
    private ImageView addButton;
    private RecyclerView innerRecycler;
    private ArrayList<Item> itemList;
    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_item = findViewById(R.id.add_item_meal);
        addButton = findViewById(R.id.add_button);
        innerRecycler = findViewById(R.id.innerRecycler);
        itemList = new ArrayList<>();


        setExample();
        setAdapter();
//        wait(3000);

        left_nav = findViewById(R.id.my_drawer_layout);
        actionBar = new ActionBarDrawerToggle(this, left_nav, R.string.nav_open, R.string.nav_close);

        left_nav.addDrawerListener(actionBar);
        actionBar.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBar.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setAddItemListener(View view) {
        view.setBackgroundColor(Color.GREEN);
    }


    private void setAdapter() {
        listAdapter = new ListAdapter(itemList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        innerRecycler.setLayoutManager(manager);
        innerRecycler.setAdapter(listAdapter);
        innerRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    private void setExample() {
        itemList.add(new Item(Item.FOOD,4,"Oranges","bunch"));
        itemList.add(new Item(Item.FOOD,1,"Eggs","dozen"));
        itemList.add(new Item(Item.FOOD,2,"Potatoes",""));
    }
}