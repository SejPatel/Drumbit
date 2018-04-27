package com.example.devyani.drumbeatapplication.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.devyani.drumbeatapplication.Adapter.CustomAdapter;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.utils.MyDividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by devyani on 3/4/18.
 */

public class DuplicateRecyclerView extends AppCompatActivity {

    ImageView finish;
    ArrayList personImages = new ArrayList<>();
    ArrayList namesurname = new ArrayList();
    ArrayList view = new ArrayList();
    ArrayList h = new ArrayList();
    ArrayList description = new ArrayList();
    ArrayList image1 = new ArrayList();
    ArrayList image2 = new ArrayList();
    ArrayList image3 = new ArrayList();
    TextView title;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newduplicaterecycler);

        RecyclerView r1;

        r1 = (RecyclerView)findViewById(R.id.duplicate_recycler_profile);
        finish=(ImageView)findViewById(R.id.img_backpressed);
        title=(TextView)findViewById(R.id.txt_drumbeat);
        toolbar=(Toolbar)findViewById(R.id.tool_second);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        int color = Color.parseColor("#FFFFFF"); //The color u want
        finish.setColorFilter(color);

        Intent intent = getIntent();
        String username=intent.getStringExtra("username");
        title.setText(username);
       /* img_search = (ImageView)findViewById(R.id.action_searchh);
        color = Color.parseColor("#FFFFFF");
        img_search.setColorFilter(color);*/
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        r1.setLayoutManager(linearLayoutManager);



        for (int i = 0; i < 10; i++) {
            personImages.add(R.drawable.userprofile);
        }
        for (int i = 0; i < 10; i++) {
            namesurname.add("Devyani Nikumbh");
        }
        for (int i = 0; i < 10; i++) {
            view.add("412  views");
        }
        for (int i = 0; i < 10; i++) {
            h.add("3h");
        }
        for (int i = 0; i < 10; i++) {
            description.add("  Lorem Ipsum is simply dummy text of the printing and typesetting industry.of the printing and typesetting");
        }
        for (int i = 0; i < 10; i++) {
            image1.add(R.drawable.oneimage);
        }
        for (int i = 0; i < 10; i++) {
            image2.add(R.drawable.oneimage);
        }
        for (int i = 0; i < 10; i++) {
            image3.add(R.drawable.oneimage);
        }
       // CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), personImages, namesurname, view, h, description, image1, image2, image3);
        r1.addItemDecoration(new MyDividerItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.dp_8), true));

        //r1.setAdapter(customAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }
}
