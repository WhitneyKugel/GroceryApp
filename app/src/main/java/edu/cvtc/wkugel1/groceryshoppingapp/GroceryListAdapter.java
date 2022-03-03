package edu.cvtc.wkugel1.groceryshoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.WordViewHolder> {

    private final LinkedList<String> mGroceryList;
    private LayoutInflater mInflater;

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView wordItemView;
        final GroceryListAdapter mAdapter;
        // Check is item is checked
        boolean checked = false;

        public WordViewHolder(View itemView, GroceryListAdapter adapter) {
            super(itemView);
            wordItemView = (TextView) itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            // Use that to access the affected item in mWordList.
            String element = mGroceryList.get(mPosition);

            // Change the word in the mWordList.
            if (!checked) {
                mGroceryList.set(mPosition, "Checked! " + mPosition);
                checked = true;
            } else {
                mGroceryList.set(mPosition, "Unclicked! " + mPosition);
                checked = false;
            }


            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public GroceryListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.grocery_list_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListAdapter.WordViewHolder holder, int position) {
        String mCurrent = mGroceryList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mGroceryList.size();
    }

    public GroceryListAdapter(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mGroceryList = wordList;
    }

}

