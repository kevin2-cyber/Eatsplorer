package com.kimikevin.eatsplorer.model.entity;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private String category;
    private String photoRef;
    private double rating;
    private String address;

    public Restaurant(String id, String name, String category, String photoRef, double rating, String address) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.photoRef = photoRef;
        this.rating = rating;
        this.address = address;
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
}
