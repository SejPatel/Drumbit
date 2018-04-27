package com.example.devyani.drumbeatapplication.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by devyani on 18/4/18.
 */

public class CreateOwnPost extends AppCompatActivity implements LocationListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    TextView lattitude, longitude;
    EditText mypostdescription, radious;
    Button createpost;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createownpost);
        lattitude = (TextView) findViewById(R.id.txt_lattitude);
        longitude = (TextView) findViewById(R.id.txt_longitude);
        mypostdescription = (EditText) findViewById(R.id.edt_description_mypost);
        radious = (EditText) findViewById(R.id.edt_mypost_radious);
        createpost = (Button) findViewById(R.id.btn_create_post);
        requestPermission();
        /* Use the LocationManager class to obtain GPS locations */
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double Lattitude = location.getLatitude();
        double Longitude = location.getLongitude();
        lattitude.setText(String.format("%.2f", Lattitude));
        longitude.setText(String.format("%.2f", Longitude));

        createpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    createpost();
                }
            }
        });
    }

    private void createpost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<BaseResponse> call = api.createpostapi(SharedPrefManager.getInstance(CreateOwnPost.this).getIntValue(SharedPrefManager.USER_ID),
                SharedPrefManager.getInstance(CreateOwnPost.this).getStringValue(SharedPrefManager.ACCESS_TOKEN),
                mypostdescription.getText().toString(), lattitude.getText().toString(),
                longitude.getText().toString(), Integer.parseInt(radious.getText().toString()), "Surat the best");

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse createpostsecond = response.body();
                if (createpostsecond.status == 1) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(CreateOwnPost.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validate() {
        if (lattitude.getText().toString().isEmpty()) {
            lattitude.setError("lattitude can not be blank");
            return false;
        } else if (longitude.getText().toString().isEmpty()) {
            longitude.setError("longitude can not be blank");
            return false;
        } else if (mypostdescription.getText().toString().isEmpty()) {
            mypostdescription.setError("post description can not be blanck");
            return false;
        } else if (radious.getText().toString().isEmpty()) {
            radious.setError("radious can not bi blanck");
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{INTERNET, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cource = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (location && cource) {
                        Toast.makeText(getApplication(), "permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication(), "permission not granted", Toast.LENGTH_LONG).show();

                        Toast.makeText(getApplication(), "Permission Denied, You cannot access location data and camera.", Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_EXTERNAL_STORAGE, CAMERA, WRITE_EXTERNAL_STORAGE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
