package edu.cvtc.wkugel1.groceryshoppingapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract;
import edu.cvtc.wkugel1.groceryshoppingapp.R;
import edu.cvtc.wkugel1.groceryshoppingapp.adapters.ShoppingListRecyclerAdapter;
import edu.cvtc.wkugel1.groceryshoppingapp.databinding.ActivityViewListBinding;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.GroceryItemsOpenHelper;
import edu.cvtc.wkugel1.groceryshoppingapp.info.GroceryItemInfo;
import edu.cvtc.wkugel1.groceryshoppingapp.managers.GroceryListDataManager;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;

public class ViewListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ActivityViewListBinding binding;

    // Constants
    public static final int LOADER_GROCERY_ITEMS = 0;

    // Member variables
    private GroceryItemsOpenHelper mDbOpenHelper;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mGroceryItemsLayoutManager;
    private ShoppingListRecyclerAdapter mShoppingListRecyclerAdapter;
    private boolean mIsCreated = false;

    // Member Objects
    private Cursor mGroceryItemCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        binding = ActivityViewListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDbOpenHelper = new GroceryItemsOpenHelper(this);

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        // Retrieve the information from your database
        GroceryListDataManager.loadFromDatabase(mDbOpenHelper);

        // Set a reference to your list of items layout
        // TODO Change the find view if to better layout
        mRecyclerItems = (RecyclerView) findViewById(R.id.view_list_items);
        mGroceryItemsLayoutManager = new LinearLayoutManager(this);

        // Get your grocery items
        List<GroceryItemInfo> items = GroceryListDataManager.getInstance().getGroceryItems();

        // We do not have a cursor yet, so pass null.
        mShoppingListRecyclerAdapter = new ShoppingListRecyclerAdapter(this, null);

        // Display the grocery items
        displayGroceryItems();
    }

    private void displayGroceryItems() {
        mRecyclerItems.setLayoutManager(mGroceryItemsLayoutManager);
        mRecyclerItems.setAdapter(mShoppingListRecyclerAdapter);
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

        //no inspection SimplifiableIfStatement
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
                    String[] groceryItemColumns = {
                            GroceryItemInfoEntry.COLUMN_GROCERY_ITEM,
                            GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST,
                            GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_AISLE,
                            GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_ADD_TO_LIST,
                            GroceryItemInfoEntry._ID
                    };

                    // Create an order by field for sorting purposes.
                    String groceryItemOrderBy = GroceryItemInfoEntry.COLUMN_GROCERY_ITEM;

                    // Populate your cursor with the results of the query.
                    return db.query(GroceryItemInfoEntry.TABLE_NAME, groceryItemColumns,
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
        System.out.println("Complete Shopping Trip");
    }
}