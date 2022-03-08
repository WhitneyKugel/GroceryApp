package edu.cvtc.wkugel1.groceryshoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import edu.cvtc.wkugel1.groceryshoppingapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void launchMakeListActivity(View view) {

        Intent intent = new Intent(this, MakeListActivity.class);
        startActivity(intent);

    }

    public void launchCreateMealActivity(View view) {

        Intent intent = new Intent(this, CreateMealActivity.class);
        startActivity(intent);

    }

    public void launchViewListsActivity(View view) {

        Intent intent = new Intent(this, ViewListActivity.class);
        startActivity(intent);

    }
}