package edu.cvtc.wkugel1.groceryshoppingapp.info;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuMealInfo implements Parcelable{

    // Member Attributes
    private String mMenuMeal;
    private String mMenuDay;
    private String mMenuType;
    private int mId;

    // Overload Constructors
    public MenuMealInfo(String meal, String day, String type) {
        mMenuMeal = meal;
        mMenuDay = day;
        mMenuType = type;
    }

    public MenuMealInfo(int id, String meal, String day, String type) {
        mId = id;
        mMenuMeal = meal;
        mMenuDay = day;
        mMenuType = type;
    }

    // Getters and Setters
    public int getId() {
        return mId;
    }

    public String getMenuMeal() {
        return mMenuMeal;
    }

    public String getMenuDay() {
        return mMenuDay;
    }

    public String getMenuType() {
        return mMenuType;
    }

    public void setMenuMeal(String menuMeal) {
        mMenuMeal = menuMeal;
    }
    public void setMenuDay(String menuDay) {
        mMenuDay = menuDay;
    }
    public void setMenuType(String menuType) {
        mMenuType = menuType;
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
        mMenuDay = parcel.readString();
        mMenuType = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mMenuMeal);
        parcel.writeString(mMenuDay);
        parcel.writeString(mMenuType);
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
