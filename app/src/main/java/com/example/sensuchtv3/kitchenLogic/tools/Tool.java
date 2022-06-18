package com.example.sensuchtv3.kitchenLogic.tools;

import java.util.ArrayList;

public class Tool {
    private String name;
    private int type;
    public static final int TOOL = 2, NONE = 0;
    public static final Tool EMPTY = new Tool("None", NONE);

    public Tool(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static ArrayList<Tool> createRestrictionList() {
        ArrayList<Tool> restrict = new ArrayList<>();
        restrict.add(EMPTY);
        return restrict;
    }
}
