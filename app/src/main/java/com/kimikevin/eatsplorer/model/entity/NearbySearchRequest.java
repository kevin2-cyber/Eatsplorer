package com.kimikevin.eatsplorer.model.entity;

import com.google.gson.annotations.SerializedName;

public class NearbySearchRequest {
    @SerializedName("includedTypes")
    private String[] includedTypes;
    @SerializedName("maxResultCount")
    private int maxResultCount;
    private LocationRestriction locationRestriction;

    public NearbySearchRequest(double latitude, double longitude, double radiusMeters) {
        this.includedTypes = new String[] {"restaurant, food"};
        this.maxResultCount = 20;
        this.locationRestriction = new LocationRestriction(
                new Circle(new Center(latitude, longitude), radiusMeters)
        );
    }

    private static class LocationRestriction {
        @SerializedName("circle")
        private Circle circle;

        public LocationRestriction(Circle circle) {
            this.circle = circle;
        }
    }

    private static class Circle {
        @SerializedName("center")
        private Center center;
        @SerializedName("radius")
        private double radius;

        public Circle(Center center, double radius) {
            this.center = center;
            this.radius = radius;
        }
    }

    private static class Center {
        @SerializedName("latitude")
        private double latitude;
        @SerializedName("longitude")
        private double longitude;

        public Center(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
