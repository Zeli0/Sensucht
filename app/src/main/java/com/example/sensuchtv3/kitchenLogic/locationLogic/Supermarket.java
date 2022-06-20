package com.example.sensuchtv3.kitchenLogic.locationLogic;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<Double> scrape(String[] ingredients) {
        return null;
    }
}
