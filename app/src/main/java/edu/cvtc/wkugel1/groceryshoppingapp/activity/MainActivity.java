package edu.cvtc.wkugel1.groceryshoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        Intent intent = new Intent(this, MakeMealActivity.class);
        startActivity(intent);

    }

    public void launchViewListsActivity(View view) {

        Intent intent = new Intent(this, ViewShoppingListActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_make_list:
                intent = new Intent(MainActivity.this, MakeListActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_view_list:
                intent = new Intent(MainActivity.this, ViewShoppingListActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_make_meal:
                intent = new Intent(MainActivity.this, MakeMealActivity.class);
                startActivity(intent);
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}