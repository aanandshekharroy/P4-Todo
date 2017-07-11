package com.c3cyberclub.p5_todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by theseus on 10/7/17.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "to_do_database";

    // Contacts table name
    public static final String TABLE_TODOS = "Todos";

    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DEADLINE = "deadline";
    public static final String KEY_CREATED_AT="created_at";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TODOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DEADLINE + " TEXT,"
                + KEY_CREATED_AT + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
    }
}