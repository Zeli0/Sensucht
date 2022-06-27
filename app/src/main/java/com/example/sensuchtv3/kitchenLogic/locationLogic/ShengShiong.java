package com.example.sensuchtv3.kitchenLogic.locationLogic;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import okhttp3.HttpUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ShengShiong extends Supermarket{
    public ShengShiong(LatLng martLocation) {
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
                                //This website does offer the ability to filter based on food groups
                                return Jsoup.connect("https://shengsiong.com.sg/")
                                        .maxBodySize(0)
                                        .get();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .thenApplyAsync(theDoc ->  theDoc.select("div.card-body"))
                        //div.product-packSize for the size of the goods
                        .thenApplyAsync(goodies -> goodies.stream().map(good ->
                                        good.getElementsByClass("product-price"))
                                .filter(x -> x.size() > 0)
                                .map(price -> {
                                    return price.get(0);
                                })
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
