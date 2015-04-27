package com.jim.demo1;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jim on 2/2/2015.
 */
public class matches_activity extends Activity implements LocationListener {
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
    public void onLocationChanged(Location location) {
       myLocation = new myLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
       // myLocation.setLatd(location.getLatitude());
        System.out.println("Latitude = " + myLocation.getLatd());
       // myLocation.setLongd(location.getLongitude());
        System.out.println("Longitude = " + myLocation.getLongd());


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


class myLocation{
    String latd;
    String longd;

    public myLocation(String theLat, String theLong){
        this.latd = theLat;
        this.longd = theLong;
    }

    public String getLatd() {
        return latd;
    }

    public void setLatd(String latd) {
        this.latd = latd;
    }

    public String getLongd() {
        return longd;
    }

    public void setLongd(String longd) {
        this.longd = longd;
    }
}