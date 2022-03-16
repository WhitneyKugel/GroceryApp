package edu.cvtc.wkugel1.groceryshoppingapp.managers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;
import edu.cvtc.wkugel1.groceryshoppingapp.info.GroceryItemInfo;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.GroceryItemsOpenHelper;

public class GroceryItemDataManager {

    private static GroceryItemDataManager ourInstance = null;
    private List<GroceryItemInfo> mGroceryItems = new ArrayList<>();

    public static GroceryItemDataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new GroceryItemDataManager();
        }
        return ourInstance;
    }

    // Return a list of your grocery items
    public List<GroceryItemInfo> getGroceryItems() {
        return mGroceryItems;
    }

    private static void loadGroceryItemsFromDatabase(Cursor cursor) {
        // Retrieve the field positions in your database.
        // The positions of fields may change over time as the database grows,
        // so you want to use your constants to reference where those positions are in the table.
        int listGroceryItemPosition = cursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM);
        int listCostPosition = cursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM);
        int listAislePosition = cursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_AISLE);
        int listAddToListPosition = cursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_ADD_TO_LIST);
        int idPosition = cursor.getColumnIndex(GroceryItemInfoEntry._ID);

        // Create an instance of your DataManager and use the DataManager
        // to clear any information from the array list.
        GroceryItemDataManager dm = getInstance();
        dm.mGroceryItems.clear();

        // Loop through the cursor rows and add new grocery item objects to
        // your array list.
        while (cursor.moveToNext()) {
            String listGroceryItem = cursor.getString(listGroceryItemPosition);
            String listCost = cursor.getString(listCostPosition);
            String listAisle = cursor.getString(listAislePosition);
            int listAddToList = cursor.getInt(listAddToListPosition);
            int id = cursor.getInt(idPosition);

            GroceryItemInfo list = new GroceryItemInfo(id, listGroceryItem, listCost, listAisle);
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
                GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_AISLE,
                GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_ADD_TO_LIST,
                GroceryItemInfoEntry._ID};

        // Create an order by field for sorting purposes.
        String groceryItemOrderBy = GroceryItemInfoEntry.COLUMN_GROCERY_ITEM;

        // Populate your cursor with the results of the query.
        final Cursor groceryItemCursor = db.query(GroceryItemInfoEntry.TABLE_NAME, groceryItemColumns,
                null, null, null, null, groceryItemOrderBy);

        // Call the method to load your array list.
        loadGroceryItemsFromDatabase(groceryItemCursor);
    }

    public int createNewGroceryItem() {
        // Create an empty grocery item object to use on your activity screen
        // when you want a "blank" record to show up. It will return the
        // size of the new grocery item array list.
        GroceryItemInfo groceryItem = new GroceryItemInfo(null, null, null);
        mGroceryItems.add(groceryItem);
        return mGroceryItems.size();
    }
}
