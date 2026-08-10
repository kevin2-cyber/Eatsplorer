package com.kimikevin.eatsplorer.model.mapper;

import com.kimikevin.eatsplorer.model.entity.NearbySearchResponse;
import com.kimikevin.eatsplorer.model.entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMapper {
    public static List<Restaurant> mapToDomain(List<NearbySearchResponse.PlaceSummary> apiList) {
        List<Restaurant> domainList = new ArrayList<>();

        if (apiList == null) return domainList;

        for (NearbySearchResponse.PlaceSummary item : apiList) {
            String name = (item.getDisplayName() != null) ? item.getDisplayName().text : "Unknown Restaurant";
            String category = (item.getPrimaryTypeDisplayName() != null) ? item.getPrimaryTypeDisplayName().text : "Restaurant";
            String photo = (item.getPhotos() != null && !item.getPhotos().isEmpty()) ? item.getPhotos().get(0).name : null;
            double rating = (item.getRating() != null) ? item.getRating() : 0.0;
            String address = (item.getFormattedAddress() != null) ? item.getFormattedAddress() : "Unknown Address";
            double latitude = (item.getLocation() != null) ? item.getLocation().latitude : 0.0;
            double longitude = (item.getLocation() != null) ? item.getLocation().longitude : 0.0;


            domainList.add(new Restaurant(item.getId(), name, category, photo, rating, address, latitude, longitude));
        }
        return domainList;
    }
}
