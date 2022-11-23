package com.kunle.shoppinglistapp.models;

import java.util.ArrayList;

public class Meal {
    private ArrayList<Food> ingredients;

    public Meal() {
    }

    public Meal(ArrayList<Food> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Food> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Food> ingredients) {
        this.ingredients = ingredients;
    }
}
