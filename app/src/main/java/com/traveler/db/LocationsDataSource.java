package com.traveler.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.traveler.models.db.Location;

import java.util.ArrayList;
import java.util.List;

import static com.traveler.db.DatabaseHelper.COLUMN_ID;
import static com.traveler.db.DatabaseHelper.COLUMN_IMAGE_URL;
import static com.traveler.db.DatabaseHelper.COLUMN_TITLE;
import static com.traveler.db.DatabaseHelper.COLUMN_TYPE;
import static com.traveler.db.DatabaseHelper.TABLE_SAVED_LOCATIONS;

/**
 * Provides convenient methods to perform database operations (insert/get) on locations.
 *
 * @author vgrec, created on 1/22/15.
 */
public class LocationsDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            COLUMN_ID,
            COLUMN_IMAGE_URL,
            COLUMN_TITLE,
            COLUMN_TYPE
    };

    public LocationsDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_URL, location.getImageUrl());
        values.put(COLUMN_TITLE, location.getTitle());
        values.put(COLUMN_TYPE, location.getType().toString());

        return database.insert(TABLE_SAVED_LOCATIONS, null, values);
    }

    public List<Location> getPlaces() {
        return getLocations(Location.Type.PLACE);
    }

    public List<Location> getLocalities() {
        return getLocations(Location.Type.LOCALITY);
    }

    /**
     * Returns a list of Locations depending of Location.Type
     */
    private List<Location> getLocations(Location.Type type) {
        List<Location> locations = new ArrayList<>();

        String where = COLUMN_TYPE + "=" + "'" + type.toString() + "'";
        Cursor cursor = database.query(TABLE_SAVED_LOCATIONS, allColumns, where, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor);
            locations.add(location);
            cursor.moveToNext();
        }
        cursor.close();
        return locations;
    }

    private Location cursorToLocation(Cursor cursor) {
        Location location = new Location();
        location.setId(cursor.getLong(0));
        location.setImageUrl(cursor.getString(1));
        location.setTitle(cursor.getString(2));
        location.setType(Location.Type.valueOf(cursor.getString(3)));
        return location;
    }
}
