package com.example.mahmoudsaed.movieappstage1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Mahmoud Saed on 10/2/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.childViewHolder> {
    private   String[][] Images;
    private String []bitmaps;

    private final ImageAdapterInterface minterface;
public interface ImageAdapterInterface{
    public void onclick(String s[]);
}


    Context context;
    public ImageAdapter(Context context,ImageAdapterInterface imageinterfaceinstance){

     minterface=imageinterfaceinstance;
      this.context=context;

    }
    public void setImage(String images[][]){
        Images=images;

        notifyDataSetChanged();
    }
    public void setBitmaps(String bitmaps[]){
        this.bitmaps=bitmaps;
        notifyDataSetChanged();
    }

    @Override
    public childViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int inf=R.layout.list_item;
        LayoutInflater inflater=LayoutInflater.from(context);

        View v=inflater.inflate(inf,parent,false);
        childViewHolder childViewHolder=new childViewHolder(v);

        return childViewHolder;
    }

    @Override
    public void onBindViewHolder(childViewHolder holder, int position) {
       // Log.d("imageAdapter",Images[position]);

       //  Log.d("imageAdapter","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

     holder.bindImage(position);
    }


    @Override
    public int getItemCount() {
        if (Images==null){
           // Log.d("imageAdapter","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            return 0;
        }
       //  Log.d("imageAdapter",Images.length+"");
        return Images.length;
    }

    class childViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


public final ImageView imageView;

    public childViewHolder(View v){
        super(v);

        imageView=(ImageView)v.findViewById(R.id.image_id);
        v.setOnClickListener(this);

    }

void bindImage(int position){
    // Log.d("imageAdapter",Images[position][1]);
    if (bitmaps!=null){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(bitmaps[position]));



        } catch (IOException e) {

        }
            imageView.setImageBitmap(bitmap);

    }else {

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + Images[position][1]).into(imageView);
    }
    /*Picasso.with(context)
            .load("http://image.tmdb.org/t/p/w185/"+Images[position][1])
             .into(getLocation());*/


}



        @Override
        public void onClick(View v) {
            if (bitmaps == null) {
                int pos = getAdapterPosition();
                String s[] = Images[pos];

                minterface.onclick(s);
            }else{
                //Log.d("ImageAdapter","okkkk");
                String s[]=new String[2];
                        s[0]=bitmaps[getAdapterPosition()];
                        s[1]=Images[getAdapterPosition()][0];
                minterface.onclick(s);
            }
        }
    }


}
