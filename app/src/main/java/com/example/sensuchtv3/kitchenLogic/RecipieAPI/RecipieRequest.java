package com.example.sensuchtv3.kitchenLogic.RecipieAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RecipieRequest {
    private int offset;
    private int number;
    private int totalResults;
    @SerializedName("results")
    private List<Recipe> recipes;

    public RecipieRequest(int rOffset, int rNumber, int rTotalResults, List<Recipe> rRecipe) {
        offset = rOffset;
        number = rNumber;
        totalResults = rTotalResults;
        recipes = rRecipe;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
