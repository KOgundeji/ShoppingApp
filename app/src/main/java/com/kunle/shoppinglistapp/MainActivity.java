package com.kunle.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.splashscreen.SplashScreen;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);
        changeFragment(new GroceryListFragment());

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
                break;
            case R.id.nav_meals:
                changeFragment(new MealsFragment());
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