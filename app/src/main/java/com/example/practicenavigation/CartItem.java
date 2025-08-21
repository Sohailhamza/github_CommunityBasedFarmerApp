package com.example.practicenavigation;

public class CartItem {
    private final String name;
    private final int imageRes;
    private final double price;

    public CartItem(String name, int imageRes, double price) {
        this.name = name;
        this.imageRes = imageRes;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public double getPrice() {
        return price;
    }
}
