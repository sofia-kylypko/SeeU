package com.test.seeu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SeeU_DB";
    public static final String TABLE_ATTRACTIONS = "Attractions_table";

    public static final String KEY_ID = "_id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_NAME = "name";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_DETAILS = "details";
    public static final String KEY_ATTRACT = "attract";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_ATTRACTIONS + " (" +
                KEY_ID + " integer primary key, " +
                KEY_IMAGE + " text, " +
                KEY_NAME + " text, " +
                KEY_AUTHOR + " text, " +
                KEY_DETAILS + " text," +
                KEY_ATTRACT + " integer" +
                ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_ATTRACTIONS);

        onCreate(sqLiteDatabase);
    }
}
