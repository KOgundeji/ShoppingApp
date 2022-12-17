package com.kunle.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.splashscreen.SplashScreen;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationBarView;
import com.kunle.shoppinglistapp.data.SettingsDao;
import com.kunle.shoppinglistapp.databinding.ActivityMainBinding;
import com.kunle.shoppinglistapp.models.Settings;
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

        final int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            ShoppingViewModel.insertSettings(new Settings(Settings.DARK_MODE, 1));
        } else if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            ShoppingViewModel.insertSettings(new Settings(Settings.DARK_MODE, 0));
        } else {
        }

        ShoppingViewModel.insertSettings(new Settings(Settings.SCREEN_ON,0));
        ShoppingViewModel.insertSettings(new Settings(Settings.NO_CATEGORIES,0));

        viewModel.checkSetting(Settings.SCREEN_ON).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            }
        });

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