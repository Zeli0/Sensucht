package com.example.sensuchtv3;

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

    public void setType(int newType) {
        type = newType;
    }

    public int getType() {
        return type;
    }

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
