package com.example.devyani.drumbeatapplication.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devyani.drumbeatapplication.Interfaces.Api;
import com.example.devyani.drumbeatapplication.R;

import com.example.devyani.drumbeatapplication.model.UserGroup.UserGroupResponse;
import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by devyani on 30/3/18.
 */

public class signup extends AppCompatActivity {

    EditText first, last, email, password, confirmpassword;
    TextView title;
    ImageView back, photo;
    private static final int PERMISSION_REQUEST_CODE = 200;
    TextView signup;
    File f;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        first = (EditText) findViewById(R.id.edt_first_name);
        last = (EditText) findViewById(R.id.edt_last_name);
        email = (EditText) findViewById(R.id.edt_emaill);
        password = (EditText) findViewById(R.id.edt_passwordd);
        confirmpassword = (EditText) findViewById(R.id.edt_confirm_password);
        signup = (TextView) findViewById(R.id.txt_register);
        back = (ImageView) findViewById(R.id.img_backpressed);
        title = (TextView) findViewById(R.id.txt_drumbeat);
        photo = (ImageView) findViewById(R.id.img_pick_profile);
        title.setText("Sign Up");

        ImageView finish = (ImageView) findViewById(R.id.img_backpressed);

        int color = Color.parseColor("#FFFFFF"); //The color u want
        finish.setColorFilter(color);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())

                getRegistration();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }

    private void getRegistration() {
        progressDialog = new ProgressDialog(signup.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Logging please wait"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Api api = retrofit.create(Api.class);
        Call<UserGroupResponse> call = api.getRegistration(first.getText().toString(), last.getText().toString(),
                email.getText().toString(), password.getText().toString(), "android", "abcdefghijklmnopqrstuvwxyz");

        call.enqueue(new Callback<UserGroupResponse>() {
            @Override
            public void onResponse(Call<UserGroupResponse> call, Response<UserGroupResponse> response) {
                UserGroupResponse example = response.body();
                if (example.getStatus() == 1) {
                    progressDialog.dismiss();
                    SharedPrefManager.getInstance(signup.this)
                            .setStringData(SharedPrefManager.ACCESS_TOKEN, example.data.access_token);
                    SharedPrefManager.getInstance(signup.this)
                            .setIntegerData(SharedPrefManager.USER_ID, example.data.user.user_id);
                    SharedPrefManager.getInstance(signup.this)
                            .setStringData(SharedPrefManager.First_Name, example.data.user.first_name);
                    SharedPrefManager.getInstance(signup.this)
                            .setStringData(SharedPrefManager.Last_Name, example.data.user.last_name);
                    SharedPrefManager.getInstance(signup.this)
                            .setStringData(SharedPrefManager.Email, example.data.user.email);
                    SharedPrefManager.getInstance(signup.this)
                            .setStringData(SharedPrefManager.photo, example.data.user.profile_image);
                    SharedPrefManager.getInstance(signup.this)
                            .setStringData(SharedPrefManager.profilethumb, example.data.user.profile_image_thumb);
                    Intent intent = new Intent(signup.this, tabViewpager.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserGroupResponse> call, Throwable t) {
                Toast.makeText(signup.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //request for permission
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        selectImage();
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(signup.this);
        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        //Create a file to store the image
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {

                        }
                        if (photoFile != null) {
                            // dought
                            Uri photoURI;
                            photoURI = FileProvider.getUriForFile(signup.this, getApplicationContext().getPackageName() + ".provider", photoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    photoURI);
                            startActivityForResult(intent,
                                    1);
                        }
                    }
                    //   File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg"); // for storing our image in file
                    //  intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[which].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // request permission will return the request code and result code that we will check in on activityresult
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                photo.setImageBitmap(imageBitmap);
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Glide.with(this).load(imageFilePath).into(photo);
               /* f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg"))
                        f = temp;
                    break;
                }

                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    photo.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            } else if (requestCode == 2) {

                // complete dought
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.d("path of image ", picturePath + "");
                photo.setImageBitmap(thumbnail);
            }
        }
    }

    String imageFilePath;

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean gallary = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (gallary && cameraAccepted)
                        Toast.makeText(getApplication(), "Permission Granted, Now you can access Gallary data and camera.", Toast.LENGTH_LONG).show();
                    else {

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


    public boolean validate() {
        if(photo.getDrawable() == null){
            Toast.makeText(getApplicationContext(),"please select photo",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (first.getText().toString().isEmpty()) {
            first.setError("First name can not be blank");
            return false;
        } else if (last.getText().toString().isEmpty()) {
            last.setError("Last name can not be blank");
            return false;
        }
        String str = login.validateEmail(email.getText().toString().trim());
        if (!TextUtils.isEmpty(str)) {
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError("Password can not be blank");
            return false;
        } else if (confirmpassword.getText().toString().length() == 0) {
            confirmpassword.setError("Password can not be blank");
            return false;
        } else if (!password.getText().toString().equalsIgnoreCase(confirmpassword.getText().toString())) {
            confirmpassword.setError("password does not match");
            return false;
        }


        return true;
    }


}
