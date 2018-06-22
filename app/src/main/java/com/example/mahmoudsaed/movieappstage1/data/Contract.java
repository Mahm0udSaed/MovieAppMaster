package com.example.mahmoudsaed.movieappstage1.data;

import android.net.Uri;

/**
 * Created by Mahmoud Saed on 1/23/2018.
 */

public class Contract {

    public static final  String TABLE_NAME="userData";

    public static final Uri CONTENT=Uri.parse("content://com.example.mahmoudsaed.movieappstage1/userData").buildUpon().build();

    public static final String ID="id";
    public static final String MOVIE_NAME="movie_name";
    public static final String MOVIE_PICTURE="movie_picture";




}
