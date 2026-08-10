package com.kimikevin.eatsplorer.model.entity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlacesService {
    String LIST_FIELD_MASK = "places.id,places.displayName,places.formattedAddress,places.photos,places.primaryTypeDisplayName,places.rating";

    @POST("v1/places:searchNearby")
    Call<NearbySearchResponse> searchNearby(
            @Header("X-Goog-Api-Key") String apiKey,
            @Header("X-Goog-FieldMask") String fieldMask,
            @Body NearbySearchRequest request
    );

    String DETAILS_FIELD_MASK = "nationalPhoneNumber,websiteUri,regularOpeningHours";
    @GET("v1/places/{placeId}")
    Call<PlaceDetailsResponse> getPlaceDetails(
            @Path("placeId") String placeId,
            @Header("X-Goog-Api-Key") String apiKey,
            @Header("X-Goog-FieldMask") String fieldMask,
            @Header("X-Goog-LanguageCode") String languageCode
    );
}
