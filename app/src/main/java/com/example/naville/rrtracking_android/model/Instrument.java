package com.example.naville.rrtracking_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Instrument implements Parcelable{

    @SerializedName("id_rastreador")
    private String instrumentID;
    @SerializedName("instrumento_nome")
    private String instrumentName;
    @SerializedName("instrumento_detalhes")
    private String instrumentDetails;
    @SerializedName("instrumento_serie")
    private String instrumentSerialNumber;
    @SerializedName("instrumento_modelo")
    private String instrumentModel;
    @SerializedName("instrumento_marca")
    private String instrumentBrand;
    @SerializedName("rastreador_serie")
    private String trackerSerialNumber;
    @SerializedName("rastreador_chip")
    private String trackerChipNumber;
    @SerializedName("rastreador_imei")
    private String trackerIMEINumber;
    @SerializedName("rastreador_ultima_localizacao")
    private String trackerLastLocation;
    @SerializedName("rastreador_latitude")
    private Float trackerLatitude;
    @SerializedName("rastreador_longitude")
    private Float trackerLongitude;
    @SerializedName("imagens")
    private List<String> imagesList;

    public Instrument() {
    }

    public Instrument(String instrumentID, String instrumentName, String instrumentDetails, String instrumentSerialNumber, String instrumentModel, String instrumentBrand, String trackerSerialNumber, String trackerChipNumber, String trackerIMEINumber, String trackerLastLocation, Float trackerLatitude, Float trackerLongitude, List<String> imagesList) {
        this.instrumentID = instrumentID;
        this.instrumentName = instrumentName;
        this.instrumentDetails = instrumentDetails;
        this.instrumentSerialNumber = instrumentSerialNumber;
        this.instrumentModel = instrumentModel;
        this.instrumentBrand = instrumentBrand;
        this.trackerSerialNumber = trackerSerialNumber;
        this.trackerChipNumber = trackerChipNumber;
        this.trackerIMEINumber = trackerIMEINumber;
        this.trackerLastLocation = trackerLastLocation;
        this.trackerLatitude = trackerLatitude;
        this.trackerLongitude = trackerLongitude;
        this.imagesList = imagesList;
    }

    protected Instrument(Parcel in) {
        instrumentID = in.readString();
        instrumentName = in.readString();
        instrumentDetails = in.readString();
        instrumentSerialNumber = in.readString();
        instrumentModel = in.readString();
        instrumentBrand = in.readString();
        trackerSerialNumber = in.readString();
        trackerChipNumber = in.readString();
        trackerIMEINumber = in.readString();
        trackerLastLocation = in.readString();
        if (in.readByte() == 0) {
            trackerLatitude = null;
        } else {
            trackerLatitude = in.readFloat();
        }
        if (in.readByte() == 0) {
            trackerLongitude = null;
        } else {
            trackerLongitude = in.readFloat();
        }
        imagesList = in.createStringArrayList();
    }

    public static final Creator<Instrument> CREATOR = new Creator<Instrument>() {
        @Override
        public Instrument createFromParcel(Parcel in) {
            return new Instrument(in);
        }

        @Override
        public Instrument[] newArray(int size) {
            return new Instrument[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(instrumentID);
        dest.writeString(instrumentName);
        dest.writeString(instrumentDetails);
        dest.writeString(instrumentSerialNumber);
        dest.writeString(instrumentModel);
        dest.writeString(instrumentBrand);
        dest.writeString(trackerSerialNumber);
        dest.writeString(trackerChipNumber);
        dest.writeString(trackerIMEINumber);
        dest.writeString(trackerLastLocation);
        if (trackerLatitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(trackerLatitude);
        }
        if (trackerLongitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(trackerLongitude);
        }
        dest.writeStringList(imagesList);
    }

    public String getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(String instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentDetails() {
        return instrumentDetails;
    }

    public void setInstrumentDetails(String instrumentDetails) {
        this.instrumentDetails = instrumentDetails;
    }

    public String getInstrumentSerialNumber() {
        return instrumentSerialNumber;
    }

    public void setInstrumentSerialNumber(String instrumentSerialNumber) {
        this.instrumentSerialNumber = instrumentSerialNumber;
    }

    public String getInstrumentModel() {
        return instrumentModel;
    }

    public void setInstrumentModel(String instrumentModel) {
        this.instrumentModel = instrumentModel;
    }

    public String getInstrumentBrand() {
        return instrumentBrand;
    }

    public void setInstrumentBrand(String instrumentBrand) {
        this.instrumentBrand = instrumentBrand;
    }

    public String getTrackerSerialNumber() {
        return trackerSerialNumber;
    }

    public void setTrackerSerialNumber(String trackerSerialNumber) {
        this.trackerSerialNumber = trackerSerialNumber;
    }

    public String getTrackerChipNumber() {
        return trackerChipNumber;
    }

    public void setTrackerChipNumber(String trackerChipNumber) {
        this.trackerChipNumber = trackerChipNumber;
    }

    public String getTrackerIMEINumber() {
        return trackerIMEINumber;
    }

    public void setTrackerIMEINumber(String trackerIMEINumber) {
        this.trackerIMEINumber = trackerIMEINumber;
    }

    public String getTrackerLastLocation() {
        return trackerLastLocation;
    }

    public void setTrackerLastLocation(String trackerLastLocation) {
        this.trackerLastLocation = trackerLastLocation;
    }

    public Float getTrackerLatitude() {
        return trackerLatitude;
    }

    public void setTrackerLatitude(Float trackerLatitude) {
        this.trackerLatitude = trackerLatitude;
    }

    public Float getTrackerLongitude() {
        return trackerLongitude;
    }

    public void setTrackerLongitude(Float trackerLongitude) {
        this.trackerLongitude = trackerLongitude;
    }

    public List<String> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<String> imagesList) {
        this.imagesList = imagesList;
    }
}


//@Override
//    public String toString() {
//
//        return "Nome do instrumento: " + getInstrumentName()
//                + "\nDetalhes do instrumento: " + getInstrumentDetails()
//                + "\nNúmero de série do instrumento: " + getInstrumentSerialNumber()
//                + "\nModelo do instrumento: " + getInstrumentModel()
//                + "\nMarca do instrumento:" + getInstrumentBrand()
//                + "\nNúmero de série do rastreador: " + getTrackerSerialNumber()
//                + "\nNúmero do chip do rastreador: " + getTrackerChipNumber()
//                + "\nIMEI do rastreador: " + getTrackerIMEINumber()
//                + "\nÚltima localização do rastreador: " + getTrackerLastLocation()
//                + "\nLatitude e longitude do rastreador: " + getTrackerLatitude() + "," + getTrackerLongitude()
//                + "\nImagens do instrumento: " + getImagesList();
//
//    }
