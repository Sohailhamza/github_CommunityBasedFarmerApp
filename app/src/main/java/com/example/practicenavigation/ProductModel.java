package com.example.practicenavigation;

public class ProductModel {

    private String docId;
    private String name;
    private String location;
    private String description;
    private String price;
    private String quantity;
    private String unit;
    private String imageUrl;
    private String farmerId;

    public ProductModel() {}  // Firestore needs empty constructor

    public ProductModel(String name, String location, String description,
                        String price, String quantity, String unit,
                        String imageUrl, String farmerId, String docId) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.imageUrl = imageUrl;
        this.farmerId = farmerId;
        this.docId = docId;
    }

    // 🔹 Getter + Setter for docId
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public String getImageUrl() { return imageUrl; }
    public String getFarmerId() { return farmerId; }
}
