package com.kunle.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.RecyclerCategory;
import com.kunle.shoppinglistapp.models.RecyclerItem;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.util.CategoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout left_nav;
    private ActionBarDrawerToggle actionBar;
    private AutoCompleteTextView add_item;
    private ImageView addButton;
    private RecyclerView outerRecycler;
    private CategoryAdapter categoryAdapter;
    private Map<String, ArrayList<RecyclerItem>> categoryMap;
    private ArrayList<RecyclerItem> recyclerItemList;
    private ArrayList<RecyclerCategory> recyclerCategories;
    private ArrayList<String> categoryList;
    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setExample();
        setAdapter(setRecyclerCategoryList());
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

    private void initialize() {
        add_item = findViewById(R.id.add_item_meal);
        addButton = findViewById(R.id.add_button);
        outerRecycler = findViewById(R.id.outerRecycler);
        recyclerItemList = new ArrayList<>();
        recyclerCategories = new ArrayList<>();

        categoryMap = new HashMap<>();
        categoryList = new ArrayList<>();
        init_categoryList(categoryList);
    }

    private ArrayList<RecyclerCategory> setRecyclerCategoryList() {

        for (RecyclerItem item : recyclerItemList) {
            String category = item.getCategory();
            if (categoryMap.get(category) != null) {
                ArrayList<RecyclerItem> itemList = categoryMap.get(category);
                itemList.add(item);
                categoryMap.replace(category,itemList);
            } else {
                ArrayList<RecyclerItem> itemList = new ArrayList<>();
                itemList.add(item);
                categoryMap.put(category,itemList);
            }
        }

        for (String category : categoryMap.keySet()) {
            RecyclerCategory cat = new RecyclerCategory(category,categoryMap.get(category));
            recyclerCategories.add(cat);
        }

        return recyclerCategories;
    }



    public void setAddItemListener(View view) {
        view.setBackgroundColor(Color.GREEN);
    }


    private void setAdapter(ArrayList<RecyclerCategory> categories) {
        categoryAdapter = new CategoryAdapter(this, categories);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        outerRecycler.setLayoutManager(manager);
        outerRecycler.setAdapter(categoryAdapter);
    }

    private void setExample() {
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 4, "Oranges",
                "bunch","Fruit"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 4, "Pineapple",
                "bunch","Fruit"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Eggs",
                "dozen","Dairy"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Cheese",
                "dozen","Dairy"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 2, "Pasta",
                "boxes","Bread/Grains"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Tissues",
                "box","For the Home"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 3, "Potatoes",
                "","Produce"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Strawberries",
                "carton","Fruit"));


    }

    private void init_categoryList(ArrayList<String> categoryList) {
        categoryList.add("Produce");
        categoryList.add("Fruit");
        categoryList.add("Meat/Fish");
        categoryList.add("Condiments");
        categoryList.add("Beverages");
        categoryList.add("Snacks");
        categoryList.add("Pet Supplies");
        categoryList.add("Baking/Spices");
        categoryList.add("Bread/Grains");
        categoryList.add("Dairy");
        categoryList.add("Frozen Food");
        categoryList.add("Canned Goods");
        categoryList.add("For the Home");
        categoryList.add("Toiletries");
    }

    public void toMealPage(MenuItem item) {
        Intent intent = new Intent(this, MealListActivity.class);
        startActivity(intent);
    }
}