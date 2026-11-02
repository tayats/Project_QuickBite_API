package org.example;

public class OrderRequest {

    private int restaurantId;
    private String customerName;
    private String deliveryAddress;
    private String itemsSummary;
    private double subtotal;

    public OrderRequest() {}

    public OrderRequest(int restaurantId, String customerName, String deliveryAddress, String itemsSummary, double subtotal) {
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.itemsSummary = itemsSummary;
        this.subtotal = subtotal;

    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getItemsSummary() {
        return itemsSummary;
    }

    public void setItemsSummary(String itemsSummary) {
        this.itemsSummary = itemsSummary;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
