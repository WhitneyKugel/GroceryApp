package edu.cvtc.wkugel1.groceryshoppingapp.managers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.MealPlannerInfoEntry;
import edu.cvtc.wkugel1.groceryshoppingapp.info.MenuMealInfo;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.MenuMealsOpenHelper;

public class MenuDataManager {
    private static MenuDataManager ourInstance = null;
    private List<MenuMealInfo> mMenuNames = new ArrayList<>();

    public static MenuDataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new MenuDataManager();
        }
        return ourInstance;
    }

    // Return a list of your menu names
    public List<MenuMealInfo> getMenus() {
        return mMenuNames;
    }

    private static void loadMenusFromDatabase(Cursor cursor) {
        // Retrieve the field positions in your database.
        // The positions of fields may change over time as the database grows,
        // so you want to use your constants to reference where those positions are in the table.
        int listMenuNamePosition = cursor.getColumnIndex(MealPlannerInfoEntry.COLUMN_MEAL_NAME);
        int idPosition = cursor.getColumnIndex(MealPlannerInfoEntry._ID);

        // Create an instance of your DataManager and use the DataManager
        // to clear any information from the array list.
        MenuDataManager dm = getInstance();
        dm.mMenuNames.clear();

        // Loop through the cursor rows and add new menu objects to
        // your array list.
        while (cursor.moveToNext()) {
            String listMenuName = cursor.getString(listMenuNamePosition);
            int id = cursor.getInt(idPosition);

            MenuMealInfo list = new MenuMealInfo(id, listMenuName);

            dm.mMenuNames.add(list);
        }

        // Close the cursor (to prevent memory leaks)
        cursor.close();

    }

    public static void loadFromDatabase(MenuMealsOpenHelper dbHelper) {
        // Open your database in read mode.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a list of columns you want to return.
        String[] menuNameColumns = {
                MealPlannerInfoEntry.COLUMN_MEAL_NAME,
                MealPlannerInfoEntry._ID};

        // Create an order by field for sorting purposes.
        String mealNameOrderBy = MealPlannerInfoEntry.COLUMN_MEAL_NAME;

        // Populate your cursor with the results of the query.
        final Cursor menuNameCursor = db.query(MealPlannerInfoEntry.TABLE_NAME, menuNameColumns,
                null, null, null, null, mealNameOrderBy);

        // Call the method to load your array list.
        loadMenusFromDatabase(menuNameCursor);
    }

    public int createNewMenu() {
        // Create an empty menu object to use on your activity screen
        // when you want a "blank" record to show up. It will return the
        // size of the new menu array list.
        MenuMealInfo menuMealInfo = new MenuMealInfo((String) null);
        mMenuNames.add(menuMealInfo);
        return mMenuNames.size();
    }
}


