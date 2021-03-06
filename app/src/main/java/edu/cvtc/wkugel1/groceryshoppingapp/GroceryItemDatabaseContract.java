package edu.cvtc.wkugel1.groceryshoppingapp;

import android.provider.BaseColumns;

public class GroceryItemDatabaseContract {
    private GroceryItemDatabaseContract() {}

    // Table for adding reoccurring items to database
    public static final class GroceryItemInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "grocery_info";
        public static final String COLUMN_GROCERY_ITEM = "grocery_item";
        public static final String COLUMN_GROCERY_ITEM_COST = "grocery_item_cost";
        public static final String COLUMN_GROCERY_ITEM_AISLE = "grocery_item_aisle";
        public static final String COLUMN_GROCERY_ITEM_ADD_TO_LIST = "grocery_item_for_list";

        public static final String INDEX1 = TABLE_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1 = "CREATE INDEX " + INDEX1 + " ON " +
                TABLE_NAME + "(" + COLUMN_GROCERY_ITEM + ")";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " + COLUMN_GROCERY_ITEM + " TEXT NOT NULL, " +
                COLUMN_GROCERY_ITEM_COST + " TEXT, " + COLUMN_GROCERY_ITEM_AISLE + " TEXT, "
                + COLUMN_GROCERY_ITEM_ADD_TO_LIST + " INTEGER DEFAULT 0)";
    }

    // Table for adding reoccurring meals to database
    public static final class MealPlannerInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "menu_meal_info";
        public static final String COLUMN_MEAL_NAME = "meal_name";
        public static final String COLUMN_MEAL_DAY = "meal_day";
        public static final String COLUMN_MEAL_TYPE = "meal_type";

        public static final String INDEX1 = TABLE_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1 = "CREATE INDEX " + INDEX1 + " ON " +
                TABLE_NAME + "(" + COLUMN_MEAL_NAME + ")";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " + COLUMN_MEAL_NAME + " TEXT NOT NULL, " +
                COLUMN_MEAL_DAY + " TEXT, " + COLUMN_MEAL_TYPE + " TEXT)";

    }

    // Table for working with shopping lists in database
    public static final class GroceryListInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "grocery_list_info";
        public static final String COLUMN_GROCERY_ITEM = "grocery_item";
        public static final String COLUMN_GROCERY_ITEM_COST = "grocery_item_cost";
        public static final String COLUMN_GROCERY_ITEM_AISLE = "grocery_item_aisle";
        public static final String COLUMN_GROCERY_ITEM_IN_CART = "grocery_item_in_cart";

        public static final String INDEX1 = TABLE_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1 = "CREATE INDEX " + INDEX1 + " ON " +
                TABLE_NAME + "(" + COLUMN_GROCERY_ITEM + ")";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " + COLUMN_GROCERY_ITEM + " TEXT NOT NULL, " +
                COLUMN_GROCERY_ITEM_COST + " TEXT, " + COLUMN_GROCERY_ITEM_AISLE + " TEXT, "
                + COLUMN_GROCERY_ITEM_IN_CART + " TEXT)";

    }

}
