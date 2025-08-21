package com.example.practicenavigation;

public class RecommendedItem {
    private String name;
    private int imageResource;

    public RecommendedItem(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}

