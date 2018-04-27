package com.example.devyani.drumbeatapplication.Activity;

import android.app.ProgressDialog;
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

import com.example.devyani.drumbeatapplication.Adapter.boardrecyclersAdapter;
import com.example.devyani.drumbeatapplication.Interfaces.Api;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.model.BaseResponse;
import com.example.devyani.drumbeatapplication.model.GroupDetail.GroupDetailResponse;
import com.example.devyani.drumbeatapplication.model.UserGroup.User;
import com.example.devyani.drumbeatapplication.model.UserGroup.UserData;
import com.example.devyani.drumbeatapplication.model.personImages;
import com.example.devyani.drumbeatapplication.utils.MyDividerItemDecoration;
import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.devyani.drumbeatapplication.utils.SharedPrefManager.*;

/**
 * Created by devyani on 3/4/18.
 */

public class boardclickeventrecycler extends AppCompatActivity {

    ArrayList<personImages> list;
    TextView name_toolbar, toolbar_reviews, toolbar_hour, txt_dummy;
    Toolbar toolbar;
    ImageView backpressed, follow;
    ProgressDialog progressDialog;
    public UserData userData;
    public User user;

    private GroupDetailResponse groupDetailResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardclickeventrecycler);

        RecyclerView r1;
        r1 = (RecyclerView) findViewById(R.id.recycler_board_click);
        name_toolbar = (TextView) findViewById(R.id.name_toolbar);
        toolbar_reviews = (TextView) findViewById(R.id.toolbar_reviews);
        toolbar_hour = (TextView) findViewById(R.id.toolbar_hour);
        txt_dummy = (TextView) findViewById(R.id.txt_dummy);
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

        userData = getIntent().getParcelableExtra("userData");

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

        boardrecyclersAdapter board = new boardrecyclersAdapter(getApplicationContext(), list);
        //       r1.addItemDecoration(new MyDividerItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.dp_8), true));
        //r1.addItemDecoration(new MyDividerItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.dp_8), true));

        r1.setAdapter(board);
        progressDialog = new ProgressDialog(boardclickeventrecycler.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Change Password"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        alldetailsapi();

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followunfollow();
            }
        });

    }

    private void alldetailsapi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<GroupDetailResponse> call = api.alldetailsapi(getInstance(boardclickeventrecycler.this).getIntValue(USER_ID),
                getInstance(boardclickeventrecycler.this).getStringValue(ACCESS_TOKEN)
                , userData.group_id);
        call.enqueue(new Callback<GroupDetailResponse>() {
            @Override
            public void onResponse(Call<GroupDetailResponse> call, Response<GroupDetailResponse> response) {
                groupDetailResponse = response.body();
                if (groupDetailResponse.status == 1) {

                    progressDialog.dismiss();

                    if (groupDetailResponse.data.user.user_id == (getInstance(boardclickeventrecycler.this).getIntValue(USER_ID))) {
                        follow.setVisibility(View.GONE);
                    } else {
                        follow.setVisibility(View.VISIBLE);
                    }

                    if (groupDetailResponse.data.is_following == 1) {
                        follow.setImageResource(R.drawable.likefild);
                    } else {
                        follow.setImageResource(R.drawable.like);
                    }

                    name_toolbar.setText(userData.user.first_name);
                    toolbar_reviews.setText(String.valueOf(userData.created));
                    toolbar_hour.setText(String.valueOf(userData.total_view));
                    txt_dummy.setText(userData.content);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GroupDetailResponse> call, Throwable t) {

            }
        });
    }


    private void followunfollow() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<BaseResponse> call = api.followunfollow(SharedPrefManager.getInstance(boardclickeventrecycler.this).getIntValue(SharedPrefManager.USER_ID),
                SharedPrefManager.getInstance(boardclickeventrecycler.this).getStringValue(SharedPrefManager.ACCESS_TOKEN),
                groupDetailResponse.data.user.user_id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().status == 1) {
                    if (groupDetailResponse.data.is_following == 1) {
                        groupDetailResponse.data.is_following = 0;
                        // Unselected
                        follow.setImageResource(R.drawable.like);
                    } else {
                        groupDetailResponse.data.is_following = 1;
                        //selected
                        follow.setImageResource(R.drawable.likefild);
                    }
                }

            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

}
