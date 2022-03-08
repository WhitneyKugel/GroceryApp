package edu.cvtc.wkugel1.groceryshoppingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuMealInfo implements Parcelable{

    // Member Attributes
    private String mMenuMeal;
    private int mId;

    // Overload Constructors
    public MenuMealInfo(String meal) {
        mMenuMeal = meal;
    }

    public MenuMealInfo(int id, String meal) {
        mId = id;
        mMenuMeal = meal;
    }

    // Getters and Setters
    public int getId() {
        return mId;
    }

    public String getMenuMeal() {
        return mMenuMeal;
    }

    public void setMenuName(String menuMeal) {
        mMenuMeal = menuMeal;
    }

    private String getCompareKey() {
        return mMenuMeal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuMealInfo that = (MenuMealInfo) o;
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

    protected MenuMealInfo(Parcel parcel) {
        mMenuMeal = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mMenuMeal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MenuMealInfo> CREATOR = new Parcelable.Creator<MenuMealInfo>() {
        @Override
        public MenuMealInfo createFromParcel(Parcel parcel) {
            return new MenuMealInfo(parcel);
        }

        @Override
        public MenuMealInfo[] newArray(int size) {
            return new MenuMealInfo[size];
        }
    };
}
