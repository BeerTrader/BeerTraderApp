package com.jim.demo1.Favorites;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.jim.demo1.R;

/**
 * Created by Jim on 5/2/2015.
 */
public class Location extends Activity implements LocationListener {
    protected LocationManager locationManager;
    myLocation myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_layout);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }


    @Override
    public void onLocationChanged(android.location.Location location) {
        myLocation = new myLocation((location.getLatitude()), (location.getLongitude()));
        System.out.println("Latitude = " + String.valueOf(myLocation.getLatd()));
        System.out.println("Longitude = " + String.valueOf(myLocation.getLongd()));


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

}


class myLocation {
    double latd;
    double longd;

    public myLocation(double theLat, double theLong) {
        this.latd = theLat;
        this.longd = theLong;
    }

    public double getLatd() {
        return latd;
    }

    public void setLatd(double latd) {
        this.latd = latd;
    }

    public double getLongd() {
        return longd;
    }

    public void setLongd(double longd) {
        this.longd = longd;
    }

    {
    }
}
