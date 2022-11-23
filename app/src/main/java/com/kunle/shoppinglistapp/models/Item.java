package com.kunle.shoppinglistapp.models;

public class Item {
    public static final int FOOD = 1;
    public static final int MEAL = 2;

    private int listType, quantity, id;
    private String name, measurement;

    public Item() {
    }

    public Item(int listType, int quantity, String name, String measurement) {
        this.listType = listType;
        this.quantity = quantity;
        this.name = name;
        this.measurement = measurement;
    }

    public int getListType() {
        return listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
