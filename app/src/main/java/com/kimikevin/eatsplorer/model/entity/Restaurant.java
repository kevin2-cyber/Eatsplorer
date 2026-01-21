package com.kimikevin.eatsplorer.model.entity;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private String category;
    private String photoRef;
    private double rating;
    private String address;
    private double latitude;
    private double longitude;

    public Restaurant(String id, String name, String category, String photoRef, double rating, String address, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.photoRef = photoRef;
        this.rating = rating;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public double getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
