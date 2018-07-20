package com.example.naville.rrtracking_android.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MyLocation {

    private GoogleApiClient googleApiClient;
    private static final String TAG = "Localizacao  atual";
    public static FusedLocationProviderClient fusedLocationProviderClient;
    public static Location mMyLocation;
    public static Double lat, lng;
    public static LocationCallback locationCallback;
    public static LocationRequest mLocationRequest;

    public static void fusedLocation(Activity activity) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    public static void getMyLocation(Activity activity) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(activity, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                try {
                    if (task.isSuccessful() && task.getResult() != null){

                        mMyLocation = task.getResult();

                        lat = mMyLocation.getLatitude();
                        lng = mMyLocation.getLongitude();

                        Log.i(TAG, "onComplete: " + lat + " " + lng);

                        mLocationRequest = new LocationRequest();
                        mLocationRequest.setInterval(1000);
                        mLocationRequest.setFastestInterval(100);
                        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                }

                }catch (Exception e){
                    Log.i(TAG, "onCompleteERRO: " + e.getMessage());
                }

            }
        });

    }



}
