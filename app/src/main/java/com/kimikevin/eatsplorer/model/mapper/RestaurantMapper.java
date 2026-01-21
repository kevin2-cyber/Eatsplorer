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
            String name = (item.displayName != null) ? item.displayName.text : "Unknown Restaurant";
            String category = (item.primaryTypeDisplayName != null) ? item.primaryTypeDisplayName.text : "Restaurant";
            String photo = (item.photos != null && !item.photos.isEmpty()) ? item.photos.get(0).name : null;
            double rating = (item.rating != null) ? item.rating : 0.0;
            String address = (item.formattedAddress != null) ? item.formattedAddress : "Unknown Address";
            double latitude = (item.location != null) ? item.location.latitude : 0.0;
            double longitude = (item.location != null) ? item.location.longitude : 0.0;


            domainList.add(new Restaurant(item.id, name, category, photo, rating, address, latitude, longitude));
        }
        return domainList;
    }
}
