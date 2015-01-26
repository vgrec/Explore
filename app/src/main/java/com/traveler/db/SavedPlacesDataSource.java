package com.traveler.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.traveler.models.db.SavedPlace;

import java.util.ArrayList;
import java.util.List;

import static com.traveler.db.DatabaseHelper.COLUMN_ID;
import static com.traveler.db.DatabaseHelper.COLUMN_IMAGE_URL;
import static com.traveler.db.DatabaseHelper.COLUMN_PLACE_ID;
import static com.traveler.db.DatabaseHelper.COLUMN_TITLE;
import static com.traveler.db.DatabaseHelper.TABLE_SAVED_LOCATIONS;

/**
 * Provides convenient methods to perform database operations (insert/get) on locations.
 *
 * @author vgrec, created on 1/22/15.
 */
public class SavedPlacesDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            COLUMN_ID,
            COLUMN_PLACE_ID,
            COLUMN_IMAGE_URL,
            COLUMN_TITLE,
    };

    public SavedPlacesDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long savePlace(SavedPlace savedPlace) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLACE_ID, savedPlace.getPlaceId());
        values.put(COLUMN_IMAGE_URL, savedPlace.getImageUrl());
        values.put(COLUMN_TITLE, savedPlace.getTitle());

        return database.insert(TABLE_SAVED_LOCATIONS, null, values);
    }

    /**
     * Returns a list of Locations depending of Location.Type
     */
    public List<SavedPlace> getPlaces() {
        List<SavedPlace> savedPlaces = new ArrayList<>();
        Cursor cursor = database.query(TABLE_SAVED_LOCATIONS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SavedPlace savedPlace = cursorToSavedPlace(cursor);
            savedPlaces.add(savedPlace);
            cursor.moveToNext();
        }
        cursor.close();
        return savedPlaces;
    }

    private SavedPlace cursorToSavedPlace(Cursor cursor) {
        SavedPlace savedPlace = new SavedPlace();
        savedPlace.setId(cursor.getLong(0));
        savedPlace.setPlaceId(cursor.getString(1));
        savedPlace.setImageUrl(cursor.getString(2));
        savedPlace.setTitle(cursor.getString(3));
        return savedPlace;
    }

    public boolean isPlaceSaved(String placeId) {
        boolean isPlaceSaved = false;
        String where = COLUMN_PLACE_ID + "=" + "'" + placeId + "'";
        Cursor cursor = database.query(TABLE_SAVED_LOCATIONS, allColumns, where, null, null, null, null);
        if (cursor != null) {
            isPlaceSaved = cursor.getCount() > 0;
            cursor.close();
        }
        return isPlaceSaved;
    }

    public boolean removePlace(String placeId) {
        String where = COLUMN_PLACE_ID + "=" + "'" + placeId + "'";
        return database.delete(TABLE_SAVED_LOCATIONS, where, null) > 0;
    }
}
