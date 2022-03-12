package edu.cvtc.wkugel1.groceryshoppingapp.info;

import android.os.Parcel;
import android.os.Parcelable;

public class GroceryItemInfo implements Parcelable{

    // Member Attributes
    private String mGroceryItem;
    private String mCost;
    private String mAisle;
    private int mAddToList;
    private int mId;

    // Overload Constructors
    public GroceryItemInfo(String item, String cost, String aisle, int addToList) {
        mGroceryItem = item;
        mCost = cost;
        mAisle = aisle;
        mAddToList = addToList;
    }

    public GroceryItemInfo(int id, String item, String cost, String aisle, int addToList) {
        mId = id;
        mGroceryItem = item;
        mCost = cost;
        mAisle = aisle;
        mAddToList = addToList;
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

    public String getAisle() { return mAisle; }

    public int getAddToList() { return mAddToList; }

    public void setTitle(String title) {
        mGroceryItem = title;
    }

    public void setDescription(String description) {
        mCost = description;
    }

    public void setAisle(String aisle) { mAisle = aisle; }

    public void setAddToList(int addToList) { mAddToList = addToList; }

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
        mAisle = parcel.readString();
        mAddToList = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mGroceryItem);
        parcel.writeString(mCost);
        parcel.writeString(mAisle);
        parcel.writeInt(mAddToList);
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
