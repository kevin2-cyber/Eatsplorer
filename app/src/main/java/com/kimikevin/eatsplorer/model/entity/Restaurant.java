package com.kimikevin.eatsplorer.model.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Domain model representing a Restaurant.
 */
public record Restaurant(String id, String name, String category, String photoRef, double rating,
                         String address, double latitude,
                         double longitude) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @NonNull
    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                '}';
    }
}
