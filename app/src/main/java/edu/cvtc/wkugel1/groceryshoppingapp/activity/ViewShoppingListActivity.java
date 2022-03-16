package edu.cvtc.wkugel1.groceryshoppingapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import edu.cvtc.wkugel1.groceryshoppingapp.R;
import edu.cvtc.wkugel1.groceryshoppingapp.adapters.ShoppingListRecyclerAdapter;
import edu.cvtc.wkugel1.groceryshoppingapp.databinding.ActivityViewListBinding;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.GroceryItemsOpenHelper;
import edu.cvtc.wkugel1.groceryshoppingapp.info.GroceryListInfo;
import edu.cvtc.wkugel1.groceryshoppingapp.managers.GroceryListDataManager;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryListInfoEntry;

public class ViewShoppingListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ActivityViewListBinding binding;

    // Initialize new GroceryListInfo to empty
    private GroceryListInfo mGroceryList = new GroceryListInfo(0, "", "", "", 0);

    // Constants
    public static final int LOADER_GROCERY_ITEMS = 0;

    // Member variables
    private GroceryItemsOpenHelper mDbOpenHelper;
    private RecyclerView mRecyclerListItems;
    private LinearLayoutManager mGroceryListLayoutManager;
    private ShoppingListRecyclerAdapter mShoppingListRecyclerAdapter;

    // Member Objects
    private Cursor mGroceryItemCursor;
    private boolean mIsCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_list);

        binding = ActivityViewListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDbOpenHelper = new GroceryItemsOpenHelper(this);

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        // Retrieve the information from your database
        GroceryListDataManager.loadFromDatabase(mDbOpenHelper);

        // Set a reference to your list of items layout
        mRecyclerListItems = findViewById(R.id.view_list_items);
        mGroceryListLayoutManager = new LinearLayoutManager(this);

        // Get your grocery items
        List<GroceryListInfo> items = GroceryListDataManager.getInstance().getGroceryListItems();

        // We do not have a cursor yet, so pass null.
        mShoppingListRecyclerAdapter = new ShoppingListRecyclerAdapter(this, null);

        // Display the grocery items
        displayGroceryListItems();
    }

    private void displayGroceryListItems() {
        mRecyclerListItems.setLayoutManager(mGroceryListLayoutManager);
        mRecyclerListItems.setAdapter(mShoppingListRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(ViewShoppingListActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_make_list:
                intent = new Intent(ViewShoppingListActivity.this, MakeListActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_make_meal:
                intent = new Intent(ViewShoppingListActivity.this, MakeMealActivity.class);
                startActivity(intent);
                return true;
            default:
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
                    String[] groceryItemColumns = {
                            GroceryListInfoEntry.COLUMN_GROCERY_ITEM,
                            GroceryListInfoEntry.COLUMN_GROCERY_ITEM_COST,
                            GroceryListInfoEntry.COLUMN_GROCERY_ITEM_AISLE,
                            GroceryListInfoEntry.COLUMN_GROCERY_ITEM_IN_CART,
                            GroceryListInfoEntry._ID
                    };

                    // Create an order by field for sorting purposes.
                    String groceryItemOrderBy = GroceryListInfoEntry.COLUMN_GROCERY_ITEM;

                    // Populate your cursor with the results of the query.
                    return db.query(GroceryListInfoEntry.TABLE_NAME, groceryItemColumns,
                            null, null, null, null, groceryItemOrderBy);

                }
            };
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_GROCERY_ITEMS && mIsCreated) {
            // Associate the cursor with your RecyclerAdapter
            mShoppingListRecyclerAdapter.changeCursor(data);
            mIsCreated = false;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == LOADER_GROCERY_ITEMS) {
            // Change the cursor to null (cleanup)
            mShoppingListRecyclerAdapter.changeCursor(null);
        }
    }

    public void completeList(View view) {
        // Get connection to the database. Use the writable method since we are changing the data.
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.execSQL("delete from "+ GroceryListInfoEntry.TABLE_NAME);

        Toast.makeText(view.getContext(),
                "Shopping complete!", Toast.LENGTH_LONG).show();

        finish();
        startActivity(getIntent());
    }
}