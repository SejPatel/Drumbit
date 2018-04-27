package com.example.devyani.drumbeatapplication.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
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
 * Created by devyani on 31/3/18.
 */

public class editprofile extends AppCompatActivity {


    TextView txt_edit_profile , first,last,email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        TextView txt_drumbeat = (TextView) findViewById(R.id.txt_drumbeat);
        txt_drumbeat.setText("Edit Profile");
        ImageView finish = (ImageView) findViewById(R.id.img_backpressed);
        txt_edit_profile = (TextView)findViewById(R.id.edit_profile);
        first = (TextView)findViewById(R.id.edt_editprofile_fname);
        last = (TextView)findViewById(R.id.edt_editprofile_lname);
        email = (TextView)findViewById(R.id.edt_editprofile_email);

        int color = Color.parseColor("#FFFFFF"); //The color u want
        finish.setColorFilter(color);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        first.setText(SharedPrefManager.getInstance(editprofile.this).getStringValue(SharedPrefManager.First_Name));
        last.setText(SharedPrefManager.getInstance(editprofile.this).getStringValue(SharedPrefManager.Last_Name));
        email.setText(SharedPrefManager.getInstance(editprofile.this).getStringValue(SharedPrefManager.Email));

        txt_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(editprofile.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setTitle("Edit Profile"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                getEditApi();
            }
        });

    }

    private void getEditApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<BaseResponse> call = api.getEditApi(SharedPrefManager.getInstance(editprofile.this).getIntValue(SharedPrefManager.USER_ID),
                SharedPrefManager.getInstance(editprofile.this).getStringValue(SharedPrefManager.ACCESS_TOKEN),first.getText().toString(),
                last.getText().toString(),email.getText().toString());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse example = response.body();
                if(example.status == 1)
                {
                    SharedPrefManager.getInstance(editprofile.this)
                            .setStringData(SharedPrefManager.First_Name,first.getText().toString());
                    SharedPrefManager.getInstance(editprofile.this)
                            .setStringData(SharedPrefManager.Last_Name,last.getText().toString());
                    SharedPrefManager.getInstance(editprofile.this)
                            .setStringData(SharedPrefManager.Email,email.getText().toString());


                    Toast.makeText(editprofile.this,"Profile updated Successfully",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(editprofile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
