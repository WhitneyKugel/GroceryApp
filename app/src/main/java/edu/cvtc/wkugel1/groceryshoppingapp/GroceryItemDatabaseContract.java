package edu.cvtc.wkugel1.groceryshoppingapp;

import android.provider.BaseColumns;

public class GroceryItemDatabaseContract {
    private GroceryItemDatabaseContract() {}

    public static final class GroceryItemInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "grocery_info";
        public static final String COLUMN_GROCERY_ITEM = "grocery_item";
        public static final String COLUMN_GROCERY_ITEM_COST = "grocery_item_cost";

        public static final String INDEX1 = TABLE_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1 = "CREATE INDEX " + INDEX1 + " ON " +
                TABLE_NAME + "(" + COLUMN_GROCERY_ITEM + ")";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " + COLUMN_GROCERY_ITEM + " TEXT NOT NULL, " +
                COLUMN_GROCERY_ITEM_COST + " TEXT)";
    }

}
