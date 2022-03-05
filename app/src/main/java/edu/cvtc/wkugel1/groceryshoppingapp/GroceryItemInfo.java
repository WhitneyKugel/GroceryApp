package edu.cvtc.wkugel1.groceryshoppingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class GroceryItemInfo implements Parcelable{

    // Member Attributes
    private String mGroceryItem;
    private String mCost;
    private int mId;

    // Overload Constructors
    public GroceryItemInfo(String item, String cost) {
        mGroceryItem = item;
        mCost = cost;
    }

    public GroceryItemInfo(int id, String item, String cost) {
        mId = id;
        mGroceryItem = item;
        mCost = cost;
    }

    // Getters and Setters
    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mGroceryItem;
    }

    public String getDescription() {
        return mCost;
    }

    public void setTitle(String title) {
        mGroceryItem = title;
    }

    public void setDescription(String description) {
        mCost = description;
    }

    private String getCompareKey() {
        return mGroceryItem + "|" + mCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroceryItemInfo that = (GroceryItemInfo) o;
        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }

    protected GroceryItemInfo(Parcel parcel) {
        mGroceryItem = parcel.readString();
        mCost = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mGroceryItem);
        parcel.writeString(mCost);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GroceryItemInfo> CREATOR = new Creator<GroceryItemInfo>() {
        @Override
        public GroceryItemInfo createFromParcel(Parcel parcel) {
            return new GroceryItemInfo(parcel);
        }

        @Override
        public GroceryItemInfo[] newArray(int size) {
            return new GroceryItemInfo[size];
        }
    };
}
