package com.example.sensuchtv3.kitchenLogic.locationLogic;

import android.location.Location;
import com.example.sensuchtv3.kitchenLogic.ingredients.Ingredient;
import com.google.android.gms.maps.model.LatLng;
import okhttp3.HttpUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Giant extends Supermarket{
    public Giant(LatLng martLocation) {
        super(martLocation);
    }

    @Override
    public CompletableFuture<Double> scrape(Ingredient[] ingredients) {
        CompletableFuture<Double> finalCost = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            finalCost = CompletableFuture.supplyAsync(() -> 0.0);
            for (Ingredient ing : ingredients) {
                CompletableFuture<Double> doc = CompletableFuture.supplyAsync(() -> {
                            try {
                                return Jsoup.connect("https://giant.sg/search?")
                                        .data("q", ing.getName())
                                        .get();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .thenApplyAsync(theDoc -> {
                            System.out.println(theDoc.toString());
                            return theDoc.select("div.product_detail");})
                        .thenApplyAsync(goodies -> goodies.stream().map(good -> {
                                    Elements temp = good.getElementsByClass("price_now price-any price_normal");
                                    if (temp.isEmpty()) {
                                        temp = good.getElementsByClass("price_now f-green price_normal");
                                        if (temp.isEmpty()) {
                                            temp = good.getElementsByClass("price_now price-buy price_normal");
                                        }
                                    }
                                    return temp;
                                })
                                .filter(x -> x.size() > 0)
                                .map(Elements::first)
                                .filter(Objects::nonNull)
                                .map(Element::text)
                                .map(price -> price.substring(1))
                                .map(price -> price.split(" ")[0])
                                .map(Double::parseDouble)
                                .reduce(Double.MAX_VALUE, (subtotal, element) -> element < subtotal ?
                                        element
                                        : subtotal))
                        .whenComplete((msg, ex) -> {
                            if (ex != null) {
                                System.out.println("Exception occurred");
                            } else {
                                System.out.println(msg);
                            }});
                finalCost.thenCombineAsync(doc, Double::sum);
            }
        }

        return finalCost;
    }
}
