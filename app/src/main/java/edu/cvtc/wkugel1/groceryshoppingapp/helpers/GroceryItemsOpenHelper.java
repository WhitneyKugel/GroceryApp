package edu.cvtc.wkugel1.groceryshoppingapp.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.MealPlannerInfoEntry;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryListInfoEntry;

public class GroceryItemsOpenHelper extends SQLiteOpenHelper {
    public GroceryItemsOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String DATABASE_NAME = "groceryshoppingapp_wkugel1.db";
    public static final int DATABASE_VERSION = 6;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GroceryItemInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(GroceryItemInfoEntry.SQL_CREATE_INDEX1);

        db.execSQL(MealPlannerInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(MealPlannerInfoEntry.SQL_CREATE_INDEX1);

        db.execSQL(GroceryListInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(GroceryListInfoEntry.SQL_CREATE_INDEX1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
