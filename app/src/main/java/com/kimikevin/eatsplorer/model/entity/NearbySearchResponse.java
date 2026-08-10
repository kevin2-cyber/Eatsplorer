package com.kimikevin.eatsplorer.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearbySearchResponse {
    @SerializedName("places")
    private List<PlaceSummary> places;

    public List<PlaceSummary> getPlaces() {
        return places;
    }

    public static class PlaceSummary {
        @SerializedName("id")
        private String id;

        @SerializedName("displayName")
        private DisplayName displayName;

        @SerializedName("formattedAddress")
        private String formattedAddress;

        @SerializedName("location")
        private Location location;

        @SerializedName("primaryTypeDisplayName")
        private DisplayName primaryTypeDisplayName;

        @SerializedName("photos")
        private List<Photo> photos;

        @SerializedName("rating")
        private Double rating;

        public String getId() {
            return id;
        }

        public DisplayName getDisplayName() {
            return displayName;
        }

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public Location getLocation() {
            return location;
        }

        public DisplayName getPrimaryTypeDisplayName() {
            return primaryTypeDisplayName;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public Double getRating() {
            return rating;
        }

        public String getName() {
            return (displayName != null) ? displayName.text : "Unknown Restaurant";
        }

        public String getCategory() {
            return (primaryTypeDisplayName != null) ? primaryTypeDisplayName.text : "Restaurant";
        }

        public String getRatingText() {
            return (rating != null) ? String.valueOf(rating) : "N/A";
        }

        public String getFirstPhotoId() {
            if (photos != null && !photos.isEmpty()) {
                return photos.get(0).name;
            }
            return null;
        }

        public static class DisplayName {
            @SerializedName("text")
            public String text;
        }

        public static class Photo {
            @SerializedName("name")
            public String name;
            @SerializedName("widthPx")
            public int widthPx;
            @SerializedName("heightPx")
            public int heightPx;
        }

        public static class Location {
            @SerializedName("latitude")
            public double latitude;
            @SerializedName("longitude")
            public double longitude;
        }
    }
}
