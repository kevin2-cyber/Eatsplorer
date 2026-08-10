package com.kimikevin.eatsplorer.model.entity;

import com.google.gson.annotations.SerializedName;

public class NearbySearchRequest {
    @SerializedName("includedTypes")
    private String[] includedTypes;
    @SerializedName("maxResultCount")
    private int maxResultCount;
    private LocationRestriction locationRestriction;

    public NearbySearchRequest(double latitude, double longitude, double radiusMeters) {
        this.includedTypes = new String[] {"restaurant"};
        this.maxResultCount = 20;
        this.locationRestriction = new LocationRestriction(
                new Circle(new Center(latitude, longitude), radiusMeters)
        );
    }

    private record LocationRestriction(@SerializedName("circle") Circle circle) {
    }

    private record Circle(@SerializedName("center") Center center,
                          @SerializedName("radius") double radius) {
    }

    private record Center(@SerializedName("latitude") double latitude,
                          @SerializedName("longitude") double longitude) {
    }
}
