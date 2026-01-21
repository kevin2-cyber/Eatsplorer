package com.kimikevin.eatsplorer.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearbySearchResponse {
    @SerializedName("places")
    public List<PlaceSummary> places;

    public static class PlaceSummary {
        public String id;
        public DisplayName displayName;
        public String formattedAddress;
        public Location location;
        public DisplayName primaryTypeDisplayName;
        public List<Photo> photos;
        public Double rating;
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
            public String text;
        }

        public static class Photo {
            public String name;
            public int widthPx;
            public int heightPx;
        }

        public static class Location {
            public double latitude;
            public double longitude;
        }
    }
}
