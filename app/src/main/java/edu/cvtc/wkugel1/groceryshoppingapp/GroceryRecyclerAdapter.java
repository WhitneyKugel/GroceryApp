package edu.cvtc.wkugel1.groceryshoppingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;

public class GroceryRecyclerAdapter extends RecyclerView.Adapter<GroceryRecyclerAdapter.ViewHolder> {

    // Member variables
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private Cursor mCursor;
    private int mGroceryItemPosition;
    private int mGroceryItemCostPosition;
    private int mIdPosition;

    public GroceryRecyclerAdapter(Context context, Cursor cursor) {
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
            mGroceryItemPosition = mCursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM);
            mGroceryItemCostPosition = mCursor.getColumnIndex(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST);
            mIdPosition = mCursor.getColumnIndex(GroceryItemInfoEntry._ID);
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
        View itemView = mLayoutInflater.inflate(R.layout.grocery_item_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Move the cursor to the correct row
        mCursor.moveToPosition(position);

        // Get the actual values
        String groceryItem = mCursor.getString(mGroceryItemPosition);
        String groceryItemCost = mCursor.getString(mGroceryItemCostPosition);
        int id = mCursor.getInt(mIdPosition);

        // Pass the information to the holder
        holder.mGroceryItem.setText(groceryItem);
        holder.mGroceryCost.setText(groceryItemCost);
        holder.mId = id;
    }

    @Override
    public int getItemCount() {
        // If the cursor is null, return 0. Otherwise return the count of records in it.
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // Member variables for inner class
        public final TextView mGroceryItem;
        public final TextView mGroceryCost;
        public int mId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mGroceryItem = (TextView) itemView.findViewById(R.id.text_grocery_item);
            mGroceryCost = (TextView) itemView.findViewById(R.id.text_cost);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, GroceryActivity.class);
                    intent.putExtra(GroceryActivity.GROCERY_ITEM_ID, mId);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
