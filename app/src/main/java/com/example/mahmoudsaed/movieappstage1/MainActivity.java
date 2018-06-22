package com.example.mahmoudsaed.movieappstage1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoudsaed.movieappstage1.data.Contract;
import com.example.mahmoudsaed.movieappstage1.data.dbHelper;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements ImageAdapter.ImageAdapterInterface {



    public  int id;

  public RecyclerView recyclerView;
    public ImageAdapter img;
    public TextView textView;
    private String array[];
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.error);
        GridLayoutManager gd=new GridLayoutManager(this,2);
        recyclerView=(RecyclerView)findViewById(R.id.re1);
        recyclerView.setLayoutManager(gd);
        column_space itemDecoration = new column_space(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        img=new ImageAdapter(this,this);
        recyclerView.setAdapter(img);
        boolean re=isOnline();

      if(re==true){
        loadData("popular");
      }else{
           textView.setVisibility(View.VISIBLE);
        }

       }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void loadData(String s){
        new connectToNetwork().execute(s);
    }

    @Override
    public void onclick(String s[]) {
        Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(intent);
    }
    



    public class connectToNetwork extends AsyncTask<String,Void,String[][]>{

     @Override
     protected String[][] doInBackground(String... params) {
//String s=null;
     // if(params[0]!=null){
       //   s=params[0];
      //}

       URL url=NetworkUtils.buildUrl(params[0]);
         try {
             String s=NetworkUtils.getResponseFromHttpUrl(url);
             String image_url[][]=extract_Json.imageUrl(s);
             for(int k=0;k<image_url.length;k++){
                 for(int j=0;j<5;j++) {
                    // Log.d("MainActivity", image_url[k][j]);
                 }
             }
             return image_url;

         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }

     }

     @Override
     protected void onPostExecute(String[][] strings) {
         if(strings!=null) {
             img.setImage(strings);
         }
     }
 }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();

        menuInflater.inflate(R.menu.main,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      // item.set(true);
         id=item.getItemId();
        if(id==R.id.popular){

            img.setBitmaps(null);
        loadData("popular");
            return true;

        }else if(id==R.id.top){
            img.setBitmaps(null);
            loadData("top_rated");
        return true;
        }else if(id==R.id.favourites){

            displayFavourites();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void displayFavourites() {


        dbHelper db= new dbHelper(getApplicationContext());
        SQLiteDatabase sq=db.getWritableDatabase();
        Cursor cs= sq.query(Contract.TABLE_NAME,null,null,null,null,null,null);

        String data[][] = new String[cs.getCount()][2];
        String bitmaps[]=new String[cs.getCount()];

       // Log.d("MainActivity","okkk");
        int o=0;
        while (cs.moveToPosition(o)) {

            String k = cs.getString(cs.getColumnIndex(Contract.MOVIE_PICTURE));
            bitmaps[o]=k;

            Uri ur = Uri.parse(k);
            data[o][0]=cs.getString(cs.getColumnIndex(Contract.MOVIE_NAME));





            // img1.setImageBitmap(bitmap);


            o++;

        }
        img.setBitmaps(bitmaps);
        img.setImage(data);
    }
}
