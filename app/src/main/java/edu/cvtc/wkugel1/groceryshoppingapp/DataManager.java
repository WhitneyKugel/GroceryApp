package edu.cvtc.wkugel1.groceryshoppingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;

public class DataManager {

    private static DataManager ourInstance = null;
    private List<GroceryItemInfo> mGroceryItems = new ArrayList<>();

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    // Return a list of your courses
    public List<GroceryItemInfo> getCourses() {
        return mGroceryItems;
    }

    private static void loadCoursesFromDatabase(Cursor cursor) {
        // Retrieve the field positions in your database.
        // The positions of fields may change over time as the database grows,
        // so you want to use your constants to reference where those positions are in the table.
        int listGroceryItemPosition = cursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM);
        int listCostPosition = cursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM);
        int idPosition = cursor.getColumnIndex(GroceryItemInfoEntry._ID);

        // Create an instance of your DataManager and use the DataManager
        // to clear any information from the array list.
        DataManager dm = getInstance();
        dm.mGroceryItems.clear();

        // Loop through the cursor rows and add new course objects to
        // your array list.
        while (cursor.moveToNext()) {
            String listGroceryItem = cursor.getString(listGroceryItemPosition);
            String listCost = cursor.getString(listCostPosition);
            int id = cursor.getInt(idPosition);

            GroceryItemInfo list = new GroceryItemInfo(id, listGroceryItem, listCost);

            dm.mGroceryItems.add(list);
        }

        // Close the cursor (to prevent memory leaks)
        cursor.close();

    }

    public static void loadFromDatabase(GroceryItemsOpenHelper dbHelper) {
        // Open your database in read mode.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a list of columns you want to return.
        String[] groceryItemColumns = {
                GroceryItemInfoEntry.COLUMN_GROCERY_ITEM,
                GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST,
                GroceryItemInfoEntry._ID};

        // Create an order by field for sorting purposes.
        String groceryItemOrderBy = GroceryItemInfoEntry.COLUMN_GROCERY_ITEM;

        // Populate your cursor with the results of the query.
        final Cursor groceryItemCursor = db.query(GroceryItemInfoEntry.TABLE_NAME, groceryItemColumns,
                null, null, null, null, groceryItemOrderBy);

        // Call the method to load your array list.
        loadCoursesFromDatabase(groceryItemCursor);
    }

    public int createNewCourse() {
        // Create an empty course object to use on your activity screen
        // when you want a "blank" record to show up. It will return the
        // size of the new course array list.
        GroceryItemInfo course = new GroceryItemInfo(null, null);
        mGroceryItems.add(course);
        return mGroceryItems.size();
    }
}
