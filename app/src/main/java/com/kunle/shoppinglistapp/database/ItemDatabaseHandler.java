package com.kunle.shoppinglistapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kunle.shoppinglistapp.models.RecyclerItem;
import com.kunle.shoppinglistapp.util.Util;

import java.util.ArrayList;

public class ItemDatabaseHandler extends SQLiteOpenHelper {

    public ItemDatabaseHandler(@Nullable Context context) {
        super(context, Util.TABLE_NAME_ITEM, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete existing table
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME_ITEM;
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        //create new table with same information
        createTable(db);
    }

    public void refreshTable() {
        //used to "start over". Delete table and creates it again
        SQLiteDatabase db = this.getWritableDatabase();
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME_ITEM;
        db.execSQL(DROP_TABLE);

        createTable(db);
    }

    public void createTable(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME_ITEM
                + "("
                + Util.ITEM_KEY_ID + " INTEGER PRIMARY KEY," //autoincrements automatically because it is the primary key
                + Util.ITEM_KEY_NAME + " TEXT,"
                + Util.ITEM_KEY_QUANTITY + " REAL,"
                + Util.ITEM_KEY_MEASUREMENT + " TEXT,"
                + Util.ITEM_KEY_LIST_TYPE + " REAL,"
                + Util.ITEM_KEY_CATEGORY + " REAL)";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    //CRUD = Create(Add), Read, Update, Delete

    public void addItem(RecyclerItem recyclerItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        //create database row from Item instance
        ContentValues values = new ContentValues();
        values.put(Util.ITEM_KEY_NAME, recyclerItem.getName());
        values.put(Util.ITEM_KEY_QUANTITY, recyclerItem.getQuantity());
        values.put(Util.ITEM_KEY_MEASUREMENT, recyclerItem.getMeasurement());
        values.put(Util.ITEM_KEY_LIST_TYPE, recyclerItem.getListType());
        values.put(Util.ITEM_KEY_CATEGORY, recyclerItem.getCategory());

        //insert database row into db
        db.insert(Util.TABLE_NAME_ITEM, null, values);
        db.close(); //close db connection
    }

    public RecyclerItem getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME_ITEM,
                new String[]{Util.ITEM_KEY_ID,Util.ITEM_KEY_NAME, Util.ITEM_KEY_QUANTITY,
                        Util.ITEM_KEY_MEASUREMENT, Util.ITEM_KEY_LIST_TYPE, Util.ITEM_KEY_CATEGORY},
                Util.ITEM_KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            RecyclerItem recyclerItem = new RecyclerItem();
            recyclerItem.setId(Integer.parseInt(cursor.getString(0)));
            recyclerItem.setName(cursor.getString(1));
            recyclerItem.setQuantity(Integer.parseInt(cursor.getString(2)));
            recyclerItem.setMeasurement(cursor.getString(3));
            recyclerItem.setListType(Integer.parseInt(cursor.getString(4)));
            recyclerItem.setCategory(cursor.getString(5));
            db.close();
            return recyclerItem;
        }
        db.close();
        return null;
    }

    public ArrayList<RecyclerItem> getAllItems() {
        ArrayList<RecyclerItem> recyclerItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //select all contacts
        String SELECT_TABLE = "SELECT * FROM " + Util.TABLE_NAME_ITEM;
        Cursor cursor = db.rawQuery(SELECT_TABLE,null);

        //Loop through data
        if (cursor.moveToFirst()) {
            do {
                RecyclerItem recyclerItem = new RecyclerItem();
                recyclerItem.setId(Integer.parseInt(cursor.getString(0)));
                recyclerItem.setName(cursor.getString(1));
                recyclerItem.setQuantity(Integer.parseInt(cursor.getString(2)));
                recyclerItem.setMeasurement(cursor.getString(3));
                recyclerItem.setListType(Integer.parseInt(cursor.getString(4)));
                recyclerItem.setCategory(cursor.getString(5));

                recyclerItemList.add(recyclerItem);
            } while (cursor.moveToNext());
        }
        return recyclerItemList;
    }

    public void updateItem(RecyclerItem recyclerItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.ITEM_KEY_NAME, recyclerItem.getName());
        values.put(Util.ITEM_KEY_QUANTITY, recyclerItem.getQuantity());
        values.put(Util.ITEM_KEY_MEASUREMENT, recyclerItem.getMeasurement());
        values.put(Util.ITEM_KEY_LIST_TYPE, recyclerItem.getListType());
        values.put(Util.ITEM_KEY_CATEGORY, recyclerItem.getCategory());

        db.update(Util.TABLE_NAME_ITEM, values,Util.ITEM_KEY_ID + "=?",
                new String[]{String.valueOf(recyclerItem.getId())});
        db.close();
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME_ITEM,Util.ITEM_KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount() {
        String SELECT_TABLE = "SELECT * FROM " + Util.TABLE_NAME_ITEM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_TABLE,null);

        return cursor.getCount();
    }
}
