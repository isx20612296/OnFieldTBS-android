package com.example.onfieldtbs_android.service.google;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GoogleLocationServices {
    public static void getCoordinate(Context context, LocationInterface locationResponse){

        // Store the request parameters in the location provider
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Check if permissions have been granted
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        // LocationServices: location services integration
        // LocationsCallBack: It is used to receive notifications from the moment the location of the device has changed
        // Method requestLocationUpdate: Request location updates with a callback on the specified Looper thread
        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // removeLocationUpdate: Removes all location updates for the given pending intent.
                LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(this);
                if(locationResult.getLocations().size() >0){
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    locationResponse.getLocation(latitude, longitude);
                }
            }
        }, Looper.myLooper());


    }
}
