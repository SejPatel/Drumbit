package com.example.devyani.drumbeatapplication.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    Handler myHandler;
    Runnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
    }

    public void initVariable() {
        myHandler = new Handler();

        myHandler.postDelayed(myRunnable = new Runnable() {
            @Override
            public void run() {
                if (SharedPrefManager.getInstance(MainActivity.this).getBooleanValue(SharedPrefManager.islogin)) {
                    Intent intent = new Intent(MainActivity.this, tabViewpager.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacks(myRunnable);
        }
    }

}
// && SharedPrefManager.getInstance(MainActivity.this) != null
