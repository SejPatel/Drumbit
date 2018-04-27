package com.example.devyani.drumbeatapplication.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

public class ChangePassword extends AppCompatActivity {

    TextView change_password;
    EditText currentpassword, newpassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        TextView txt_drumbeat = (TextView) findViewById(R.id.txt_drumbeat);
        txt_drumbeat.setText("Change Password");
        ImageView finish = (ImageView) findViewById(R.id.img_backpressed);
        currentpassword = (EditText) findViewById(R.id.edt_current_password);
        newpassword = (EditText) findViewById(R.id.edt_new_password);
        change_password = (TextView) findViewById(R.id.txt_change_password);
        int color = Color.parseColor("#FFFFFF"); //The color u want
        finish.setColorFilter(color);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ChangePassword.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setTitle("Change Password"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                changePassword();
            }
        });
    }

    private void changePassword() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<BaseResponse> call = api.changePassword(SharedPrefManager.getInstance(ChangePassword.this).getIntValue(SharedPrefManager.USER_ID),
                SharedPrefManager.getInstance(ChangePassword.this).getStringValue(SharedPrefManager.ACCESS_TOKEN), currentpassword.getText().toString(),
                newpassword.getText().toString());

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse example = response.body();
                if (example.status == 1) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(ChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
