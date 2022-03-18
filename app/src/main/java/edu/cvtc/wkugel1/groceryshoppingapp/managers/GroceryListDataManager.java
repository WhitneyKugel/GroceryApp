package edu.cvtc.wkugel1.groceryshoppingapp.managers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryListInfoEntry;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.GroceryItemsOpenHelper;
import edu.cvtc.wkugel1.groceryshoppingapp.info.GroceryListInfo;

public class GroceryListDataManager {

    private static GroceryListDataManager ourInstance = null;
    private List<GroceryListInfo> mGroceryList = new ArrayList<>();

    public static GroceryListDataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new GroceryListDataManager();
        }
        return ourInstance;
    }

    // Return a list of your grocery items
    public List<GroceryListInfo> getGroceryListItems() {
        return mGroceryList;
    }

    private static void loadGroceryListFromDatabase(Cursor cursor) {
        // Retrieve the field positions in your database.
        // The positions of fields may change over time as the database grows,
        // so you want to use your constants to reference where those positions are in the table.
        int listGroceryItemPosition = cursor.getColumnIndex(GroceryListInfoEntry.COLUMN_GROCERY_ITEM);
        int listCostPosition = cursor.getColumnIndex(GroceryListInfoEntry.COLUMN_GROCERY_ITEM);
        int listAislePosition = cursor.getColumnIndex(GroceryListInfoEntry.COLUMN_GROCERY_ITEM_AISLE);
        int listAddToListPosition = cursor.getColumnIndex(GroceryListInfoEntry.COLUMN_GROCERY_ITEM_IN_CART);
        int idPosition = cursor.getColumnIndex(GroceryListInfoEntry._ID);

        // Create an instance of your DataManager and use the DataManager
        // to clear any information from the array list.
        GroceryListDataManager dm = getInstance();
        dm.mGroceryList.clear();

        // Loop through the cursor rows and add new grocery item objects to
        // your array list.
        while (cursor.moveToNext()) {
            String listGroceryItem = cursor.getString(listGroceryItemPosition);
            String listCost = cursor.getString(listCostPosition);
            String listAisle = cursor.getString(listAislePosition);
            int listAddToList = cursor.getInt(listAddToListPosition);
            int id = cursor.getInt(idPosition);

            GroceryListInfo list = new GroceryListInfo(id, listGroceryItem, listCost, listAisle, listAddToList);
            dm.mGroceryList.add(list);

        }

        // Close the cursor (to prevent memory leaks)
        cursor.close();

    }

    public static void loadFromDatabase(GroceryItemsOpenHelper dbHelper) {
        // Open your database in read mode.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a list of columns you want to return.
        String[] groceryListColumns = {
                GroceryListInfoEntry.COLUMN_GROCERY_ITEM,
                GroceryListInfoEntry.COLUMN_GROCERY_ITEM_COST,
                GroceryListInfoEntry.COLUMN_GROCERY_ITEM_AISLE,
                GroceryListInfoEntry.COLUMN_GROCERY_ITEM_IN_CART,
                GroceryListInfoEntry._ID};

        // Create an order by field for sorting purposes.
        String groceryListOrderBy = GroceryListInfoEntry.COLUMN_GROCERY_ITEM_AISLE;

        // Populate your cursor with the results of the query.
        final Cursor groceryListCursor = db.query(GroceryListInfoEntry.TABLE_NAME, groceryListColumns,
                null, null, null, null, groceryListOrderBy);

        // Call the method to load your array list.
        loadGroceryListFromDatabase(groceryListCursor);
    }

    public int createNewGroceryItem() {
        // Create an empty grocery item object to use on your activity screen
        // when you want a "blank" record to show up. It will return the
        // size of the new grocery item array list.
        GroceryListInfo groceryListItem = new GroceryListInfo(null, null, null, 0);
        mGroceryList.add(groceryListItem);
        return mGroceryList.size();
    }
}
