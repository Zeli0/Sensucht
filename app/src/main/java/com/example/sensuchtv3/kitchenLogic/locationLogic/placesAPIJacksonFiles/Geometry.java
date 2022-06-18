package com.example.sensuchtv3.kitchenLogic.locationLogic.placesAPIJacksonFiles;



public class Geometry {
    private Location location;

    public void setLocation(Location newLocation) {
        location = newLocation;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "[" + location.getLat() + "," + location.getLng() + "]";
    }
}
