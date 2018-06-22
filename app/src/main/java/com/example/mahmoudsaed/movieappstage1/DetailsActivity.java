package com.example.mahmoudsaed.movieappstage1;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoudsaed.movieappstage1.data.Contract;
import com.example.mahmoudsaed.movieappstage1.data.dbHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements trailerAdapter.trailerAdapterInterface,View.OnClickListener{

      private TextView t1,t2,t3,trailer,review;
    private Button btn1;
    private ImageView img1;
    private RecyclerView re_trailers,re_reviews;
    private static final int loader1=20;
    private static final int loader2=21;
    private static  int flag=1;
    private trailerAdapter tr,tr1;

    String s[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        t1 = (TextView) findViewById(R.id.title);
        t2 = (TextView) findViewById(R.id.riText);
        t3 = (TextView) findViewById(R.id.overview);
        img1=(ImageView)findViewById(R.id.img);
        re_trailers=(RecyclerView)findViewById(R.id.re2);
        re_reviews=(RecyclerView)findViewById(R.id.re3);

        trailer=(TextView)findViewById(R.id.trailerText);

        review=(TextView) findViewById(R.id.reviewsText);
        review.setOnClickListener(this);
        btn1=(Button)findViewById(R.id.fav);
        btn1.setOnClickListener(this);

        LinearLayoutManager lm=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        re_trailers.setLayoutManager(lm);
        re_trailers.setHasFixedSize(true);
         tr=new trailerAdapter(this);

        re_trailers.setAdapter(tr);
        LinearLayoutManager lm2=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

       re_reviews.setLayoutManager(lm2);
        tr1=new trailerAdapter(this);
        re_reviews.setHasFixedSize(true);
        re_reviews.setAdapter(tr1);
        //////////////////////////
        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(Intent.EXTRA_TEXT)){
                s = i.getStringArrayExtra(Intent.EXTRA_TEXT);
                if(s.length==2){
                    trailer.setVisibility( View.GONE);
                    review.setVisibility( View.GONE);
                    btn1.setVisibility(View.GONE);
                    t1.setText(s[1]);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(s[0]));



                    } catch (IOException e) {

                    }
                    img1.setImageBitmap(bitmap);

                }else {

                    t1.setText(s[0]);


                    Picasso.with(this).load("http://image.tmdb.org/t/p/w185/" + s[1]).into(img1);

                    t2.setText(s[3] + "\n" + s[4]);
                    t3.setText(s[2]);

                    //trailer and reviews

                new async1().execute();
                 new async2().execute();





                }

            }
        }


    }


    private  Target getTarget(final String url, final String name ){
        Target target = new Target(){


            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    private String p2;
                    String path;
                    @Override
                    public void run() {
                        dbHelper db= new dbHelper(getApplicationContext());
                        SQLiteDatabase sq=db.getWritableDatabase();
                        int per_check= ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                     if(per_check!=getPackageManager().PERMISSION_GRANTED){
                         ActivityCompat.requestPermissions(
                                 DetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1 );
                     }else {
                         File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url+".jpg");
                         Log.d("DetailsActivity",file.getAbsolutePath());
                         try {
                             file.createNewFile();
                             FileOutputStream ostream = new FileOutputStream(file);
                             bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                             ostream.flush();
                             ostream.close();
                             path=  MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,url,null);

                         } catch (IOException e) {
                             Log.e("IOException", e.getLocalizedMessage());
                         }
                      //   Log.d("DetailsActivity",file.getAbsolutePath());
                         ContentValues cv=new ContentValues();
                         cv.put(Contract.MOVIE_PICTURE,path);
                         cv.put(Contract.MOVIE_NAME,name);
                         cv.put(Contract.ID,url);
                         Cursor cs= getApplicationContext().getContentResolver().query(Contract.CONTENT,null,null,null,null);
                         int i=0;
                         int flag=0;
                          while (cs.moveToPosition(i)) {
                              if(!cs.getString(cs.getColumnIndex(Contract.ID)).equals(url)) {
                                  i++;
                              }else {
                                  flag=1;
                                  break;
                              }
                          }
                          if(flag==0){
                              getApplicationContext().getContentResolver().insert(Contract.CONTENT, cv);
                          }



                         Log.d("DetailsActivity",Contract.TABLE_NAME);

                     }
                       // photoFile=file.getAbsolutePath();

                    }


                }).start();

            }



            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }




    class async1 extends AsyncTask<Void,Void,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            Uri uri=Uri.parse("https://api.themoviedb.org/3/movie/"+s[5]+"/videos").buildUpon()
                    .appendQueryParameter("api_key","38a8f4db2353ee0d37f5d85c9cf79f2f").build();
            Log.d("DetailsActivity",uri.toString());
            URL url=null;

            try {
                url = new URL(uri.toString());
                // Log.d("DetailsActivity",url.toString());
                String res = NetworkUtils.getResponseFromHttpUrl(url);


                    flag = 2;
                    ArrayList<String> extraction = extract_Json.Trailer_url(res);
                    //  Log.d("DetailsActivity",extraction.get(0));

                    return extraction;

            }catch(Exception ex){
                    return null;
                }

        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            s.add("2");
            tr.setImage(s);
        }
    }
    class async2 extends AsyncTask<Void,Void,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            Uri uri=Uri.parse("https://api.themoviedb.org/3/movie/"+s[5]+"/reviews").buildUpon()
                    .appendQueryParameter("api_key","38a8f4db2353ee0d37f5d85c9cf79f2f").build();
            Log.d("DetailsActivity",uri.toString());
            URL url=null;

            try {
                url = new URL(uri.toString());
                // Log.d("DetailsActivity",url.toString());
                String res = NetworkUtils.getResponseFromHttpUrl(url);


                flag = 2;
                ArrayList<String> extraction = extract_Json.Reviews_url(res);
                //  Log.d("DetailsActivity",extraction.get(0));

                return extraction;

            }catch(Exception ex){
                return null;
            }
        }

        @Override
        protected void onPostExecute( ArrayList<String> s) {
            super.onPostExecute(s);

            tr1.setImage(s);

        }
    }
    @Override
    public void onclick(String s) {
        Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v="+s));
        startActivity(i);
    }

     @Override
    public void onClick(View v) {
        if(!(v.getId()==R.id.reviewsText)) {
            Picasso.with(this)
                    .load("http://image.tmdb.org/t/p/w185/" + s[1])
                    .placeholder(R.drawable.user_placeholder).into(getTarget(s[5], s[0]));
        }



    }
}
