package com.example.mahmoudsaed.movieappstage1;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

     public static String url1="https://api.themoviedb.org/3/movie/";
     public static String key="38a8f4db2353ee0d37f5d85c9cf79f2f";

    public static URL buildUrl(String s){
        Uri uri=Uri.parse(url1+s).buildUpon().appendQueryParameter("api_key",key).build();
        URL url=null;

        try {
            url=new URL(uri.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
