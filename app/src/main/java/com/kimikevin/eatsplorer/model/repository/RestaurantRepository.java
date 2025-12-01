package com.kimikevin.eatsplorer.model.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kimikevin.eatsplorer.BuildConfig;
import com.kimikevin.eatsplorer.model.entity.NearbySearchRequest;
import com.kimikevin.eatsplorer.model.entity.NearbySearchResponse;
import com.kimikevin.eatsplorer.model.entity.PlaceDetailsResponse;
import com.kimikevin.eatsplorer.model.entity.PlacesService;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.model.mapper.RestaurantMapper;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantRepository {
    private static final String TAG = "RestaurantRepo";
    private static final String BASE_URL = "https://places.googleapis.com/";

    private static final String API_KEY = BuildConfig.GMP_KEY;
    private final PlacesService apiService;
    private static RestaurantRepository instance;

    private RestaurantRepository() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(PlacesService.class);
    }

    public static synchronized RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    public void searchNearby(double lat, double lng, MutableLiveData<List<Restaurant>> liveData, MutableLiveData<String> errorData) {
        NearbySearchRequest requestBody = new NearbySearchRequest(lat, lng, 5000);
        apiService.searchNearby(API_KEY, PlacesService.LIST_FIELD_MASK, requestBody)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Restaurant> cleanList = RestaurantMapper.mapToDomain(response.body().places);
                            liveData.postValue(cleanList);
                        } else {
                            String errorMessage = "Error: " + response.code() + " " + response.message();
                            Log.e(TAG, errorMessage);
                            errorData.postValue(errorMessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<NearbySearchResponse> call, Throwable t) {
                        Log.e(TAG, "Network Failure: " + t.getMessage());
                        errorData.postValue("Network Failure: " + t.getMessage());
                    }
                });
    }

    public void getPlaceDetails(String placeId, MutableLiveData<PlaceDetailsResponse> liveData, MutableLiveData<String> errorData) {
        apiService.getPlaceDetails(placeId, API_KEY, PlacesService.DETAILS_FIELD_MASK, "en")
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            liveData.postValue(response.body());
                        } else {
                            errorData.postValue("Error fetching details: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                        errorData.postValue("Network Failure: " + t.getMessage());
                        Log.e(TAG, "Network Failure: " + t.getMessage());
                    }
                });
    }
}
