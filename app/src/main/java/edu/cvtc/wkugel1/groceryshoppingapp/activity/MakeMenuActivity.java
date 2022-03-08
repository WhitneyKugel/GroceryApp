package edu.cvtc.wkugel1.groceryshoppingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import edu.cvtc.wkugel1.groceryshoppingapp.helpers.MenuMealsOpenHelper;
import edu.cvtc.wkugel1.groceryshoppingapp.info.MenuMealInfo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
