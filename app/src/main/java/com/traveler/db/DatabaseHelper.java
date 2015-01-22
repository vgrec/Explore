package com.traveler.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Defines the database tables and columns, and the creation of it.
 *
 * @author vgrec, created on 1/22/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "savedlocations.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SAVED_LOCATIONS = "SavedLocations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TYPE = "type";

    private static final String DATABASE_CREATE =
            "create table " + TABLE_SAVED_LOCATIONS
                    + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_IMAGE_URL + " text, "
                    + COLUMN_TITLE + " text not null, "
                    + COLUMN_TYPE + " text not null"
                    + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_LOCATIONS);
        onCreate(db);
    }
}
