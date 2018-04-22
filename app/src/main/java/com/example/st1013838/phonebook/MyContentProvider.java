package com.example.st1013838.phonebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Steven on 4/22/2018.
 */

public class MyContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.st1013838.phonebook.MyContentProvider";
    private static final String DATABASE_NAME = "PhoneBook.db";
    private static final String TABLE_ITEM = "Contacts";

    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String PHONE_NUMBER = "PhoneNumber";

    private static final int DATABASE_VERSION = 1;

    public static final int CONTACTS = 1;
    public static final int CONTACTS_ID = 2;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ITEM);

    private SQLiteDatabase sqlDB;
    private UriMatcher uriMatcher;

    public  MyContentProvider(){
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int rowsDeleted = 0;
        switch (uriType){
            case CONTACTS: rowsDeleted = sqlDB.delete(TABLE_ITEM, selection, selectionArgs);
            break;
            default: throw new UnsupportedOperationException("Unknown URI: " + uri + " is not supported.");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriTtpe = uriMatcher.match(uri);
        long id = 0;
        switch (uriTtpe){
            case CONTACTS: sqlDB.insert(TABLE_ITEM,null, values);
                break;
            default: throw  new  UnsupportedOperationException("Unknown URI: " + uri +
                    " is not supported");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return uri.parse(TABLE_ITEM + "/" + id);

    }

    @Override
    public boolean onCreate() {
        //next three lines are used to start a content provider class
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_ITEM, CONTACTS);
        uriMatcher.addURI(AUTHORITY, TABLE_ITEM +"/#", CONTACTS_ID);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        if (sqlDB != null)
            return true;
        else
            return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_ITEM);
        int uriType = uriMatcher.match(uri);
        switch (uriType){
            case CONTACTS: break;
            default:throw  new  UnsupportedOperationException("Unknown URI: " + uri +
                    " is not supported");
        }
        Cursor cursor = queryBuilder.query(sqlDB,projection,selection,selectionArgs,
                null,null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return  cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int rowsUpdated = 0;
        int uriTtpe = uriMatcher.match(uri);
        switch (uriTtpe){
            case CONTACTS: rowsUpdated =
                    sqlDB.update(TABLE_ITEM, values,selection,selectionArgs);
                break;
            default: throw  new  UnsupportedOperationException("Unknown URI: " + uri +
                    " is not supported");
        }

        return rowsUpdated;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context c){
            super(c, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            String create_product_table = "CREATE TABLE " + TABLE_ITEM +"(" +
                    FIRST_NAME +" TEXT," + LAST_NAME + " TEXT," + PHONE_NUMBER + " TEXT )";
            db.execSQL(create_product_table);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d("MyDBHandler", "Updating db from version " + oldVersion + " to " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
            onCreate(db);
        }

    }
}
