package com.kimikevin.eatsplorer.model.mapper

import com.kimikevin.eatsplorer.model.entity.NearbySearchResponse
import com.kimikevin.eatsplorer.model.entity.Restaurant

object RestaurantMapper {
    fun mapToDomain(apiList: List<NearbySearchResponse.PlaceSummary>?): List<Restaurant> {
        return apiList?.map { item ->
            Restaurant(
                id = item.id,
                name = item.displayName?.text ?: "Unknown Restaurant",
                category = item.primaryTypeDisplayName?.text ?: "Restaurant",
                photoRef = item.photos?.firstOrNull()?.name,
                rating = item.rating ?: 0.0,
                address = item.formattedAddress ?: "Unknown Address",
                latitude = item.location?.latitude ?: 0.0,
                longitude = item.location?.longitude ?: 0.0
            )
        } ?: emptyList()
    }
}
