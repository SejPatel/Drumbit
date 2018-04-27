package com.example.devyani.drumbeatapplication.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devyani.drumbeatapplication.Interfaces.Api;
import com.example.devyani.drumbeatapplication.R;

import com.example.devyani.drumbeatapplication.model.UserGroup.UserGroupResponse;
import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devyani on 30/3/18.
 */

public class login extends AppCompatActivity {

    TextInputLayout input_email, input_password;
    EditText edt_email, edt_pass;
    TextView txt_signup, txt_forget, registration;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        input_email = (TextInputLayout) findViewById(R.id.input_email);
        input_password = (TextInputLayout) findViewById(R.id.input_password);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_pass = (EditText) findViewById(R.id.edt_password);
        txt_signup = (TextView) findViewById(R.id.txt_signup);
        txt_forget = (TextView) findViewById(R.id.txt_forget);
        registration = (TextView) findViewById(R.id.txt_signin);


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

        txt_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(login.this);
                dialog.setContentView(R.layout.forgetpassword);
                final EditText email = (EditText) dialog.findViewById(R.id.forgot_email);
                TextView submit = (TextView) dialog.findViewById(R.id.forgot_submit);
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = validateEmail(email.getText().toString().trim());
                        if (!TextUtils.isEmpty(str)) {
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), "Thank you", Toast.LENGTH_LONG).show();

                        email.setHint("type your email address here...");


//                        if (!(email.getText().toString().trim().length() > 0)) {
//                            Toast.makeText(getApplicationContext(), "Please fill this", Toast.LENGTH_LONG).show();
//                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
//                            Toast.makeText(getApplicationContext(), "Enter valid email Address", Toast.LENGTH_LONG).show();
//                        }

                    }
                });
            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(login.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setTitle("Logging please wait"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                String str = validateEmail(edt_email.getText().toString().trim());
                String pass = validatePassword(edt_pass.getText().toString().trim());
                if (!TextUtils.isEmpty(str)) {
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                    return;
                } else if (!(TextUtils.isEmpty(pass))) {
                    Toast.makeText(getApplicationContext(), pass, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    getLoginApi();
                }

            }

        });
    }

    private void getLoginApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<UserGroupResponse> call = api.getLoginApi(edt_email.getText().toString(),
                edt_pass.getText().toString(), "android", "asdgfhgshdgfhgsfyasgfyg");

        call.enqueue(new Callback<UserGroupResponse>() {
            @Override
            public void onResponse(Call<UserGroupResponse> call, Response<UserGroupResponse> response) {
                UserGroupResponse example = response.body();
                if (example.getStatus() == 1) {
                    SharedPrefManager.getInstance(login.this)
                            .setStringData(SharedPrefManager.ACCESS_TOKEN,example.data.access_token);
                    SharedPrefManager.getInstance(login.this)
                            .setIntegerData(SharedPrefManager.USER_ID, example.data.user.user_id);
                    SharedPrefManager.getInstance(login.this)
                            .setStringData(SharedPrefManager.First_Name, example.data.user.first_name);
                    SharedPrefManager.getInstance(login.this)
                            .setStringData(SharedPrefManager.Last_Name, example.data.user.last_name);
                    SharedPrefManager.getInstance(login.this)
                            .setStringData(SharedPrefManager.Email, example.data.user.email);
                    SharedPrefManager.getInstance(login.this)
                            .setStringData(SharedPrefManager.photo, example.data.user.profile_image);
                    SharedPrefManager.getInstance(login.this)
                            .setStringData(SharedPrefManager.profilethumb, example.data.user.profile_image_thumb);
                    SharedPrefManager.getInstance(login.this)
                            .setBooleanData(SharedPrefManager.islogin, true);
                    progressDialog.dismiss();
                    Intent intent = new Intent(login.this, tabViewpager.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(login.this, example.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<UserGroupResponse> call, Throwable t) {
                Toast.makeText(login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static String validateEmail(String email) {
        if (!(email.length() > 0)) {
            return "Please enter email";
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Enter valid email Address";
        }
        return "";
    }

    public static String validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return "please enter password.";
        } else if ((password.length() < 6)) {
            return "password length must be greater than 6";
        }
        return "";
    }

   /* private boolean validate(EditText edt_email, TextInputLayout input_email) {
        if (!(edt_email.getText().toString().trim().length() > 0)) {
            edt_email.requestFocus();
            input_email.setError("please Fill this");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches()) {
            edt_email.requestFocus();
            input_email.setError("Please enter valid email address");
            return false;
        } else {
            return true;
        }

    }*/

  /*  private boolean validatepassword(EditText edt_pass, TextInputLayout input_password) {
        if (!(edt_pass.getText().toString().trim().length() > 0)) {
            edt_pass.requestFocus();
            input_password.setError("please fill this");
            return false;
        } else if (!(edt_pass.getText().toString().trim().length() > 8)) {
            edt_pass.requestFocus();
            input_password.setError("Password length should not be less than 8");
            return false;
        } else {
            return true;
        }

    }*/

}
