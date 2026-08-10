package com.kimikevin.eatsplorer.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceDetailsResponse {
    @SerializedName("nationalPhoneNumber")
    private String nationalPhoneNumber;

    @SerializedName("websiteUri")
    private String websiteUri;

    @SerializedName("regularOpeningHours")
    private OpeningHours regularOpeningHours;

    public String getNationalPhoneNumber() {
        return nationalPhoneNumber;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public OpeningHours getRegularOpeningHours() {
        return regularOpeningHours;
    }

    public boolean isOpenNow() {
        return regularOpeningHours != null && regularOpeningHours.openNow;
    }

    public static class OpeningHours {
        @SerializedName("openNow")
        public boolean openNow;
        @SerializedName("weekdayDescriptions")
        public List<String> weekdayDescriptions;
    }
}
