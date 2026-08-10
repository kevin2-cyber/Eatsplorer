package com.kimikevin.eatsplorer.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.kimikevin.eatsplorer.BuildConfig;
import com.kimikevin.eatsplorer.model.entity.NearbySearchRequest;
import com.kimikevin.eatsplorer.model.entity.NearbySearchResponse;
import com.kimikevin.eatsplorer.model.entity.PlaceDetailsResponse;
import com.kimikevin.eatsplorer.model.entity.PlacesService;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.model.mapper.RestaurantMapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class that abstracts the data source (Google Places API).
 */
public class RestaurantRepository {
    private static final String TAG = "RestaurantRepo";
    private static final String API_KEY = BuildConfig.GOOGLE_MAPS_API_KEY;
    
    private final PlacesService apiService;
    private static RestaurantRepository instance;

    private RestaurantRepository() {
        apiService = RetrofitClient.getClient().create(PlacesService.class);
    }

    public static synchronized RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    /**
     * Search for nearby restaurants based on latitude and longitude.
     */
    public void searchNearby(double lat, double lng, MutableLiveData<List<Restaurant>> liveData, MutableLiveData<String> errorData) {
        NearbySearchRequest requestBody = new NearbySearchRequest(lat, lng, 5000);
        apiService.searchNearby(API_KEY, PlacesService.LIST_FIELD_MASK, requestBody)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<NearbySearchResponse> call, @NonNull Response<NearbySearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Restaurant> cleanList = RestaurantMapper.mapToDomain(response.body().getPlaces());
                            liveData.postValue(cleanList);
                        } else {
                            handleError(response.code(), response.message(), errorData);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NearbySearchResponse> call, @NonNull Throwable t) {
                        handleFailure(t, errorData);
                    }
                });
    }

    /**
     * Fetch detailed information for a specific restaurant.
     */
    public void getPlaceDetails(String placeId, MutableLiveData<PlaceDetailsResponse> liveData, MutableLiveData<String> errorData) {
        apiService.getPlaceDetails(placeId, API_KEY, PlacesService.DETAILS_FIELD_MASK, "en")
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<PlaceDetailsResponse> call, @NonNull Response<PlaceDetailsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            liveData.postValue(response.body());
                        } else {
                            handleError(response.code(), response.message(), errorData);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PlaceDetailsResponse> call, @NonNull Throwable t) {
                        handleFailure(t, errorData);
                    }
                });
    }

    private void handleError(int code, String message, MutableLiveData<String> errorData) {
        String errorMessage = "API Error (" + code + "): " + message;
        Log.e(TAG, errorMessage);
        errorData.postValue(errorMessage);
    }

    private void handleFailure(Throwable t, MutableLiveData<String> errorData) {
        String failureMessage = "Network Failure: " + t.getMessage();
        Log.e(TAG, failureMessage, t);
        errorData.postValue(failureMessage);
    }
}
