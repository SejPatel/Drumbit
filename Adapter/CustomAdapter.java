package com.example.devyani.drumbeatapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devyani.drumbeatapplication.Fragment.User_Profile;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.listner.OnItemClickListner;
import com.example.devyani.drumbeatapplication.model.UserGroup.UserData;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devyani on 31/3/18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    List<UserData> alluserpostdata ;
    //declare interface
    private Context context;
    private OnItemClickListner onClick;


    public CustomAdapter(Context context,List<UserData> alluserpostdata) {
            this.context = context;
            this.alluserpostdata = alluserpostdata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.userprofile, parent, false);

        //  set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);   //pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final UserData userData = alluserpostdata.get(position);
        holder.mypostusername.setText(userData.user.first_name +" " + userData.user.last_name);
        holder.mypostuserview.setText(String.valueOf(userData.total_view));
        holder.myposttime.setText(userData.created +"");
        holder.mypostdescription.setText(userData.content);
        holder.rel_main_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position,1);
            }
        });

    }



    @Override
    public int getItemCount() {
        return alluserpostdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel_main_click;
        TextView mypostusername,mypostuserview,myposttime,mypostdescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            rel_main_click=(RelativeLayout)itemView.findViewById(R.id.rel_main_click);
            mypostusername=(TextView)itemView.findViewById(R.id.txt_namesurname);
            mypostuserview=(TextView)itemView.findViewById(R.id.txt_h);
            myposttime=(TextView)itemView.findViewById(R.id.txt_view);
            mypostdescription=(TextView)itemView.findViewById(R.id.txt_description);
        }
    }
    public void setListner(OnItemClickListner listner){

        onClick = listner;
    }
}
