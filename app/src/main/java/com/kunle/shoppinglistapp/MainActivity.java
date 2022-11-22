package com.kunle.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout left_nav;
    private ActionBarDrawerToggle actionBar;
    private TextInputLayout shoppingList_item;
    private CheckBox checkBox;
    private AutoCompleteTextView add_item;
    private ImageView addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        shoppingList_item = findViewById(R.id.shoppingList_item);
        checkBox = findViewById(R.id.checkBox);
        add_item = findViewById(R.id.add_item_meal);


//        wait(3000);
        setContentView(R.layout.activity_main);
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

    private void setEndIconListener() {
        shoppingList_item.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(Color.YELLOW);
            }
        });
    }

    private void setCheckBoxListener() {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(Color.RED);
            }
        });
    }

    public void setAddItemListener(View view) {
        view.setBackgroundColor(Color.GREEN);
    }
}