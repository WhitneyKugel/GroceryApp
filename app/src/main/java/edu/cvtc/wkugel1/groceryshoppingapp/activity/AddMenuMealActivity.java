package edu.cvtc.wkugel1.groceryshoppingapp.activity;

import static edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.MealPlannerInfoEntry.COLUMN_MEAL_DAY;
import static edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.MealPlannerInfoEntry.COLUMN_MEAL_TYPE;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.MealPlannerInfoEntry;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.GroceryItemsOpenHelper;
import edu.cvtc.wkugel1.groceryshoppingapp.info.MenuMealInfo;
import edu.cvtc.wkugel1.groceryshoppingapp.R;

public class AddMenuMealActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Constants
    public static final String MENU_MEAL_ID = "edu.cvtc.wkugel1.groceryshoppingapp.MENU_MEAL_ID";
    public static final String COLUMN_MEAL_NAME = "edu.cvtc.wkugel1.groceryshoppingapp.COLUMN_MEAL_NAME";
    private static final int ID_NOT_SET = -1;
    public static final int LOADER_MENU_MEALS = 0;

    // Initialize new MenuName to empty
    private MenuMealInfo mMenuMeal = new MenuMealInfo("", "", "");

    // Member Variables
    private boolean mIsNewMenuMeal;
    private boolean mIsCancelling;
    private int mMenuMealId;
    private int mMenuNamePosition;
    private int mMenuDayPosition;
    private int mMenuTypePosition;
    private String mOriginalMenuMeal;
    private String mOriginalMenuDay;
    private String mOriginalMenuType;

    // Member objects
    private EditText mTextMenuMeal;
    private EditText mTextMenuDay;
    private EditText mTextMenuType;
    private GroceryItemsOpenHelper mDbOpenHelper;
    private Cursor mMenuMealCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_menu_list_add_menu_meal);

        mDbOpenHelper = new GroceryItemsOpenHelper(this);

        readDisplayStateValues();

        // If the bundle is null, save the values. Otherwise restore the original value.
        if (savedInstanceState == null) {
            saveOriginalMenuMealValues();
        } else {
            restoreOriginalMenuMealValues(savedInstanceState);
        }

        mTextMenuMeal = findViewById(R.id.text_meal_item);
        mTextMenuDay = findViewById(R.id.text_meal_day);
        mTextMenuType = findViewById(R.id.text_meal_type);

        // If it is not a new menu meal, load the menu meal data into the layout
        if (!mIsNewMenuMeal) {
            LoaderManager.getInstance(this).initLoader(LOADER_MENU_MEALS, null, this);
        }
    }

    private void displayMeal() {
        // Retrieve the values from the cursor based upon the position of the columns.
        String menuMeal = mMenuMealCursor.getString(mMenuNamePosition);
        String menuDay = mMenuMealCursor.getString(mMenuNamePosition);
        String menuType = mMenuMealCursor.getString(mMenuNamePosition);

        // Use the information to populate the layout.
        mTextMenuMeal.setText(menuMeal);
        mTextMenuDay.setText(menuDay);
        mTextMenuType.setText(menuType);
    }

    private void restoreOriginalMenuMealValues(Bundle savedInstanceState) {
        // Get the original values from the savedInstanceState
        mOriginalMenuMeal = savedInstanceState.getString(COLUMN_MEAL_NAME);
        mOriginalMenuDay = savedInstanceState.getString(COLUMN_MEAL_DAY);
        mOriginalMenuType = savedInstanceState.getString(COLUMN_MEAL_TYPE);
    }

    private void saveOriginalMenuMealValues() {
        // Only save values if you do not have a new menu meal
        if (!mIsNewMenuMeal) {
            mOriginalMenuMeal = mMenuMeal.getMenuMeal();
            mOriginalMenuDay = mMenuMeal.getMenuDay();
            mOriginalMenuType = mMenuMeal.getMenuType();
        }
    }

    private void readDisplayStateValues() {
        // Get the intent passed into the activity
        Intent intent = getIntent();

        // Get the menu meal id passed into the intent
        mMenuMealId = intent.getIntExtra(MENU_MEAL_ID, ID_NOT_SET);

        // If the menu meal id is not set, create a new menu meal
        mIsNewMenuMeal = mMenuMealId == ID_NOT_SET;
        if (mIsNewMenuMeal) {
            createNewMenu();
        }
    }

    private void createNewMenu() {
        // Create ContentValues object to hold our fields
        ContentValues values = new ContentValues();

        // For a new menu meal, we don't know what the values will be,
        // so we set the columns to empty strings.
        values.put(MealPlannerInfoEntry.COLUMN_MEAL_NAME, "");
        values.put(MealPlannerInfoEntry.COLUMN_MEAL_DAY, "");
        values.put(MealPlannerInfoEntry.COLUMN_MEAL_TYPE, "");

        // Get connection to the database. Use the writable method since we are changing the data.
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

        // Insert the new row in the database and assign the new id to our member variable
        // for meal id. Cast the 'long' return value to an int.
        mMenuMealId = (int)db.insert(MealPlannerInfoEntry.TABLE_NAME, null, values);
    }

    private void saveMenuMeal() {
        // Get the values from the layout
        String menuMeal = mTextMenuMeal.getText().toString();
        String menuDay = mTextMenuDay.getText().toString();
        String menuType = mTextMenuType.getText().toString();

        // Call the method to write to the database
        saveMenuMealToDatabase(menuMeal, menuDay, menuType);
    }

    private void saveMenuMealToDatabase(String menuMeal, String menuDay, String menuType) {
        // Create selection criteria
        String selection = MealPlannerInfoEntry._ID + " = ?";
        String[] selectionArgs = {Integer.toString(mMenuMealId)};

        // Use a ContentValues object to put our information into.
        ContentValues values = new ContentValues();
        values.put(MealPlannerInfoEntry.COLUMN_MEAL_NAME, menuMeal);
        values.put(MealPlannerInfoEntry.COLUMN_MEAL_DAY, menuDay);
        values.put(MealPlannerInfoEntry.COLUMN_MEAL_TYPE, menuType);

        AsyncTaskLoader<String> task = new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                // Get connection to the database. Use the writable method since
                // we are changing the data.
                SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

                // Call the update method
                db.update(MealPlannerInfoEntry.TABLE_NAME, values, selection, selectionArgs);
                return null;
            }
        };
        task.loadInBackground();
    }

    private void storePreviousMenuMealValues() {
        mMenuMeal.setMenuMeal(mOriginalMenuMeal);
        mMenuMeal.setMenuDay(mOriginalMenuDay);
        mMenuMeal.setMenuDay(mOriginalMenuType);

    }

    private void deleteMenuMealFromDatabase() {
        // Create selection criteria
        String selection = MealPlannerInfoEntry._ID + " = ?";
        String[] selectionArgs = {Integer.toString(mMenuMealId)};

        AsyncTaskLoader<String> task = new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {

                // Get connection to the database. Use the writable method since we are changing the data.
                SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

                // Call the delete method
                db.delete(MealPlannerInfoEntry.TABLE_NAME, selection, selectionArgs);
                return null;
            }
        };
        task.loadInBackground();
    }

    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Create a local cursor loader
        CursorLoader loader = null;

        // Check to see if the id is for your loader.
        if (id == LOADER_MENU_MEALS) {
            loader = createLoaderMenuMeals();
        }
        return loader;
    }

    private CursorLoader createLoaderMenuMeals() {
        return new CursorLoader(this) {
            @Override
            public Cursor loadInBackground() {
                // Open a connection to the database
                SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

                // Build the selection criteria. In this case, you want to set the ID of the menu meal
                // to the passed-in menu meal id from the Intent.
                String selection = MealPlannerInfoEntry._ID + " = ?";
                String[] selectionArgs = {Integer.toString(mMenuMealId)};

                // Create a list of the columns you are pulling from the database
                String[] menuMealColumns = {
                        MealPlannerInfoEntry.COLUMN_MEAL_NAME,
                        MealPlannerInfoEntry.COLUMN_MEAL_DAY,
                        MealPlannerInfoEntry.COLUMN_MEAL_TYPE,
                };

                // Fill your cursor with the information you have provided.
                return db.query(MealPlannerInfoEntry.TABLE_NAME, menuMealColumns, selection,
                        selectionArgs, null,null,null);
            }
        };
    }

    @Override
    // Called when data is loaded
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // Check to see if this is your cursor for your loader
        if (loader.getId() == LOADER_MENU_MEALS) {
            loadFinishedMenuMeals(data);
        }
    }

    private void loadFinishedMenuMeals(Cursor data) {
        // Populate your member cursor with the data.
        mMenuMealCursor = data;

        // Get the positions of the fields in the cursor so that
        // you are able to retrieve them into your layout
        mMenuNamePosition = mMenuMealCursor.getColumnIndex(MealPlannerInfoEntry.COLUMN_MEAL_NAME);
        mMenuDayPosition = mMenuMealCursor.getColumnIndex(MealPlannerInfoEntry.COLUMN_MEAL_DAY);
        mMenuTypePosition = mMenuMealCursor.getColumnIndex(MealPlannerInfoEntry.COLUMN_MEAL_TYPE);

        // Make sure that you have moved to the correct record.
        // The cursor will not have populated any of the
        // fields until you move it.
        mMenuMealCursor.moveToNext();

        // Call the method to display the meal.
        displayMenuMeal();
    }

    private void displayMenuMeal() {
        // Retrieve the values from the cursor based upon the position of the columns.
        String menuMeal = mMenuMealCursor.getString(mMenuNamePosition);

        // Use the information to populate the layout.
        mTextMenuMeal.setText(menuMeal);

    }

    @Override
    // Called during cleanup
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Check to see if this is your cursor for your loader.
        if (loader.getId() == LOADER_MENU_MEALS) {
            // If the cursor is not null, close it
            if (mMenuMealCursor != null) {
                mMenuMealCursor.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_grocery_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks
        // on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();

        if (id == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
        }

        if (id == R.id.action_delete) {
            mIsCancelling = true;
            deleteMenuMealFromDatabase();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Did the user cancel the process?
        if (mIsCancelling) {
            // Is this a new menu meal?
            if (mIsNewMenuMeal) {
                // Delete the new menu meal.
                deleteMenuMealFromDatabase();
            } else {
                // Put the original values on the screen.
                storePreviousMenuMealValues();
            }
        } else {
            // Save the data when leaving the activity.
            saveMenuMeal();
        }
    }
}














