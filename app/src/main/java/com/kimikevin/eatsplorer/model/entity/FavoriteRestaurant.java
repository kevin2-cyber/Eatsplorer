package com.kimikevin.eatsplorer.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteRestaurant {

    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String category;
    public String photoRef;
    public double rating;
    public String address;
    public double latitude;
    public double longitude;

    public static FavoriteRestaurant fromRestaurant(Restaurant r) {
        FavoriteRestaurant fav = new FavoriteRestaurant();
        fav.id = r.id();
        fav.name = r.name();
        fav.category = r.category();
        fav.photoRef = r.photoRef();
        fav.rating = r.rating();
        fav.address = r.address();
        fav.latitude = r.latitude();
        fav.longitude = r.longitude();
        return fav;
    }

    public Restaurant toRestaurant() {
        return new Restaurant(id, name, category, photoRef, rating, address, latitude, longitude);
    }
}
