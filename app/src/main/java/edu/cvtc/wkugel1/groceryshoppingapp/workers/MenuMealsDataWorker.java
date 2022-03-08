package edu.cvtc.wkugel1.groceryshoppingapp.workers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.MealPlannerInfoEntry;

public class MenuMealsDataWorker {

    // Member Attributes
    private SQLiteDatabase mDb;

    // Constructor
    public MenuMealsDataWorker(SQLiteDatabase db) {
        mDb = db;
    }

    private void insertMenuMeal(String meal) {
        ContentValues values = new ContentValues();
        values.put(MealPlannerInfoEntry.COLUMN_MEAL_NAME, meal);

        long newRowId = mDb.insert(MealPlannerInfoEntry.TABLE_NAME, null, values);
    }

    public void insertMenuMeals() {

    }
}
