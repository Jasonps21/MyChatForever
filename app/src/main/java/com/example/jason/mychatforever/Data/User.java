package com.example.jason.mychatforever.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
    private String nama;
    private String email;
    private String telepon;

    protected User(Parcel in) {
        nama = in.readString();
        email = in.readString();
        telepon = in.readString();
    }

    public User() {

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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nama);
        parcel.writeString(email);
        parcel.writeString(telepon);
    }
}
