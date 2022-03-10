package edu.cvtc.wkugel1.groceryshoppingapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.cvtc.wkugel1.groceryshoppingapp.R;
import edu.cvtc.wkugel1.groceryshoppingapp.adapters.MenuMealRecyclerAdapter;
import edu.cvtc.wkugel1.groceryshoppingapp.databinding.ActivityMakeMenuBinding;
import edu.cvtc.wkugel1.groceryshoppingapp.helpers.GroceryItemsOpenHelper;
import edu.cvtc.wkugel1.groceryshoppingapp.info.MenuMealInfo;
import edu.cvtc.wkugel1.groceryshoppingapp.managers.MenuDataManager;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.MealPlannerInfoEntry;

public class CreateMealActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ActivityMakeMenuBinding binding;

    // Constants
    public static final int LOADER_MENU_MEALS = 0;

    // Member variables
    private GroceryItemsOpenHelper mDbOpenHelper;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mMenusLayoutManager;
    private MenuMealRecyclerAdapter mMenuRecyclerAdapter;

    private boolean mIsCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu_item_list);

        binding = ActivityMakeMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDbOpenHelper = new GroceryItemsOpenHelper(this);

        FloatingActionButton fab = findViewById(R.id.menu_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateMealActivity.this, MakeMenuActivity.class));
            }
        });

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        // Retrieve the information from your database
        MenuDataManager.loadFromDatabase(mDbOpenHelper);

        // Set a reference to your list of items layout
        mRecyclerItems = (RecyclerView) findViewById(R.id.list_menus);
        mMenusLayoutManager = new LinearLayoutManager(this);

        // Get your menus
        List<MenuMealInfo> items = MenuDataManager.getInstance().getMenus();

        // We do not have a cursor yet, so pass null.
        mMenuRecyclerAdapter = new MenuMealRecyclerAdapter(this, null);

        // Display the menus
        displayMenus();
    }

    private void displayMenus() {
        mRecyclerItems.setLayoutManager(mMenusLayoutManager);
        mRecyclerItems.setAdapter(mMenuRecyclerAdapter);
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
        LoaderManager.getInstance(this).restartLoader(LOADER_MENU_MEALS, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Create new cursor loader
        CursorLoader loader = null;

        if (id == LOADER_MENU_MEALS) {
            loader = new CursorLoader(this) {
                @Override
                public Cursor loadInBackground() {
                    mIsCreated = true;

                    // Open your database in read mode.
                    SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

                    // Create a list of columns you want to return.
                    String[] menuColumns = {
                            MealPlannerInfoEntry.COLUMN_MEAL_NAME,
                            MealPlannerInfoEntry._ID
                    };

                    // Create an order by field for sorting purposes.
                    String menuOrderBy = MealPlannerInfoEntry.COLUMN_MEAL_NAME;

                    // Populate your cursor with the results of the query.
                    return db.query(MealPlannerInfoEntry.TABLE_NAME, menuColumns,
                            null, null, null, null, menuOrderBy);

                }
            };
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_MENU_MEALS && mIsCreated) {
            // Associate the cursor with your RecyclerAdapter
            mMenuRecyclerAdapter.changeCursor(data);
            mIsCreated = false;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == LOADER_MENU_MEALS) {
            // Change the cursor to null (cleanup)
            mMenuRecyclerAdapter.changeCursor(null);
        }
    }

    private void loadMenus() {
        // Open your database in read mode.
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

        // Create a list of columns you want to return.
        String[] menusColumns = {
                MealPlannerInfoEntry.COLUMN_MEAL_NAME,
                MealPlannerInfoEntry._ID
        };

        // Create an order by field for sorting purposes.
        String menuOrderBy = MealPlannerInfoEntry.COLUMN_MEAL_NAME;

        // Populate your cursor with the results of the query.
        final Cursor menuCursor = db.query(MealPlannerInfoEntry.TABLE_NAME, menusColumns,
                null, null, null, null, menuOrderBy);

        // Associate the cursor with your RecyclerAdapter
        mMenuRecyclerAdapter.changeCursor(menuCursor);
    }

//    public void onCheckBoxClicked(View view) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Add or remove from shopping list.
//        if (checked) {
//            // Add item to shopping list
//            System.out.println(view);
//        } else {
//            // Remove item from shopping list
//
//        }
//
//    }

    public void makeMenu(View view) {
    }

}
