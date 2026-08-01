package com.kimikevin.eatsplorer.model.entity

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PlacesService {
    companion object {
        const val LIST_FIELD_MASK = "places.id,places.displayName,places.formattedAddress,places.photos,places.primaryTypeDisplayName,places.rating,places.location"
        const val DETAILS_FIELD_MASK = "nationalPhoneNumber,websiteUri,regularOpeningHours"
    }

    @POST("v1/places:searchNearby")
    suspend fun searchNearby(
        @Header("X-Goog-Api-Key") apiKey: String,
        @Header("X-Goog-FieldMask") fieldMask: String,
        @Body request: NearbySearchRequest
    ): Response<NearbySearchResponse>

    @GET("v1/places/{placeId}")
    suspend fun getPlaceDetails(
        @Path("placeId") placeId: String,
        @Header("X-Goog-Api-Key") apiKey: String,
        @Header("X-Goog-FieldMask") fieldMask: String,
        @Header("X-Goog-LanguageCode") languageCode: String
    ): Response<PlaceDetailsResponse>
}
