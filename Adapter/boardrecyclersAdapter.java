package com.example.devyani.drumbeatapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.model.personImages;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devyani on 3/4/18.
 */

public class boardrecyclersAdapter extends RecyclerView.Adapter<boardrecyclersAdapter.MyViewHolder> {
    private List<personImages> personList;
    private personImages personImages;
    Context context;

    public boardrecyclersAdapter(Context context, List<personImages> personList) {
        this.personList = personList;
        this.context = context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.boardclickeventcontent, parent, false);

        //  set the view's size, margins, paddings and layout parameters
        //pass the view to View Holder
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(boardrecyclersAdapter.MyViewHolder holder, int position) {
        personImages = personList.get(position);
        holder.board.setImageResource((personImages.getImage()));

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView board;

        public MyViewHolder(View itemView) {
            super(itemView);
            board = (ImageView) itemView.findViewById(R.id.img_board);
        }
    }
}
