package com.example.naville.rrtracking_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Login implements Parcelable{

    @SerializedName("status")
    private int status;
    @SerializedName("resultado")
    private String result;
    @SerializedName("id_usuario")
    private int userId;
    @SerializedName("nome_usuario")
    private String userName;

    public Login() {
    }

    public Login(int status, String result, int userId, String userName) {
        this.status = status;
        this.result = result;
        this.userId = userId;
        this.userName = userName;
    }

    protected Login(Parcel in) {
        status = in.readInt();
        result = in.readString();
        userId = in.readInt();
        userName = in.readString();
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(result);
        dest.writeInt(userId);
        dest.writeString(userName);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
