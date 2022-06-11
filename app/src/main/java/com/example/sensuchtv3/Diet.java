package com.example.sensuchtv3;

import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Diet {
    private String name;
    private int type;
    private boolean toggled;
    public static final int BAN = 2,HABIT = 1, NONE = 0;


    public Diet(String name) {
        this.name = name;
    }

    public Diet(String name, boolean toggled) {
        this.name = name;
        this.toggled = toggled;
    }

    public String getName() {
        return name;
    }

    public boolean getChecked() {
        return toggled;
    }

    public void flipToggle() {
        toggled = !toggled;
    }
    public static List<Diet> getHabits() {
        String[] names = {"Halal", "Gluten Free", "Ketogenic", "Vegetarian", "Lactose Intolerant", "Vegan",
                            "Ovo-Vegetarian", "Pescetarian", "Paleo", "Primal", "Low FODMAP", "Whole30"};
        List<String> habitNames = Arrays.asList(names);
        Function<String, Diet> goober = x -> new Diet(x, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return habitNames.stream().map(goober).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
