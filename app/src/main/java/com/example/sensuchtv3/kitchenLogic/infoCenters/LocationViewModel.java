package com.example.sensuchtv3.kitchenLogic.infoCenters;

import android.location.Location;
import android.os.Build;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.sensuchtv3.kitchenLogic.ingredients.Ingredient;
import com.example.sensuchtv3.kitchenLogic.locationLogic.Supermarket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<Location> userLocation;
    private MutableLiveData<List<Supermarket>> nearbyMarts;
    private MutableLiveData<List<Location>> nearbyMartLocations;

    public LocationViewModel() {
        userLocation = new MutableLiveData<>();
        nearbyMarts = new MutableLiveData<>();
        nearbyMartLocations = new MutableLiveData<>();
    }

    public void setUserLocation(Location userKitchen) {
        userLocation.setValue(userKitchen);
    }

    public Location getUserLocation() {
        return userLocation.getValue();
    }

    public void setNearbyMarts(List<Supermarket> locationArray){
        nearbyMarts.setValue(locationArray);
    }

    public CompletableFuture<Double> collectiveScrape(Ingredient[] ingredients) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return nearbyMarts.getValue()
                    .stream()
                    .map(mart -> mart.scrape(ingredients))
                    .reduce(CompletableFuture.supplyAsync(() -> Double.MAX_VALUE),
                            (x, y) -> x.thenCombineAsync(y, Math::min));
        }
        return null;
    }
}
