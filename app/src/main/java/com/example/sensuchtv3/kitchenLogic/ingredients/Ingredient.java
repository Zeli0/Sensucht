package com.example.sensuchtv3.kitchenLogic.ingredients;

import java.util.ArrayList;

public class Ingredient {
    private String name;
    private int number;
    private String quantifier;
    public static final Ingredient EMPTY = new Ingredient("None", 0, null);

    public Ingredient(String name, int number, String quantifier) {
        this.name = name;
        this.number = number;
        this.quantifier = quantifier;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getQuantifier() {
        return quantifier;
    }

    public static ArrayList<Ingredient> createIngredientList() {
        ArrayList<Ingredient> ings = new ArrayList<Ingredient>();
        ings.add(EMPTY);
        return ings;
    }
}
