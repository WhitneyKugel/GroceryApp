package edu.cvtc.wkugel1.groceryshoppingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.MealPlannerInfoEntry;
import edu.cvtc.wkugel1.groceryshoppingapp.R;
import edu.cvtc.wkugel1.groceryshoppingapp.activity.AddMenuMealActivity;

public class MenuMealRecyclerAdapter extends RecyclerView.Adapter<MenuMealRecyclerAdapter.ViewHolder> {

    // Member variables
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private Cursor mCursor;
    private int mMenuMealPosition;
    private int mMenuDayPosition;
    private int mMenuTypePosition;
    private int mIdPosition;

    public MenuMealRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mLayoutInflater = LayoutInflater.from(context);

        // Used to get the positions of the columns we
        // are interested in.
        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if (mCursor != null) {
            // Get column indexes from mCursor
            mMenuMealPosition = mCursor.getColumnIndex(MealPlannerInfoEntry.COLUMN_MEAL_NAME);
            mMenuDayPosition = mCursor.getColumnIndex(MealPlannerInfoEntry.COLUMN_MEAL_DAY);
            mMenuTypePosition = mCursor.getColumnIndex(MealPlannerInfoEntry.COLUMN_MEAL_TYPE);
            mIdPosition = mCursor.getColumnIndex(MealPlannerInfoEntry._ID);
        }
    }

    public void changeCursor(Cursor cursor) {
        // If the cursor is open, close it
        if (mCursor != null) {
            mCursor.close();
        }

        // Create a new cursor based upon the object that is passed in.
        mCursor = cursor;

        // Get the positions of the columns in the new cursor.
        populateColumnPositions();

        // Tell the activity that the data set has changed.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuMealRecyclerAdapter.ViewHolder holder, int position) {
        // Move the cursor to the correct row
        mCursor.moveToPosition(position);

        // Get the actual values
        String menuMeal = mCursor.getString(mMenuMealPosition);
        String menuDay = mCursor.getString(mMenuDayPosition);
        String menuType = mCursor.getString(mMenuTypePosition);
        int id = mCursor.getInt(mIdPosition);

        // Pass the information to the holder
        holder.mMenuMeal.setText(menuMeal);
        holder.mMenuDay.setText(menuDay);
        holder.mMenuType.setText(menuType);
        holder.mId = id;

    }

    @Override
    public int getItemCount() {
        // If the cursor is null, return 0. Otherwise return the count of records in it.
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // Member variables for inner class
        public final TextView mMenuMeal;
        public final TextView mMenuDay;
        public final TextView mMenuType;
        public int mId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMenuMeal = (TextView) itemView.findViewById(R.id.menu_option_title);
            mMenuDay = (TextView) itemView.findViewById(R.id.menu_day_title);
            mMenuType = (TextView) itemView.findViewById(R.id.menu_meal_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AddMenuMealActivity.class);
                    intent.putExtra(AddMenuMealActivity.MENU_MEAL_ID, mId);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
