package com.example.coffeeshopapp.model;

public class CartItem {
    private int productId;
    private String name;
    private double cost;
    private String imagePath;
    private int quantity;

    public CartItem(int productId, String name, double cost, String imagePath, int quantity) {
        this.productId = productId;
        this.name = name;
        this.cost = cost;
        this.imagePath = imagePath;
        this.quantity = quantity;

    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
