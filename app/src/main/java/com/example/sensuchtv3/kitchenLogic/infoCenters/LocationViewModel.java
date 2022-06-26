package com.example.sensuchtv3.kitchenLogic.infoCenters;

import android.location.Location;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.sensuchtv3.kitchenLogic.locationLogic.Supermarket;

import java.util.List;

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

    public void setNearbyMartLocations(List<Location> locationArray){
        nearbyMartLocations.setValue(locationArray);
    }


}
