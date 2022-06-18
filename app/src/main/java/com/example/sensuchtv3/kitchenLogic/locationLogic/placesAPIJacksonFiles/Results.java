package com.example.sensuchtv3.kitchenLogic.locationLogic.placesAPIJacksonFiles;

public class Results {
    private Geometry geometry;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
