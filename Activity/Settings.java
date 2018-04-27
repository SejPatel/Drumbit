package com.example.devyani.drumbeatapplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devyani.drumbeatapplication.Interfaces.Api;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.model.BaseResponse;

import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devyani on 30/3/18.
 */

public class Settings extends AppCompatActivity {

    TextView txt_editprofile, txt_password, txt_terms, txt_privacy, txt_aboutus, txt_drumbeat, txt_logout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        txt_editprofile = (TextView) findViewById(R.id.txt_editprofile);
        txt_password = (TextView) findViewById(R.id.txt_password);
        txt_terms = (TextView) findViewById(R.id.txt_terms);
        txt_privacy = (TextView) findViewById(R.id.txt_privacy);
        txt_aboutus = (TextView) findViewById(R.id.txt_aboutus);
        txt_drumbeat = (TextView) findViewById(R.id.txt_drumbeat);
        txt_logout = (TextView) findViewById(R.id.txt_logout);


        txt_drumbeat.setText("Settings");

        final ImageView finish = (ImageView) findViewById(R.id.img_backpressed);
        int color = Color.parseColor("#FFFFFF"); //The color u want
        finish.setColorFilter(color);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Settings.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setTitle("Logging Out"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                getLogoutApi();

            }
        });

        txt_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, AboutUs.class);
                startActivity(intent);
            }
        });

        txt_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, PrivacyPolicy.class);
                startActivity(intent);
            }
        });

        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, TermsOConditionActivity.class);
                startActivity(intent);
            }
        });

        txt_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        txt_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, editprofile.class);
                startActivity(intent);
            }
        });
    }

    private void getLogoutApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<BaseResponse> call = api.getLogoutApi(
                SharedPrefManager.getInstance(Settings.this).getIntValue(SharedPrefManager.USER_ID),
                SharedPrefManager.getInstance(Settings.this).getStringValue(SharedPrefManager.ACCESS_TOKEN));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse example = response.body();
                if(example.status == 1)
                {
                    SharedPrefManager.getInstance(Settings.this).editor.clear();
                    SharedPrefManager.getInstance(Settings.this).editor.commit();
                    SharedPrefManager.getInstance(Settings.this).clearSharedPref();
                    Intent intent = new Intent(Settings.this, login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(Settings.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
