package com.example.mahmoudsaed.movieappstage1.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class provider extends ContentProvider {
    private dbHelper helper;



    @Override
    public boolean onCreate() {
        helper=new dbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query(Contract.TABLE_NAME,projection
        ,selection,selectionArgs,null,null,sortOrder
        );


        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
       final SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();
        long id=sqLiteDatabase.insert(Contract.TABLE_NAME,null,values);
        if(id<=0){
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }




        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
