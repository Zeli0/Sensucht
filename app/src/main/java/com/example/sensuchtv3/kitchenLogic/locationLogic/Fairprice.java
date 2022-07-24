package com.example.sensuchtv3.kitchenLogic.locationLogic;

import android.location.Location;
import com.example.sensuchtv3.kitchenLogic.ingredients.Ingredient;
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
    public CompletableFuture<Double> scrape(Ingredient[] ingredients) {
        CompletableFuture<Double> finalCost = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            finalCost = CompletableFuture.supplyAsync(() -> 0.0);
            for (Ingredient ing : ingredients) {
                //Stored in completablefuture to once again prevent the app from freezing when this data is being obtained
                CompletableFuture<Double> doc = CompletableFuture.supplyAsync(() -> {
                            try {
                                return Jsoup.connect("https://www.fairprice.com.sg/search?")
                                        //ing is the ingredient
                                        .data("query", ing.getName())
                                        .get();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        //First obtain the element containing the product
                        .thenApplyAsync(theDoc -> theDoc.getElementsByClass(
                                "sc-1plwklf-0 iknXK product-container"))
                        //Then get the price
                        .thenApplyAsync(goodies -> goodies.stream().map(
                                //This will only get the price of the good in total
                                good ->good.getElementsByClass("sc-1bsd7ul-1 gJhHzP"))
                                //The class name below gets quantity, if needed
                                    //sc-1bsd7ul-1 LLmwF
                                .filter(x -> x.size() > 0)
                                .map(Elements::first)
                                .filter(Objects::nonNull)
                                //Get Text
                                .map(Element::text)
                                //Remove the dollar sign
                                .map(price -> price.substring(1))
                                //Remove unnecessary text
                                .map(price -> price.split(" ")[0])
                                //Convert to double
                                .map(Double::parseDouble)
                                //Get smallest price
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
