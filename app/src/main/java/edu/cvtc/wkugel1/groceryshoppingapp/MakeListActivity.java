package edu.cvtc.wkugel1.groceryshoppingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import edu.cvtc.wkugel1.groceryshoppingapp.databinding.ActivityMakeListBinding;

public class MakeListActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMakeListBinding binding;
    private final LinkedList<String> mGroceryList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private GroceryListAdapter mAdapter;
    private TextView mNewItemText;
    private FloatingActionButton mFAB;
    private FloatingActionButton mAddItemFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMakeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wordListSize = mGroceryList.size();
                mAddItemFAB = findViewById(R.id.add_item_FAB);
                mNewItemText = findViewById(R.id.addItemText);
                mFAB = findViewById(R.id.fab);

                mNewItemText.setVisibility(View.VISIBLE);

                mFAB.setVisibility(View.INVISIBLE);

                mAddItemFAB.setVisibility(View.VISIBLE);

                mNewItemText.setText("");

//                // Add a new word to the wordList.
//                mGroceryList.addLast("+ Word " + wordListSize);

//                // Notify the adapter, that the data has changed.
//                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);

                // Scroll to the bottom.
                mRecyclerView.smoothScrollToPosition(wordListSize);
            }
        });

        // Add entered item to list
        binding.addItemFAB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int wordListSize = mGroceryList.size();

                // Add a new word to the wordList.
                mGroceryList.addLast(mNewItemText.getText().toString());

                // Notify the adapter, that the data has changed.
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);

                mNewItemText.setVisibility(View.INVISIBLE);
                mFAB.setVisibility(View.VISIBLE);
                mAddItemFAB.setVisibility(View.INVISIBLE);



            }
        });

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);

        // Create an adapter and supply the data to be displayed.
        mAdapter = new GroceryListAdapter(this, mGroceryList);

        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

}
