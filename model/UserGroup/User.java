package com.example.devyani.drumbeatapplication.model.UserGroup;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by devyani on 19/4/18.
 */

public class User implements Parcelable{

    public int user_id;
    public String first_name;
    public String last_name;
    public String email;
    public String profile_image;
    public String profile_image_thumb;


    protected User(Parcel in) {
        user_id = in.readInt();
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        profile_image = in.readString();
        profile_image_thumb = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt((user_id));
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(profile_image);
        dest.writeString(profile_image_thumb);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
