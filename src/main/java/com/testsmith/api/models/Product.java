package com.testsmith.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private String id;
    private String name;
    private String description;
    private double price;
    @JsonProperty("is_location_offer")
    private boolean isLocationOffer;
    @JsonProperty("is_rental")
    private boolean isRental;
    @JsonProperty("in_stock")
    private boolean inStock;
    @JsonProperty("co2_rating")
    private String co2Rating;
    @JsonProperty("is_eco_friendly")
    private boolean isEcoFriendly;
    private Brand brand;
    private Category category;
    @JsonProperty("product_image")
    private Object productImage;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isLocationOffer() {
        return isLocationOffer;
    }

    public void setLocationOffer(boolean locationOffer) {
        isLocationOffer = locationOffer;
    }

    public boolean isRental() {
        return isRental;
    }

    public void setRental(boolean rental) {
        isRental = rental;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getCo2Rating() {
        return co2Rating;
    }

    public void setCo2Rating(String co2Rating) {
        this.co2Rating = co2Rating;
    }

    public boolean isEcoFriendly() {
        return isEcoFriendly;
    }

    public void setEcoFriendly(boolean ecoFriendly) {
        isEcoFriendly = ecoFriendly;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
