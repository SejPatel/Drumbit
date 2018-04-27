package com.example.devyani.drumbeatapplication.Activity;


import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devyani.drumbeatapplication.Adapter.MyPostRecyclerAdapter;

import com.example.devyani.drumbeatapplication.Interfaces.Api;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.model.GroupDetail.GroupDetailResponse;

import com.example.devyani.drumbeatapplication.model.UserGroup.UserData;

import com.example.devyani.drumbeatapplication.model.personImages;
import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.devyani.drumbeatapplication.utils.SharedPrefManager.USER_ID;
import static com.example.devyani.drumbeatapplication.utils.SharedPrefManager.getInstance;

/**
 * Created by devyani on 19/4/18.
 */

public class MyPostEventClickRecycler extends AppCompatActivity {

    ArrayList<personImages> list;
    TextView name_toolbar, toolbar_reviews, toolbar_hour, description,txt_dummy;
    Toolbar toolbar;
    ImageView backpressed ,follow;

    RecyclerView r1;

    private UserData userData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardclickeventrecycler);
        r1 = (RecyclerView) findViewById(R.id.recycler_board_click);
        name_toolbar = (TextView) findViewById(R.id.name_toolbar);
        txt_dummy = (TextView) findViewById(R.id.txt_dummy);
        toolbar_reviews = (TextView) findViewById(R.id.toolbar_reviews);
        toolbar_hour = (TextView) findViewById(R.id.toolbar_hour);
        description = (TextView) findViewById(R.id.txt_dummy);
        backpressed = (ImageView) findViewById(R.id.img_backpressed_other);
        toolbar = (Toolbar) findViewById(R.id.new_toolbar);
        follow = (ImageView) findViewById(R.id.cakes);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        int color = Color.parseColor("#FFFFFF"); //The color u want
        backpressed.setColorFilter(color);

        backpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userData = getIntent().getParcelableExtra("userDataMyPost");

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                followunfollow();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
//        r1.addItemDecoration(new MyDividerItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.dp_16), true));
        r1.setLayoutManager(linearLayoutManager);


        list = new ArrayList<>();
        list.add(new personImages(R.drawable.oneimage));
        list.add(new personImages(R.drawable.oneimage));
        list.add(new personImages(R.drawable.oneimage));
        list.add(new personImages(R.drawable.oneimage));
        list.add(new personImages(R.drawable.oneimage));
        list.add(new personImages(R.drawable.oneimage));
        list.add(new personImages(R.drawable.oneimage));
        list.add(new personImages(R.drawable.oneimage));
        list.add(new personImages(R.drawable.oneimage));
        MyPostRecyclerAdapter board = new MyPostRecyclerAdapter(getBaseContext(), list);
//        r1.addItemDecoration(new MyDividerItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.dp_8), true));
        //r1.addItemDecoration(new MyDividerItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.dp_8), true));

        r1.setAdapter(board);
        alldetailsapi();

    }

    private void alldetailsapi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<GroupDetailResponse> call = api.alldetailsapi(SharedPrefManager.getInstance(MyPostEventClickRecycler.this).getIntValue(SharedPrefManager.USER_ID),
                SharedPrefManager.getInstance(MyPostEventClickRecycler.this).getStringValue(SharedPrefManager.ACCESS_TOKEN)
                , userData.group_id);
        call.enqueue(new Callback<GroupDetailResponse>() {
            @Override
            public void onResponse(Call<GroupDetailResponse> call, Response<GroupDetailResponse> response) {
                GroupDetailResponse groupDetailResponse = response.body();
                if (groupDetailResponse.status == 1) {
                    name_toolbar.setText(userData.user.first_name);
                    toolbar_reviews.setText(String.valueOf(userData.created));
                    toolbar_hour.setText(String.valueOf(userData.total_view));
                    txt_dummy.setText(userData.content);
                }
            }

            @Override
            public void onFailure(Call<GroupDetailResponse> call, Throwable t) {

            }
        });
    }

/*    private void followunfollow() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<GroupDetailResponse> call = api.followunfollow(SharedPrefManager.getInstance(MyPostEventClickRecycler.this).getIntValue(SharedPrefManager.USER_ID),
                SharedPrefManager.getInstance(MyPostEventClickRecycler.this).getStringValue(SharedPrefManager.ACCESS_TOKEN),
                userData.user.user_id);
        call.enqueue(new Callback<GroupDetailResponse>() {
            @Override
            public void onResponse(Call<GroupDetailResponse> call, Response<GroupDetailResponse> response) {
                GroupDetailResponse groupDetailResponse = response.body();
                if (groupDetailResponse.status == 1) {
                  *//*  if((String.valueOf(userData.user.user_id)).equalsIgnoreCase(getInstance(MyPostEventClickRecycler.this).getStringValue(USER_ID)))
                    {
                        follow.setVisibility(View.GONE);
                    }
                    else
                    {
                        follow.setVisibility(View.VISIBLE);
                        follow.setImageResource(R.drawable.likefild);
                    }*//*
                }
            }

            @Override
            public void onFailure(Call<GroupDetailResponse> call, Throwable t) {

            }
        });
    }*/
}
