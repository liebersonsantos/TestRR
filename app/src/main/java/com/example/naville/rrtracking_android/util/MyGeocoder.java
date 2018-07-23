package com.example.naville.rrtracking_android.util;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.util.List;

public class MyGeocoder {

    public static String address;
    public static String instrumentAdress;

    public static void getMyAddress(Activity activity, Double latitude, Double longitude){

        Geocoder geocoder = new Geocoder(activity);
        List<Address> getAddress;
        try {
            getAddress = geocoder.getFromLocation(latitude, longitude, 1);

            for (int i = 0; i < getAddress.size(); i++) {

                address = getAddress.get(0).getAddressLine(0);
                Log.i("Geocoder", "Endereço: " + address);
            }

        }catch (Exception e){
            e.getMessage();
        }
    }

    public static void getInstrumentAddress(Activity activity, float latitude, float longitude){

        Geocoder geocoder = new Geocoder(activity);
        List<Address> getAddress;
        try {
            getAddress = geocoder.getFromLocation(latitude, longitude, 1);

            for (int i = 0; i < getAddress.size(); i++) {

                instrumentAdress = getAddress.get(0).getAddressLine(0);
                Log.i("Geocoder", "Endereço: " + instrumentAdress);
            }

        }catch (Exception e){
            e.getMessage();
        }
    }



}
