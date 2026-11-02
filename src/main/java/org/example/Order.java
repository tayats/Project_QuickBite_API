package org.example;

import javax.persistence.*;

@Entity
@Table(name = "customer_orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String customerName;
    private String deliveryAddress;
    private String itemsSummary;
    private double subtotal;
    private double deliveryFee;
    private double totalAmount;
    private String status;

    public Order() {}

    public Order(int id, Restaurant restaurant, String customerName, String deliveryAddress, String itemsSummary,
                 double subtotal, double deliveryFee, double totalAmount, String status) {
        this.id = id;
        this.restaurant = restaurant;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.itemsSummary = itemsSummary;
        this.subtotal = subtotal;
        this.deliveryFee = deliveryFee;
        this.totalAmount = totalAmount;
        this.status = status;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
