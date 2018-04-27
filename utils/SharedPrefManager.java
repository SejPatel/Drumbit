package com.example.devyani.drumbeatapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by devyani on 16/4/18.
 */

public class SharedPrefManager {

    public static SharedPrefManager sharedPrefManager;
    public static SharedPreferences sharedPreferences = null;
    public static SharedPreferences.Editor editor;
    public static String ACCESS_TOKEN = "token";
    public static String USER_ID = "id";
    public static String First_Name = "firstname";
    public static String Last_Name = "lastname";
    public static String Email = "email";
    public static String photo = "photo";
    public static String profilethumb = "profilethumb";
    public static String islogin = "islogin";

    public static String lat = "lat";
    public static String lng = "lng";


    public SharedPrefManager(Context context) {

        if (sharedPrefManager == null) {
            sharedPrefManager = new SharedPrefManager();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            editor = sharedPreferences.edit();
        }
    }

    public SharedPrefManager() {

    }

    public static SharedPrefManager getInstance(Context context) {
        if (sharedPrefManager == null) {
            new SharedPrefManager(context);
        }
        return sharedPrefManager;
    }

    public void clearSharedPref() {
        sharedPrefManager = null;
    }


    public void setStringData(String key, String value) {

        editor.putString(key, value).commit();
    }

    public void setIntegerData(String key, Integer value) {

        editor.putInt(key, value).commit();
    }

    public void setFloatData(String key, Float value) {

        editor.putFloat(key, value).commit();
    }


    public float getFloatValue(String key) {
        return sharedPreferences.getFloat(key, 0f);
    }
    public double getLongValue(String key){
        return sharedPreferences.getLong(key,0);
    }


    public void setBooleanData(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public boolean getBooleanValue(String key) {

        return sharedPreferences.getBoolean(key, false);
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, "token");
    }

    public int getIntValue(String key) {
        return sharedPreferences.getInt(key, 0);
    }

}
