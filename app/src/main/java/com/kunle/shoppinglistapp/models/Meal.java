package com.kunle.shoppinglistapp.models;

import java.util.ArrayList;

public class Meal {
    private String name;
    private ArrayList<Food> ingredients;

    public Meal() {
    }

    public Meal(String name, ArrayList<Food> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public ArrayList<Food> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Food> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
