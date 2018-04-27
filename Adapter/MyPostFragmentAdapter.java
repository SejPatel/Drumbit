package com.example.devyani.drumbeatapplication.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devyani.drumbeatapplication.Fragment.MyPostFragment;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.listner.OnItemClickListner;
import com.example.devyani.drumbeatapplication.model.UserGroup.UserData;
import java.util.List;

/**
 * Created by devyani on 18/4/18.
 */

public class MyPostFragmentAdapter extends RecyclerView.Adapter<MyPostFragmentAdapter.MyViewHolder> {

    List<UserData> postdata;
    private MyPostFragment context;
    private OnItemClickListner onClick;

    public MyPostFragmentAdapter(MyPostFragment myPostFragment, List<UserData> recyclerdata) {
        this.context = myPostFragment;
        this.postdata = recyclerdata;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypostrecyclercontent, parent, false);

        //  set the view's size, margins, paddings and layout parameters
        MyPostFragmentAdapter.MyViewHolder vh = new MyPostFragmentAdapter.MyViewHolder(v);   //pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyPostFragmentAdapter.MyViewHolder holder, final int position) {
        final UserData userData = postdata.get(position);
        holder.mypostusername.setText(userData.user.first_name +" " + userData.user.last_name);
        holder.mypostuserview.setText(String.valueOf(userData.total_view));
        holder.myposttime.setText(userData.created +"");
        holder.mypostdescription.setText(userData.content);
        holder.rel_mypost_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  User user = new User()
                onClick.onItemClick(position,1);
            }
        });
    }



    @Override
    public int getItemCount() {
        return postdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mypostusername,mypostuserview,myposttime,mypostdescription;
        RelativeLayout rel_mypost_click;
        public MyViewHolder(View itemView) {
            super(itemView);
            mypostusername=(TextView)itemView.findViewById(R.id.txt_mypost_username);
            mypostuserview=(TextView)itemView.findViewById(R.id.txt_mypost_nofoviews);
            myposttime=(TextView)itemView.findViewById(R.id.txt_mypost_time);
            mypostdescription=(TextView)itemView.findViewById(R.id.txt_mypost_description);
            rel_mypost_click=(RelativeLayout)itemView.findViewById(R.id.rel_mypost_click);
        }
    }
    public void setListner(OnItemClickListner listner){

        onClick = listner;
    }
}
