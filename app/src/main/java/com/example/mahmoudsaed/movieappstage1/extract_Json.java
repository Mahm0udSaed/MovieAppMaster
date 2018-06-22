package com.example.mahmoudsaed.movieappstage1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.jar.JarOutputStream;


public class extract_Json {



    public static String[][] imageUrl(String s) throws JSONException {

        String img_path[][]=null;

        JSONObject api=new JSONObject(s);

        JSONArray  results=api.getJSONArray("results");
       img_path=new String[results.length()][6];


        for (int i=0;i<results.length();i++){
            JSONObject object=results.getJSONObject(i);
            String v[]=null;
            v=new String[6];
          //  v[0]=object.getString("poster_path");
            v[0]=object.getString( "original_title");
            v[1]=object.getString("poster_path");
            v[2]=object.getString("overview");
            v[3]=object.getDouble("vote_average")+"";
            v[4]=object.getString("release_date");
            v[5]=object.getInt("id")+"";
         //   .d("extract_json",img_path[i]);


                for(int j=0;j<=5;j++){
                    img_path[i][j]=v[j];
                   // Log.d("extract_json",img_path[i][j]);
                }


        }

        return img_path;

    }

    public static ArrayList<String> Trailer_url(String s) throws JSONException {
        ArrayList<String> Trailer=new ArrayList<String>();
        JSONObject ob=new JSONObject(s);
        JSONArray array=ob.getJSONArray("results");
     int x=0;
        for(int i=0;i<array.length();i++){
            JSONObject object=array.getJSONObject(i);
            if(object.getString("type").equals("Trailer")){

                Trailer.add(object.getString("key"));
                 Log.d("extract_json",Trailer.get(x));
                x++;
            }
        }

     return  Trailer;
    }


public static ArrayList<String> Reviews_url(String s) throws JSONException {
    ArrayList<String> Reviews=new ArrayList<String>();
    JSONObject jsonObject=new JSONObject(s);

    JSONArray jsonArray=jsonObject.getJSONArray("results");
    for (int i=0;i<jsonArray.length();i++){
        JSONObject js=jsonArray.getJSONObject(i);
        Reviews.add(js.getString("author")+"-"+js.getString("content"));

        // Log.d("extract_Json",Reviews.get(i));
    }

  return Reviews;
}

}
