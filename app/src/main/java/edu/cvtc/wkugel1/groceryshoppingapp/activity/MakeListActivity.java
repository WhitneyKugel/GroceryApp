package edu.cvtc.wkugel1.groceryshoppingapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.cvtc.wkugel1.groceryshoppingapp.managers.GroceryItemDataManager;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;
import edu.cvtc.wkugel1.groceryshoppingapp.info.GroceryItemInfo;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.GroceryItemsOpenHelper;
import edu.cvtc.wkugel1.groceryshoppingapp.adapters.GroceryRecyclerAdapter;
import edu.cvtc.wkugel1.groceryshoppingapp.R;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MakeListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ActivityMakeListBinding binding;

    // Constants
    public static final int LOADER_GROCERY_ITEMS = 0;

    // Member variables
    private GroceryItemsOpenHelper mDbOpenHelper;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mCoursesLayoutManager;
    private GroceryRecyclerAdapter mGroceryRecyclerAdapter;

    private boolean mIsCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_make_list);

        binding = ActivityMakeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//
//        setSupportActionBar(binding.toolbar);

        mDbOpenHelper = new GroceryItemsOpenHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MakeListActivity.this, GroceryActivity.class));
            }
        });

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        // Retrieve the information from your database
        GroceryItemDataManager.loadFromDatabase(mDbOpenHelper);

        // Set a reference to your list of items layout
        mRecyclerItems = (RecyclerView) findViewById(R.id.list_items);
        mCoursesLayoutManager = new LinearLayoutManager(this);

        // Get your grocery items
        List<GroceryItemInfo> items = GroceryItemDataManager.getInstance().getGroceryItems();

        // We do not have a cursor yet, so pass null.
        mGroceryRecyclerAdapter = new GroceryRecyclerAdapter(this, null);

        // Display the courses
        displayCourses();
    }


    private void displayCourses() {
        mRecyclerItems.setLayoutManager(mCoursesLayoutManager);
        mRecyclerItems.setAdapter(mGroceryRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Use restartLoader instead of initLoader to make sure you re-query the database
        // each time the activity is loaded in the app.
        LoaderManager.getInstance(this).restartLoader(LOADER_GROCERY_ITEMS, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Create new cursor loader
        CursorLoader loader = null;

        if (id == LOADER_GROCERY_ITEMS) {
            loader = new CursorLoader(this) {
                @Override
                public Cursor loadInBackground() {
                    mIsCreated = true;

                    // Open your database in read mode.
                    SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

                    // Create a list of columns you want to return.
                    String[] courseColumns = {
                            GroceryItemInfoEntry.COLUMN_GROCERY_ITEM,
                            GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST,
                            GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_AISLE,
                            GroceryItemInfoEntry._ID
                    };

                    // Create an order by field for sorting purposes.
                    String courseOrderBy = GroceryItemInfoEntry.COLUMN_GROCERY_ITEM;

                    // Populate your cursor with the results of the query.
                    return db.query(GroceryItemInfoEntry.TABLE_NAME, courseColumns,
                            null, null, null, null, courseOrderBy);

                }
            };
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_GROCERY_ITEMS && mIsCreated) {
            // Associate the cursor with your RecyclerAdapter
            mGroceryRecyclerAdapter.changeCursor(data);
            mIsCreated = false;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == LOADER_GROCERY_ITEMS) {
            // Change the cursor to null (cleanup)
            mGroceryRecyclerAdapter.changeCursor(null);
        }
    }

    private void loadCourses() {
        // Open your database in read mode.
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

        // Create a list of columns you want to return.
        String[] courseColumns = {
                GroceryItemInfoEntry.COLUMN_GROCERY_ITEM,
                GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST,
                GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_AISLE,
                GroceryItemInfoEntry._ID
        };

        // Create an order by field for sorting purposes.
        String courseOrderBy = GroceryItemInfoEntry.COLUMN_GROCERY_ITEM;

        // Populate your cursor with the results of the query.
        final Cursor groceryItemCursor = db.query(GroceryItemInfoEntry.TABLE_NAME, courseColumns,
                null, null, null, null, courseOrderBy);

        // Associate the cursor with your RecyclerAdapter
        mGroceryRecyclerAdapter.changeCursor(groceryItemCursor);
    }


    public void onCheckBoxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Add or remove from shopping list.
        if (checked) {
            // Add item to shopping list
            System.out.println(view);
        } else {
            // Remove item from shopping list

        }

    }


    public void makeList(View view) {

    }
}