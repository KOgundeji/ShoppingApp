package com.kunle.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.splashscreen.SplashScreen;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;


import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kunle.shoppinglistapp.databinding.ActivityMainBinding;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding bind;
    private ShoppingViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
//        File databaseDir = new File(getApplicationContext().getApplicationInfo().dataDir + "/databases");
//        new File(databaseDir,"shopping_database.db").delete();
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        viewModel = new ShoppingViewModel(this.getApplication());

        if (viewModel.checkSetting("screen_on")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }


        changeFragment(new GroceryListFragment());
        setTitle("Grocery List");






        bind.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                changeActivity(item);
                return true;
            }
        });

    }

    private void changeActivity(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_grocery_list:
                changeFragment(new GroceryListFragment());
                setTitle("Grocery List");
                break;
            case R.id.nav_meals:
                changeFragment(new MealsFragment());
                setTitle("Meals");
                break;
            case R.id.nav_settings:
                changeFragment(new SettingsFragment());
                setTitle("Settings");
                break;
            default:
        }
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();
        fTransaction.replace(R.id.fragment_container, fragment);
        fTransaction.commit();
    }


}