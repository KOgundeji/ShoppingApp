package com.kunle.shoppinglistapp.models;

import java.util.List;

public class Meal {
    private List<Food> ingredients;

    public Meal() {
    }

    public Meal(List<Food> ingredients) {
        this.ingredients = ingredients;
    }
}
