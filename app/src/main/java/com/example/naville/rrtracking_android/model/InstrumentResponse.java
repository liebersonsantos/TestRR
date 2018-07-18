package com.example.naville.rrtracking_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstrumentResponse implements Parcelable{

    @SerializedName("status")
    private int status;
    @SerializedName("resultado")
    private String result;
    @SerializedName("instrumentos")
    private List<Instrument> instrumentList;

    public InstrumentResponse() {
    }

    public InstrumentResponse(int status, String result, List<Instrument> instrumentList) {
        this.status = status;
        this.result = result;
        this.instrumentList = instrumentList;
    }

    protected InstrumentResponse(Parcel in) {
        status = in.readInt();
        result = in.readString();
    }

    public static final Creator<InstrumentResponse> CREATOR = new Creator<InstrumentResponse>() {
        @Override
        public InstrumentResponse createFromParcel(Parcel in) {
            return new InstrumentResponse(in);
        }

        @Override
        public InstrumentResponse[] newArray(int size) {
            return new InstrumentResponse[size];
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

    public List<Instrument> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<Instrument> instrumentList) {
        this.instrumentList = instrumentList;
    }
}
