package org.example;

public class MenuItemRequest {

    private int restaurantId;
    private String name;
    private String category;
    private double price;
    private boolean available = true;

    public MenuItemRequest() {}

    public MenuItemRequest(int restaurantId, String name, String category, double price, boolean available) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
