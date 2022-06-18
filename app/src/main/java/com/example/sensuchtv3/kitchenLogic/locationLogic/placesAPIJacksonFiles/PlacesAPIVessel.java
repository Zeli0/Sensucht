package com.example.sensuchtv3.kitchenLogic.locationLogic.placesAPIJacksonFiles;

import com.example.sensuchtv3.kitchenLogic.locationLogic.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class PlacesAPIVessel {
    private Results[] results;
    private int ntuc = 0,
                    cs = 0,
                    ss = 0,
                    giant =0,
                    ryan = 0;

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    public ArrayList<Supermarket> filter() {
        ArrayList<Supermarket> marts = new ArrayList<>();
        for (Results r : results) {
            String name = r.getName().toLowerCase(Locale.ROOT);
            Location temp = r.getGeometry().getLocation();
            LatLng tempCorrectForm = new LatLng(temp.getLat(), temp.getLng());
            if (name.contains("fairprice")) {

                marts.add(new Fairprice(tempCorrectForm));
                ntuc++;
            } else if (name.contains("cold storage")) {
                marts.add(new ColdStorage(tempCorrectForm));
                cs++;
            } else if (name.contains("sheng shiong")) {
                marts.add(new ShengShiong(tempCorrectForm));
                ss++;
            } else if (name.contains("giant")) {
                marts.add(new Giant(tempCorrectForm));
                giant++;
            } else if (name.contains("ryan's")) {
                marts.add(new RyanMarket(tempCorrectForm));
                ryan++;
            }
        }
        return marts;
    }

    public ArrayList<Supermarket> getFinalPicks() {
        return null;
    }

}
