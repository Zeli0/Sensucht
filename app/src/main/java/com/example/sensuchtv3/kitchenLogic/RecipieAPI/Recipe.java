package com.example.sensuchtv3.kitchenLogic.RecipieAPI;

import com.google.gson.annotations.SerializedName;

public class Recipe {
    private int id;
    private String title;
    @SerializedName("image")
    private String imageURL;
    private String imageType;

    public Recipe(int rid, String rtitle, String rImageURL, String rImageType) {
        id = rid;
        title = rtitle;
        imageURL = rImageURL;
        imageType = rImageType;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }
}
