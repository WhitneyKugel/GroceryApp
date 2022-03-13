package edu.cvtc.wkugel1.groceryshoppingapp.info;

import android.os.Parcel;
import android.os.Parcelable;

public class GroceryListInfo implements Parcelable {

    // Member Attributes
    private String mGroceryItem;
    private String mCost;
    private String mAisle;
    private int mItemInCart;
    private int mId;

    // Overload Constructors
    public GroceryListInfo(String item, String cost, String aisle, int itemInCart) {
        mGroceryItem = item;
        mCost = cost;
        mAisle = aisle;
        mItemInCart = itemInCart;
    }

    public GroceryListInfo(int id, String item, String cost, String aisle, int itemInCart) {
        mId = id;
        mGroceryItem = item;
        mCost = cost;
        mAisle = aisle;
        mItemInCart = itemInCart;
    }


    public String getGroceryItem() {
        return mGroceryItem;
    }

    public void setGroceryItem(String mGroceryItem) {
        this.mGroceryItem = mGroceryItem;
    }

    public String getCost() {
        return mCost;
    }

    public void setCost(String mCost) {
        this.mCost = mCost;
    }

    public String getAisle() {
        return mAisle;
    }

    public void setAisle(String mAisle) {
        this.mAisle = mAisle;
    }

    public int getItemInCart() {
        return mItemInCart;
    }

    public void setItemInCart(int mItemInCart) {
        this.mItemInCart = mItemInCart;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    private String getCompareKey() {
        return mGroceryItem + "|" + mCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroceryListInfo that = (GroceryListInfo) o;
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

    protected GroceryListInfo(Parcel parcel) {
        mGroceryItem = parcel.readString();
        mCost = parcel.readString();
        mAisle = parcel.readString();
        mItemInCart = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mGroceryItem);
        parcel.writeString(mCost);
        parcel.writeString(mAisle);
        parcel.writeInt(mItemInCart);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GroceryListInfo> CREATOR = new Creator<GroceryListInfo>() {
        @Override
        public GroceryListInfo createFromParcel(Parcel parcel) {
            return new GroceryListInfo(parcel);
        }

        @Override
        public GroceryListInfo[] newArray(int size) {
            return new GroceryListInfo[size];
        }
    };
}
