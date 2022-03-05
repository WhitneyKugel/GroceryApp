package edu.cvtc.wkugel1.groceryshoppingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;

public class GroceryItemsOpenHelper extends SQLiteOpenHelper {
    public GroceryItemsOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String DATABASE_NAME = "groceryshoppingapp_wkugel1.db";
    public static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GroceryItemInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(GroceryItemInfoEntry.SQL_CREATE_INDEX1);

        GroceryItemsDataWorker worker = new GroceryItemsDataWorker(db);
        worker.insertCourses();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
