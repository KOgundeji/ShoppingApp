package com.kunle.shoppinglistapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Item;
import com.kunle.shoppinglistapp.util.Util;

import java.util.ArrayList;

public class FoodDatabaseHandler extends SQLiteOpenHelper {

    public FoodDatabaseHandler(@Nullable Context context) {
        super(context, Util.TABLE_NAME_FOOD, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete existing table
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME_FOOD;
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        //create new table with same information
        createTable(db);
    }

    public void refreshTable() {
        //used to "start over". Delete table and creates it again
        SQLiteDatabase db = this.getWritableDatabase();
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME_FOOD;
        db.execSQL(DROP_TABLE);

        createTable(db);
    }

    public void createTable(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME_FOOD
                + "("
                + Util.FOOD_KEY_ID + " INTEGER PRIMARY KEY," //autoincrements automatically because it is the primary key
                + Util.FOOD_KEY_NAME + " TEXT,"
                + Util.FOOD_KEY_QUANTITY + " REAL,"
                + Util.FOOD_KEY_MEASUREMENT + " TEXT)";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    //CRUD = Create(Add), Read, Update, Delete

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        //create database row from Item instance
        ContentValues values = new ContentValues();
        values.put(Util.FOOD_KEY_NAME, item.getName());
        values.put(Util.FOOD_KEY_QUANTITY, item.getQuantity());
        values.put(Util.FOOD_KEY_MEASUREMENT, item.getMeasurement());

        //insert database row into db
        db.insert(Util.TABLE_NAME_FOOD, null, values);
        db.close(); //close db connection
    }

    public Food getFood(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME_FOOD,
                new String[]{Util.FOOD_KEY_ID,Util.FOOD_KEY_NAME, Util.FOOD_KEY_QUANTITY,
                        Util.FOOD_KEY_MEASUREMENT},
                Util.FOOD_KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Food food = new Food();
            food.setId(Integer.parseInt(cursor.getString(0)));
            food.setName(cursor.getString(1));
            food.setQuantity(Integer.parseInt(cursor.getString(2)));
//            food.setMeasurements(cursor.getString(3));
            db.close();
            return food;
        }
        db.close();
        return null;
    }

    public ArrayList<Food> getAllFood() {
        ArrayList<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //select all contacts
        String SELECT_TABLE = "SELECT * FROM " + Util.TABLE_NAME_FOOD;
        Cursor cursor = db.rawQuery(SELECT_TABLE,null);

        //Loop through data
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(Integer.parseInt(cursor.getString(0)));
                food.setName(cursor.getString(1));
                food.setQuantity(Integer.parseInt(cursor.getString(2)));
//                food.setMeasurements(cursor.getString(3));

                foodList.add(food);
            } while (cursor.moveToNext());
        }
        return foodList;
    }

    public void updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.FOOD_KEY_NAME, food.getName());
        values.put(Util.FOOD_KEY_QUANTITY, food.getQuantity());
//        values.put(Util.FOOD_KEY_MEASUREMENT, food.getMeasurements());

        db.update(Util.TABLE_NAME_FOOD, values,Util.FOOD_KEY_ID + "=?",
                new String[]{String.valueOf(food.getId())});
        db.close();
    }

    public void deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME_FOOD,Util.FOOD_KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount() {
        String SELECT_TABLE = "SELECT * FROM " + Util.TABLE_NAME_FOOD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_TABLE,null);

        return cursor.getCount();
    }
}
