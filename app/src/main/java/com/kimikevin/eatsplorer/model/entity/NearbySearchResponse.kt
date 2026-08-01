package com.kimikevin.eatsplorer.model.entity

import com.google.gson.annotations.SerializedName

data class NearbySearchResponse(
    @SerializedName("places")
    val places: List<PlaceSummary>?
) {
    data class PlaceSummary(
        val id: String,
        val displayName: DisplayName?,
        val formattedAddress: String?,
        val location: Location?,
        val primaryTypeDisplayName: DisplayName?,
        val photos: List<Photo>?,
        val rating: Double?
    ) {
        val name: String get() = displayName?.text ?: "Unknown Restaurant"
        val category: String get() = primaryTypeDisplayName?.text ?: "Restaurant"
        val ratingText: String get() = rating?.toString() ?: "N/A"
        val firstPhotoId: String? get() = photos?.firstOrNull()?.name

        data class DisplayName(
            val text: String
        )

        data class Photo(
            val name: String,
            val widthPx: Int,
            val heightPx: Int
        )

        data class Location(
            val latitude: Double,
            val longitude: Double
        )
    }
}
