package com.jim.demo1.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jasekurasz on 5/21/15.
 */
public class User implements Parcelable {
    private long id;
    private String username;
    private String password;
    private double latitude;
    private double longitude;

    public User() {
    }

    public User(long id, String username, String password, double latitude, double longitude) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "\"id\":" + id + ",\"username\":\"" + username + "\",\"password\":" + password + ",\"latitude\":" + latitude + ",\"longitude\":" + longitude + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);

    }

    private User(Parcel in) {
        id = in.readLong();
        username = in.readString();
        password = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
