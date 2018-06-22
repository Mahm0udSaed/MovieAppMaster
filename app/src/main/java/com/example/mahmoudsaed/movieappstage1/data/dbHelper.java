package com.example.mahmoudsaed.movieappstage1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mahmoud Saed on 1/23/2018.
 */

public class dbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="user.db";
    private static int VERSION=1;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql="CREATE TABLE "+  Contract.TABLE_NAME+ " (" +
                Contract.ID +" INTEGER PRIMARY KEy, "+
                Contract.MOVIE_NAME+" TEXT NOT NULL, "+
                Contract.MOVIE_PICTURE+" TEXT NOT NULL);";
        db.execSQL(sql);
    }

   /* final String CREATE_TABLE = "CREATE TABLE "  + TaskEntry.TABLE_NAME + " (" +
            TaskEntry._ID                + " INTEGER PRIMARY KEY, " +
            TaskEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            TaskEntry.COLUMN_PRIORITY    + " INTEGER NOT NULL);";

        db.execSQL(CREATE_TABLE);*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+Contract.TABLE_NAME);
        onCreate(db);

    }
}
