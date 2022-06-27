package com.example.sensuchtv3.kitchenLogic.locationLogic;

import android.location.Location;
import com.example.sensuchtv3.kitchenLogic.ingredients.Ingredient;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Supermarket {
    private ArrayList<LatLng> martLocation;
    private double distance;

    public Supermarket(LatLng martLocation) {
        this.martLocation = new ArrayList<>();
        this.martLocation.add(martLocation);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void addLocation(LatLng newStore) {
        martLocation.add(newStore);
    }

    public CompletableFuture<Double> scrape(Ingredient[] ingredients) {
        return null;
    }
}
