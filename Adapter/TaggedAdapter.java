package com.example.devyani.drumbeatapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.listner.OnItemClickListner;

import java.util.ArrayList;

/**
 * Created by devyani on 2/4/18.
 */

public class TaggedAdapter extends RecyclerView.Adapter<TaggedAdapter.MyViewHolder> {

    ArrayList personImages,onlinename,hours;
    Context context;

    //declare interface
    private OnItemClickListner onClick;

    public TaggedAdapter(Context context, ArrayList personImages, ArrayList onlinename, ArrayList hours) {
        this.context = context;
        this.personImages = personImages;
        this.onlinename = onlinename;
        this.hours = hours;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.taggedfragmentcontent, parent, false);

        //  set the view's size, margins, paddings and layout parameters
       MyViewHolder vh = new MyViewHolder(v);
       return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.profile_image.setImageResource(Integer.parseInt(personImages.get(position).toString()));
        holder.name.setText(onlinename.get(position).toString());
        holder.post.setText(hours.get(position).toString());
        holder.rel_tagged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position,1);
            }
        });

    }


    @Override
    public int getItemCount() {
        return personImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_image;
        TextView name,post;
        RelativeLayout rel_tagged;
        public MyViewHolder(View itemView) {
            super(itemView);
            profile_image=(ImageView)itemView.findViewById(R.id.img_taggeed);
            name=(TextView)itemView.findViewById(R.id.txt_nameonline);
            post=(TextView)itemView.findViewById(R.id.txt_post);
            rel_tagged=(RelativeLayout)itemView.findViewById(R.id.rel_tagged);
        }
    }
    public void setListner(OnItemClickListner listner){

        onClick = listner;
    }
}
