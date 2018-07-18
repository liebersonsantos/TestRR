package com.example.naville.rrtracking_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecoverPasswordResponse implements Parcelable{

    @SerializedName("status")
    private int status;
    @SerializedName("resultado")
    private String result;

    public RecoverPasswordResponse() {
    }

    public RecoverPasswordResponse(int status, String result) {
        this.status = status;
        this.result = result;
    }

    protected RecoverPasswordResponse(Parcel in) {
        status = in.readInt();
        result = in.readString();
    }

    public static final Creator<RecoverPasswordResponse> CREATOR = new Creator<RecoverPasswordResponse>() {
        @Override
        public RecoverPasswordResponse createFromParcel(Parcel in) {
            return new RecoverPasswordResponse(in);
        }

        @Override
        public RecoverPasswordResponse[] newArray(int size) {
            return new RecoverPasswordResponse[size];
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
}
