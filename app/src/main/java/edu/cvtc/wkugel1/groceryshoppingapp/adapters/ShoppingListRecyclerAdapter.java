package edu.cvtc.wkugel1.groceryshoppingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import edu.cvtc.wkugel1.groceryshoppingapp.R;
import edu.cvtc.wkugel1.groceryshoppingapp.activity.GroceryActivity;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryListInfoEntry;

public class ShoppingListRecyclerAdapter extends RecyclerView.Adapter<ShoppingListRecyclerAdapter.ViewHolder> {

    // Member variables
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private Cursor mCursor;
    private int mGroceryItemPosition;
    private int mGroceryItemCostPosition;
    private int mGroceryItemAislePosition;
    private int mGroceryItemInCartPosition;
    private int mIdPosition;

    public ShoppingListRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mLayoutInflater = LayoutInflater.from(context);

        // Used to get the positions of the columns we are interested in.
        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if (mCursor != null) {
            // Get column indexes from mCursor
            mGroceryItemPosition = mCursor.getColumnIndex(GroceryListInfoEntry.COLUMN_GROCERY_ITEM);
            mGroceryItemCostPosition = mCursor.getColumnIndex(GroceryListInfoEntry.COLUMN_GROCERY_ITEM_COST);
            mGroceryItemAislePosition = mCursor.getColumnIndex(GroceryListInfoEntry.COLUMN_GROCERY_ITEM_AISLE);
            mGroceryItemInCartPosition = mCursor.getColumnIndex(GroceryListInfoEntry.COLUMN_GROCERY_ITEM_IN_CART);
            mIdPosition = mCursor.getColumnIndex(GroceryListInfoEntry._ID);
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
        View itemView = mLayoutInflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Move the cursor to the correct row
        mCursor.moveToPosition(position);

        // Get the actual values
        String groceryItem = mCursor.getString(mGroceryItemPosition);
        String groceryItemCost = mCursor.getString(mGroceryItemCostPosition);
        String groceryItemAisle = mCursor.getString(mGroceryItemAislePosition);
        int groceryItemInCartList = mCursor.getInt(mGroceryItemInCartPosition);
        int id = mCursor.getInt(mIdPosition);

        // Pass the information to the holder
        holder.mGroceryItem.setText(groceryItem);
        holder.mGroceryItemCost.setText(groceryItemCost);
        holder.mGroceryItemAisle.setText(groceryItemAisle);
        holder.mGroceryItemInCart = groceryItemInCartList;
        holder.mId = id;
    }

    @Override
    public int getItemCount() {
        // If the cursor is null, return 0. Otherwise return the count of records in it.
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Member variables for inner class
        public final TextView mGroceryItem;
        public final TextView mGroceryItemCost;
        public final TextView mGroceryItemAisle;
        public int mGroceryItemInCart;
        public int mId;
        public CardView mCardView;
        public boolean mItemInCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mGroceryItem = (TextView) itemView.findViewById(R.id.list_grocery_item_title);
            mGroceryItemCost = (TextView) itemView.findViewById(R.id.list_item_cost);
            mGroceryItemAisle = (TextView) itemView.findViewById(R.id.list_item_aisle);
            mCardView = (CardView) itemView.findViewById(R.id.shopping_list_card_view);
            mItemInCart = false;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Make in cart change here");
                    if (!mItemInCart) {
                        mItemInCart = true;
                        mCardView.setCardBackgroundColor(Color.BLUE);
                    } else {
                        mItemInCart = false;
                        mCardView.setCardBackgroundColor(Color.GRAY);
                    }

//                    Intent intent = new Intent(mContext, GroceryActivity.class);
//                    intent.putExtra(GroceryActivity.GROCERY_ITEM_ID, mId);
//                    System.out.println(mGroceryItem.getText().toString());
//                    mContext.startActivity(intent);
                }
            });
        }
    }

}
