package com.example.sensuchtv3.kitchenLogic.locationLogic;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ColdStorage extends Supermarket{
    public ColdStorage(LatLng martLocation) {
        super(martLocation);
    }

    @Override
    public CompletableFuture<Double> scrape(String[] ingredients) {
        CompletableFuture<Double> doc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            doc = CompletableFuture.supplyAsync(() -> {
                        try {
                            return Jsoup.connect("https://coldstorage.com.sg/search?")
                                        .data("q", ingredients[0])
                                        .get();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .thenApplyAsync(theDoc -> theDoc.select("div.product_detail"))
                    .thenApplyAsync(goodies -> goodies.stream().map(good -> {
                            Elements temp = good.getElementsByClass("price_now f-green price_normal");
                            if (temp.isEmpty()) {
                                temp = good.getElementsByClass("price_now price_normal f-green disc");
                            }
                            return temp;
                    })
                            .filter(x -> x.size() > 0)
                            .map(goods -> {

                                return goods.first();
                            })
                            .map(good -> good.text())
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
        }

        return doc;
    }
}
