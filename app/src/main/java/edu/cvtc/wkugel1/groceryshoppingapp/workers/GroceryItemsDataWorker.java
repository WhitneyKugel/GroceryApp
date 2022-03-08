package edu.cvtc.wkugel1.groceryshoppingapp.workers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;

public class GroceryItemsDataWorker {

    //Member Attributes
    private SQLiteDatabase mDb;

    // Constructor
    public GroceryItemsDataWorker(SQLiteDatabase db) {
        mDb = db;
    }

    private void insertGroceryItem(String item, String cost) {
        ContentValues values = new ContentValues();
        values.put(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM, item);
        values.put(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST, cost);

        long newRowId = mDb.insert(GroceryItemInfoEntry.TABLE_NAME, null, values);
    }

    public void insertGroceryItems() {
    }

}
