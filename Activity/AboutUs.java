package com.example.devyani.drumbeatapplication.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devyani.drumbeatapplication.R;

/**
 * Created by devyani on 31/3/18.
 */

public class AboutUs extends AppCompatActivity {

    TextView txt_about;
    ImageView finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        txt_about=(TextView)findViewById(R.id.txt_about);
        txt_about.setMovementMethod(new ScrollingMovementMethod());

        TextView txt_drumbeat = (TextView) findViewById(R.id.txt_drumbeat);
        txt_drumbeat.setText("About Us");
        finish=(ImageView)findViewById(R.id.img_backpressed);
        int color = Color.parseColor("#FFFFFF"); //The color u want
        finish.setColorFilter(color);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
