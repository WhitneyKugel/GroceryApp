package edu.cvtc.wkugel1.groceryshoppingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.loader.content.CursorLoader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;

public class GroceryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    // Constraints
    public static final String GROCERY_ITEM_ID = "edu.cvtc.wkugel1.groceryshoppingapp.GROCERY_ITEM_ID";
    public static final String COLUMN_GROCERY_ITEM = "edu.cvtc.wkugel1.groceryshoppingapp.COLUMN_GROCERY_ITEM";
    public static final String COLUMN_GROCERY_ITEM_COST = "edu.cvtc.wkugel1.groceryshoppingapp.COLUMN_GROCERY_ITEM_COST";
    private static final int ID_NOT_SET = -1;
    public static final int LOADER_GROCERY_ITEMS = 0;

    // Initialize new CourseInfo to empty
    private GroceryItemInfo mGroceryItem = new GroceryItemInfo(0, "", "");

    // Member variables
    private boolean mIsNewGroceryItem;
    private boolean mIsCancelling;
    private int mGroceryItemId;
    private int mGroceryItemPosition;
    private int mGroceryItemCostPosition;
    private String mOriginalGroceryItem;
    private String mOriginalGroceryCost;

    // Member objects
    private EditText mTextGroceryItem;
    private EditText mTextGroceryCost;
    private GroceryItemsOpenHelper mDbOpenHelper;
    private Cursor mGroceryItemCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_list_add_item);

        mDbOpenHelper = new GroceryItemsOpenHelper(this);

        readDisplayStateValues();

        // If the bundle is null, save the values. Otherwise restore the original value.
        if (savedInstanceState == null) {
            saveOriginalCourseValues();
        } else {
            restoreOriginalCourseValues(savedInstanceState);
        }

        mTextGroceryItem = findViewById(R.id.text_grocery_item);
        mTextGroceryCost = findViewById(R.id.text_cost);

        // If it is not a new course, load the course data into the layout
        if (!mIsNewGroceryItem) {
            LoaderManager.getInstance(this).initLoader(LOADER_GROCERY_ITEMS, null, this);
        }
    }

    private void displayCourse() {
        // Retrieve the values from the cursor based upon the position of the columns.
        String courseTitle = mGroceryItemCursor.getString(mGroceryItemPosition);
        String courseDescription = mGroceryItemCursor.getString(mGroceryItemCostPosition);

        // Use the information to populate the layout.
        mTextGroceryItem.setText(courseTitle);
        mTextGroceryCost.setText(courseDescription);
    }

    private void saveOriginalCourseValues() {
        // Only save values if you do not have a new course
        if (!mIsNewGroceryItem) {
            mOriginalGroceryItem = mGroceryItem.getTitle();
            mOriginalGroceryCost = mGroceryItem.getDescription();
        }
    }

    private void restoreOriginalCourseValues(Bundle savedInstanceState) {
        // Get the original values from the savedInstanceState
        mOriginalGroceryItem = savedInstanceState.getString(COLUMN_GROCERY_ITEM);
        mOriginalGroceryCost = savedInstanceState.getString(COLUMN_GROCERY_ITEM_COST);
    }

    private void readDisplayStateValues() {
        // Get the intent passed into the activity
        Intent intent = getIntent();

        // Get the course id passed into the intent
        mGroceryItemId = intent.getIntExtra(GROCERY_ITEM_ID, ID_NOT_SET);

        // If the course id is not set, create a new course
        mIsNewGroceryItem = mGroceryItemId == ID_NOT_SET;
        if (mIsNewGroceryItem) {
            createNewCourse();
        }
    }

    private void createNewCourse() {
        // Create ContentValues object to hold our fields
        ContentValues values = new ContentValues();

        // For a new course, we don't know what the values will be,
        // so we set the columns to empty strings.
        values.put(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM, "");
        values.put(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST, "");

        // Get connection to the database. Use the writable method since we are changing the data.
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

        // Insert the new row in the database and assign the new id to our member variable
        // for course id. Cast the 'long' return value to an int.
        mGroceryItemId = (int)db.insert(GroceryItemInfoEntry.TABLE_NAME, null, values);
    }

    private void saveCourseToDatabase(String courseTitle, String courseDescription) {
        // Create selection criteria
        String selection = GroceryItemInfoEntry._ID + " = ?";
        String[] selectionArgs = {Integer.toString(mGroceryItemId)};

        // Use a ContentValues object to put our information into.
        ContentValues values = new ContentValues();
        values.put(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM, courseTitle);
        values.put(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST, courseDescription);

        AsyncTaskLoader<String> task = new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                // Get connection to the database. Use the writable method since
                // we are changing the data.
                SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

                // Call the update method
                db.update(GroceryItemInfoEntry.TABLE_NAME, values, selection, selectionArgs);
                return null;
            }
        };

        task.loadInBackground();
    }

    private void storePreviousCourseValues() {
        mGroceryItem.setTitle(mOriginalGroceryItem);
        mGroceryItem.setDescription(mOriginalGroceryCost);
    }

    private void saveCourse() {
        // Get the values from the layout
        String courseTitle = mTextGroceryItem.getText().toString();
        String courseDescription = mTextGroceryCost.getText().toString();

        // Call the method to write to the database
        saveCourseToDatabase(courseTitle, courseDescription);
    }

    private void deleteCourseFromDatabase() {
        // Create selection criteria
        String selection = GroceryItemInfoEntry._ID + " = ?";
        String[] selectionArgs = {Integer.toString(mGroceryItemId)};

        AsyncTaskLoader<String> task = new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {

                // Get connection to the database. Use the writable method since we are changing the data.
                SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

                // Call the delete method
                db.delete(GroceryItemInfoEntry.TABLE_NAME, selection, selectionArgs);
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
        if (id == LOADER_GROCERY_ITEMS) {
            loader = createLoaderCourses();
        }
        return loader;
    }

    private CursorLoader createLoaderCourses() {
        return new CursorLoader(this) {
            @Override
            public Cursor loadInBackground() {
                // Open a connection to the database
                SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

                // Build the selection criteria. In this case, you want to set the ID of the course
                // to the passed-in course id from the Intent.
                String selection = GroceryItemInfoEntry._ID + " = ?";
                String[] selectionArgs = {Integer.toString(mGroceryItemId)};

                // Create a list of the columns you are pulling from the database
                String[] courseColumns = {
                        GroceryItemInfoEntry.COLUMN_GROCERY_ITEM,
                        GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST
                };

                // Fill your cursor with the information you have provided.
                return db.query(GroceryItemInfoEntry.TABLE_NAME, courseColumns, selection,
                        selectionArgs, null,null,null);
            }
        };
    }

    @Override
    // Called when data is loaded
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // Check to see if this is your cursor for your loader
        if (loader.getId() == LOADER_GROCERY_ITEMS) {
            loadFinishedCourses(data);
        }
    }

    private void loadFinishedCourses(Cursor data) {
        // Populate your member cursor with the data.
        mGroceryItemCursor = data;

        // Get the positions of the fields in the cursor so that
        // you are able to retrieve them into your layout
        mGroceryItemPosition = mGroceryItemCursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM);
        mGroceryItemCostPosition = mGroceryItemCursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST);

        // Make sure that you have moved to the correct record.
        // The cursor will not have populated any of the
        // fields until you move it.
        mGroceryItemCursor.moveToNext();

        // Call the method to display the course.
        displayCourse();
    }

    @Override
    // Called during cleanup
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Check to see if this is your cursor for your loader.
        if (loader.getId() == LOADER_GROCERY_ITEMS) {
            // If the cursor is not null, close it
            if (mGroceryItemCursor != null) {
                mGroceryItemCursor.close();
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Did the user cancel the process?
        if (mIsCancelling) {
            // Is this a new course?
            if (mIsNewGroceryItem) {
                // Delete the new course.
                deleteCourseFromDatabase();
            } else {
                // Put the original values on the screen.
                storePreviousCourseValues();
            }
        } else {
            // Save the data when leaving the activity.
            saveCourse();
        }
    }


}
