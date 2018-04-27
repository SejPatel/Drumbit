package com.example.devyani.drumbeatapplication.model.UserGroup;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by devyani on 19/4/18.
 */

public class UserGroupData implements Parcelable {

    public int tagged_count;
    public int follower_count;
    public String access_token;

    public ArrayList<UserData> groups = new ArrayList();
    public User user;


    protected UserGroupData(Parcel in) {
        tagged_count = in.readInt();
        follower_count = in.readInt();
        groups = in.createTypedArrayList(UserData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tagged_count);
        dest.writeInt(follower_count);
        dest.writeTypedList(groups);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserGroupData> CREATOR = new Creator<UserGroupData>() {
        @Override
        public UserGroupData createFromParcel(Parcel in) {
            return new UserGroupData(in);
        }

        @Override
        public UserGroupData[] newArray(int size) {
            return new UserGroupData[size];
        }
    };
}
