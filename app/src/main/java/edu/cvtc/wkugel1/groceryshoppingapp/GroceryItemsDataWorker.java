package edu.cvtc.wkugel1.groceryshoppingapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import edu.cvtc.wkugel1.groceryshoppingapp.GroceryItemDatabaseContract.GroceryItemInfoEntry;

public class GroceryItemsDataWorker {

    //Member Attributes
    private SQLiteDatabase mDb;

    // Constructor
    public GroceryItemsDataWorker(SQLiteDatabase db) {
        mDb = db;
    }

    private void insertCourse(String item, String cost) {
        ContentValues values = new ContentValues();
        values.put(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM, item);
        values.put(GroceryItemInfoEntry.COLUMN_GROCERY_ITEM_COST, cost);

        long newRowId = mDb.insert(GroceryItemInfoEntry.TABLE_NAME, null, values);
    }

    public void insertCourses() {
        insertCourse("Intro to Computers & Programming", "Introductory Computer  Course");
        insertCourse("Web 1 - HTML & CSS", "Introductory HTML course");
        insertCourse("Programming Fundamentals", "Introductory Programming Course");
        insertCourse("Database 1", "Introductory Database Course");
        insertCourse("Object Oriented Programming", "Second Semester Programming Course using Java");
        insertCourse(".NET Application Development", "Second Semester Programming Course using .NET");
        insertCourse("Database 2", "Intermediate Database Course");
        insertCourse("Android Development", "Application Development Course with Android");
    }

}
