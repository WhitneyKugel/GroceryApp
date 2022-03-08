package edu.cvtc.wkugel1.groceryshoppingapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.MenuMealsOpenHelper;
import edu.cvtc.wkugel1.groceryshoppingapp.info.MenuMealInfo;
import edu.cvtc.wkugel1.groceryshoppingapp.R;

public class MakeMenuActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Constants
    public static final String MENU_MEAL_ID = "edu.cvtc.wkugel1.groceryshoppingapp.MENU_MEAL_ID";
    public static final String COLUMN_MEAL_NAME = "edu.cvtc.wkugel1.groceryshoppingapp.COLUMN_MEAL_NAME";
    private static final int ID_NOT_SET = -1;
    public static final int LOADER_MENU_MEALS = 0;

    // Initialize new MenuName to empty
    private MenuMealInfo mMenuMeal = new MenuMealInfo("");

    // Member Variables
    private boolean mIsNewMenuMeal;
    private boolean mIsCancelling;
    private int mMenuMealId;
    private int mMenuNamePosition;
    private String mOriginalMenuMeal;

    // Member objects
    private EditText mTextMenuMeal;
    private MenuMealsOpenHelper mDbOpenHelper;
    private Cursor mMenuMealCursor;
    private MenuMealsOpenHelper nDbOpenHelper;
    private Cursor mMenuMealCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_menu_list_add_menu_meal);

        nDbOpenHelper = new MenuMealsOpenHelper(this);

        readDisplayStateValues();

        // If the bundle is null, save the values. Otherwise restore the original value.
        if (savedInstanceState == null) {
            saveOriginalCourseValues();
        } else {
            restoreOriginalCourseValues(savedInstanceState);
        }

        mTextMenuMeal = findViewById(R.id.text_meal_item);

        // If it is not a new course, load the course data into the layout
        if (!mIsNewMenuMeal) {
            LoaderManager.getInstance(this).initLoader(LOADER_MENU_MEALS, null, this);
        }
    }

    private void displayMeal() {
        // Retrieve the values from the cursor based upon the position of the columns.
        String menuMeal = mMenuMealCursor.getString(mMenuNamePosition);

        // Use the information to populate the layout.
        mTextMenuMeal.setText(menuMeal);
    }

    private void restoreOriginalCourseValues(Bundle savedInstanceState) {
        // Get the original values from the savedInstanceState
        mOriginalMenuMeal = savedInstanceState.getString(COLUMN_MEAL_NAME);
    }

    private void saveOriginalCourseValues() {
        // Only save values if you do not have a new course
        if (!mIsNewMenuMeal) {
            mOriginalMenuMeal = mMenuMeal.getMenuMeal();
        }
    }

    private void readDisplayStateValues() {
        // Get the intent passed into the activity
        Intent intent = getIntent();

        // Get the course id passed into the intent
        mMenuMealId = intent.getIntExtra(MENU_MEAL_ID, ID_NOT_SET);

        // If the course id is not set, create a new course
        mIsNewMenuMeal = mMenuMealId == ID_NOT_SET;
        if (mIsNewMenuMeal) {
            createNewMenu();
        }
    }



}
