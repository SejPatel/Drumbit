package com.example.devyani.drumbeatapplication.model.UserGroup;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by devyani on 19/4/18.
 */

public class UserData implements Parcelable {

    public int group_id;
    public String name;
    public String content;
    public String location;
    public String latitude;
    public String longitude;
    public int geofence_radius;
    public double distance;
    public int group_chat;
    public int private_chat;
    public int total_view;
    public String original_pic;
    public String thumb_pic;
    public String is_admin;
    public User user;
    public String created;
    public String datetime;
    public int is_following;

    protected UserData(Parcel in) {
        group_id = in.readInt();
        name = in.readString();
        content = in.readString();
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        geofence_radius = in.readInt();
        distance = in.readDouble();
        group_chat = in.readInt();
        private_chat = in.readInt();
        total_view = in.readInt();
        original_pic = in.readString();
        thumb_pic = in.readString();
        is_admin = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        created = in.readString();
        datetime = in.readString();
        is_following = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(group_id);
        dest.writeString(name);
        dest.writeString(content);
        dest.writeString(location);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeInt(geofence_radius);
        dest.writeDouble(distance);
        dest.writeInt(group_chat);
        dest.writeInt(private_chat);
        dest.writeInt(total_view);
        dest.writeString(original_pic);
        dest.writeString(thumb_pic);
        dest.writeString(is_admin);
        dest.writeParcelable(user, flags);
        dest.writeString(created);
        dest.writeString(datetime);
        dest.writeInt(is_following);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
