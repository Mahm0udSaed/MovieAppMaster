package com.example.mahmoudsaed.movieappstage1;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mahmoud Saed on 1/22/2018.
 */

public class trailerAdapter extends RecyclerView.Adapter<trailerAdapter.childViewHolder> {
    private ArrayList<String> tra_rev;

      private final trailerAdapter.trailerAdapterInterface minterface;
    public interface trailerAdapterInterface{
        public void onclick(String s);
    }


    Context context;
    public trailerAdapter(trailerAdapterInterface imageinterfaceinstance){

         minterface=imageinterfaceinstance;


    }
    public void setImage(ArrayList<String> tra_rev){
        this.tra_rev=tra_rev;

        notifyDataSetChanged();
    }

    @Override
    public trailerAdapter.childViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int inf=R.layout.tra;
        LayoutInflater inflater=LayoutInflater.from(context);

        View v=inflater.inflate(inf,parent,false);
        trailerAdapter.childViewHolder childViewHolder=new trailerAdapter.childViewHolder(v);

        return childViewHolder;
    }

    @Override
    public void onBindViewHolder(trailerAdapter.childViewHolder holder, int position) {
        // Log.d("trailerAdapter",tra_rev[position]);

        //  Log.d("trailerAdapter","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

        holder.bindImage(position);
    }


    @Override
    public int getItemCount() {
        if (tra_rev==null){
            // Log.d("trailerAdapter","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            return 0;
        }
        //  Log.d("trailerAdapter",tra_rev.length+"");
        return tra_rev.size();
    }

    class childViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView imageView;
        public final TextView t1,t3;


        public childViewHolder(View v){
            super(v);

            imageView=(ImageView)v.findViewById(R.id.img1);
            t1=(TextView)v.findViewById(R.id.t1);
            t3=(TextView)v.findViewById(R.id.t3);



            v.setOnClickListener(this);

        }

        void bindImage(int position){
            // Log.d("trailerAdapter",tra_rev[position][1]);

            if(tra_rev.get(tra_rev.size()-1).equals("2")){
                //   Log.d("trailerAdapter","okkkkkkkkkkk");
                imageView.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
                if(tra_rev.get(position).equals("2")){
                    imageView.setVisibility(View.GONE);
                    t1.setVisibility(View.GONE);
                }
               else {

                    t1.setText("Trailer" + (position));
                }
            }else {

                imageView.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                t3.setVisibility(View.VISIBLE);
                String s=tra_rev.get(position);
                // String m[]=s.split("-");

                t3.setText(s);


            }

        }

        @Override
        public void onClick(View v) {
           /* int pos=getAdapterPosition();
            String s[]= tra_rev[pos];
            minterface.onclick(s);*/
           if(tra_rev.get(tra_rev.size()-1).equals("2")){
              // Log.d("trailerAdapter",tra_rev.get(getAdapterPosition()));
               if(!(tra_rev.get(getAdapterPosition()).equals("2"))) {
                   minterface.onclick(tra_rev.get(getAdapterPosition()));
               }
           }
        }
    }


}

