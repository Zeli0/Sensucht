package com.example.sensuchtv3.kitchenLogic.locationLogic;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Fairprice extends Supermarket{
    public Fairprice(LatLng martLocation) {
        super(martLocation);
    }


    @Override
    public CompletableFuture<Double> scrape(String[] ingredients) {
        CompletableFuture<Double> finalCost = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            finalCost = CompletableFuture.supplyAsync(() -> 0.0);
            for (String ing : ingredients) {
                CompletableFuture<Double> doc = CompletableFuture.supplyAsync(() -> {
                            try {
                                return Jsoup.connect("https://www.fairprice.com.sg/search?")
                                        .data("query", ing)
                                        .get();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .thenApplyAsync(theDoc -> theDoc.getElementsByClass(
                                "sc-1plwklf-0 iknXK product-container"))
                        .thenApplyAsync(goodies -> goodies.stream().map(
                                //This will only get the price of the good in total
                                good ->good.getElementsByClass("sc-1bsd7ul-1 gJhHzP"))
                                //The class name below gets quantity, if needed
                                    //sc-1bsd7ul-1 LLmwF
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
