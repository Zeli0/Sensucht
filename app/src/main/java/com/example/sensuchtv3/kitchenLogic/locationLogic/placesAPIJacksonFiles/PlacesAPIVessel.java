package com.example.sensuchtv3.kitchenLogic.locationLogic.placesAPIJacksonFiles;

import com.example.sensuchtv3.kitchenLogic.locationLogic.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class PlacesAPIVessel {
    private Results[] results;

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    private int ntuc = -1,
            cs = -1,
            ss = -1,
            giant = -1,
            ryan = -1;

    public ArrayList<Supermarket> filter() {
        ArrayList<Supermarket> marts = new ArrayList<>();
        for (Results r : results) {
            String name = r.getName().toLowerCase(Locale.ROOT);
            Location temp = r.getGeometry().getLocation();
            LatLng tempCorrectForm = new LatLng(temp.getLat(), temp.getLng());
            if (name.contains("fairprice")) {
                if (ntuc == -1) {
                    marts.add(new Fairprice(tempCorrectForm));
                    ntuc = marts.size() - 1;
                } else {
                    marts.get(ntuc).addLocation(tempCorrectForm);
                }
            } else if (name.contains("cold storage")) {
                if (cs == -1) {
                    marts.add(new ColdStorage(tempCorrectForm));
                    cs = marts.size() - 1;
                } else {
                    marts.get(cs).addLocation(tempCorrectForm);
                }
            } else if (name.contains("sheng shiong")) {
                if (ss == -1) {
                    marts.add(new ShengShiong(tempCorrectForm));
                    ss = marts.size() - 1;
                } else {
                    marts.get(ss).addLocation(tempCorrectForm);
                }
            } else if (name.contains("giant")) {
                if (giant == -1) {
                    marts.add(new Giant(tempCorrectForm));
                    giant = marts.size() - 1;
                } else {
                    marts.get(giant).addLocation(tempCorrectForm);
                }
            } else if (name.contains("ryan's")) {
                if (ryan == -1) {
                    marts.add(new RyanMarket(tempCorrectForm));
                    ryan = marts.size() - 1;
                } else {
                    marts.get(ryan).addLocation(tempCorrectForm);
                }
            }
        }
        return marts;
    }

    public ArrayList<Supermarket> getFinalPicks() {
        return null;
    }

}
