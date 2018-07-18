package com.example.naville.rrtracking_android.model;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable{

    private final SharedPreferences sharedpreferences = null;


    @SerializedName("")
    private int userId;
    @SerializedName("")
    private String userName;
    @SerializedName("")
    private String passWord;
    @SerializedName("email_usuario")
    private String email;


    public User() {
    }

    public User(int userId, String userName, String passWord, String email) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        passWord = in.readString();
        email = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(passWord);
        dest.writeString(email);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
