package com.example.sensuchtv3.kitchenLogic;

public class Budget {
    private int money;
    private int type;
    public final static int MONTHLY = 0, DAILY = 1, PER_MEAL = 2;

    public Budget(int money, int type) {
        this.money = money;
        this.type = type;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int newMoney) {
        money = newMoney;
    }

    public int getType() {
        return type;
    }

    public void setType(int newType) {
        type = newType;
    }

    //Gets the budget in the per-meal format
    public int getPrice() {
        int divider = 1;
        switch (type) {
            case MONTHLY:
                divider = 30*3;
                break;
            case DAILY:
                divider = 3;
                break;
            case PER_MEAL:
                break;
        }
        return money / divider;
    }
}
