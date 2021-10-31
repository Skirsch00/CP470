package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;


public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "Messages.db", KEY_ID = "ID", KEY_MESSAGE = "Message", TABLE_NAME = "Messages";
    public static int VERSION_NUM = 2;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        String TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_MESSAGE + " TEXT NOT NULL" +
                ")";

        db.execSQL(TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
