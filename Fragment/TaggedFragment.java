package com.example.devyani.drumbeatapplication.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devyani.drumbeatapplication.Adapter.TaggedAdapter;
import com.example.devyani.drumbeatapplication.Activity.DuplicateRecyclerView;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.listner.OnItemClickListner;
import com.example.devyani.drumbeatapplication.utils.MyDividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by devyani on 31/3/18.
 */

public class TaggedFragment extends Fragment {

    ArrayList personImages = new ArrayList<>();
    ArrayList<String> onlinename = new ArrayList<String>();
    ArrayList hours = new ArrayList();
    ImageView img_search;
    TextView txt_drumbeat;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tagglerecycler,container,false);
        RecyclerView r1;
        r1 = (RecyclerView) rootview.findViewById(R.id.tag_recycler);
        txt_drumbeat = (TextView)rootview.findViewById(R.id.txt_drumbeat);
        img_search = (ImageView)rootview.findViewById(R.id.action_searchh);
      /*  int color = Color.parseColor("#FFFFFF"); //The color u want
        img_search.setColorFilter(color);
*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        r1.setLayoutManager(linearLayoutManager);

        for(int i=0;i<10;i++)
        {
            personImages.add(R.drawable.userprofile);
        }
        for(int i=0;i<10;i++)
        {
            onlinename.add("Devyani Nikumbh");
        }
        for(int i=0;i<10;i++)
        {
            hours.add("2 New Post");
        }

        TaggedAdapter taggedAdapter = new TaggedAdapter(getContext(), personImages, onlinename, hours);
        r1.addItemDecoration(new MyDividerItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.dp_8), true));
        r1.setAdapter(taggedAdapter);

        taggedAdapter.setListner(new OnItemClickListner() {
            @Override
            public void onItemClick(int pos) {

            }

            @Override
            public void onItemClick(int pos, int type) {
                        if(type==1)
                        {
                            String name=onlinename.get(pos);
                            Intent intent = new Intent(getActivity(), DuplicateRecyclerView.class);
                            intent.putExtra("username",name);
                            startActivity(intent);
                        }


            }

        });
        return  rootview;
    }
}
