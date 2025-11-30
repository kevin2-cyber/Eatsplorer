package com.kimikevin.eatsplorer.model.entity;

import java.util.List;

public class PlaceDetailsResponse {
    public String nationalPhoneNumber;
    public String websiteUri;
    public OpeningHours regularOpeningHours;

    public boolean isOpenNow() {
        return regularOpeningHours != null && regularOpeningHours.openNow;
    }

    public static class OpeningHours {
        public boolean openNow;
        public List<String> weekdayDescriptions;
    }
}
