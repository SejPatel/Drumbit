package com.example.devyani.drumbeatapplication.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.devyani.drumbeatapplication.Adapter.TabsAdapter;
import com.example.devyani.drumbeatapplication.Fragment.MyPostFragment;
import com.example.devyani.drumbeatapplication.Fragment.TaggedFragment;
import com.example.devyani.drumbeatapplication.Fragment.User_Profile;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by devyani on 31/3/18.
 */

public class tabViewpager extends AppCompatActivity implements LocationListener {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    Toolbar toolbar;
    ImageView back, search;
    private static final int PERMISSION_REQUEST_CODE = 200;

    LocationManager lm;
    private Context context;
    User_Profile user_profile = new User_Profile();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout);

        mViewPager = (ViewPager) findViewById(R.id.view_profile);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.tool_second);
        back = (ImageView) findViewById(R.id.img_backpressed);
        search = (ImageView) findViewById(R.id.action_searchh);

        back.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);

         /* Use the LocationManager class to obtain GPS locations */
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermission();
        } else {

//            Bundle bundle = new Bundle();
//            bundle.putFloat("Lattitude",Lattitude);
//            bundle.putFloat("Longitude",Longitude);
//            User_Profile myObj = new User_Profile();
//            myObj.setArguments(bundle);

            if (ActivityCompat.checkSelfPermission(tabViewpager.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(tabViewpager.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            SharedPrefManager.getInstance(context).setStringData(SharedPrefManager.lat, "" +loc.getLatitude());
            SharedPrefManager.getInstance(context).setStringData(SharedPrefManager.lng, "" +loc.getLongitude());

            callAPI();


        }
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
                        Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        SharedPrefManager.getInstance(context).setFloatData(SharedPrefManager.lat, (float) loc.getLatitude());
                        SharedPrefManager.getInstance(context).setFloatData(SharedPrefManager.lng, (float) loc.getLongitude());

                        callAPI();
                    } else {
                        Toast.makeText(getApplication(), "permission not granted", Toast.LENGTH_LONG).show();

//                        Toast.makeText(getApplication(), "Permission Denied, You cannot access location data and camera.", Toast.LENGTH_LONG).show();

//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(CAMERA)) {
//                                showMessageOKCancel("You need to allow access to both the permissions",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{READ_EXTERNAL_STORAGE, CAMERA, WRITE_EXTERNAL_STORAGE},
//                                                            PERMISSION_REQUEST_CODE);
//                                                    /*Intent intent = new Intent(tabViewpager.this);
//                                                    startActivity(intent);
// */
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }

                    }
                }
                break;
        }
    }

    private void callAPI() {
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
        user_profile.createboardapi();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings: {
                Intent intent = new Intent(tabViewpager.this, Settings.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupViewPager(ViewPager viewPager) {

        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new User_Profile(), "Board");
        adapter.addFragment(new TaggedFragment(), "Tagged");
        adapter.addFragment(new MyPostFragment(), "My Post");
        viewPager.setAdapter(adapter);

     /*   TabsAdapter adapter = new TabsAdapter(getFragmentManager());
        adapter.addFragment(new , "WALLETS");
        adapter.addFragment(new RewardFragment(), "REWARD");
        adapter.addFragment(new Fragment_Statement(), "STATEMENT");
        adapter.addFragment(new Gift_Card_Fragment(), "GIFTCARD");
        adapter.addFragment(new Payment_fragment(), "PAYMENT");
        viewPager.setAdapter(adapter);*/
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
