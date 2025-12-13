package com.example.practicenavigation;

public class ProductModel {
    private String name;
    private String location;
    private String description;
    private String price;
    private String imageUrl;

    public ProductModel() {}  // Firestore needs empty constructor

    public ProductModel(String name, String location, String description, String price, String imageUrl) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}
