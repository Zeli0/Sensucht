package com.example.sensuchtv3.kitchenLogic.locationLogic;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

public class Supermarket {
    private LatLng martLocation;
    private double distance;

    public Supermarket(LatLng martLocation) {
        this.martLocation = martLocation;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int[] scrape() {
        return null;
    }
}
