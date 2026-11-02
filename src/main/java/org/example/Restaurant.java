package org.example;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")

public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String cuisineType;
    private String address;
    private double minimumOrderAmount;
    private double rating;

    public Restaurant() {}

    public Restaurant(int id, String name, String cuisineType, String address, double minimumOrderAmount,
                      double rating) {
        this.id = id;
        this.name = name;
        this.cuisineType = cuisineType;
        this.address = address;
        this.minimumOrderAmount = minimumOrderAmount;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(double minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
