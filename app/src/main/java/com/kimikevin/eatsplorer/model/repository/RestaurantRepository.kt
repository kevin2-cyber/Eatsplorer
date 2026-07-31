package com.kimikevin.eatsplorer.model.repository

import android.util.Log
import com.kimikevin.eatsplorer.BuildConfig
import com.kimikevin.eatsplorer.model.entity.*
import com.kimikevin.eatsplorer.model.mapper.RestaurantMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantRepository private constructor() {
    private val apiService: PlacesService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(PlacesService::class.java)
    }

    suspend fun searchNearby(lat: Double, lng: Double): Result<List<Restaurant>> {
        return try {
            val requestBody = NearbySearchRequest(lat, lng, 5000.0)
            val response = apiService.searchNearby(API_KEY, PlacesService.LIST_FIELD_MASK, requestBody)
            if (response.isSuccessful) {
                val body = response.body()
                val cleanList = RestaurantMapper.mapToDomain(body?.places)
                Result.success(cleanList)
            } else {
                val errorMsg = "Error: ${response.code()} ${response.message()}"
                Log.e(TAG, errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network Failure: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getPlaceDetails(placeId: String): Result<PlaceDetailsResponse> {
        return try {
            val response = apiService.getPlaceDetails(placeId, API_KEY, PlacesService.DETAILS_FIELD_MASK, "en")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = "Error fetching details: ${response.message()}"
                Log.e(TAG, errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network Failure: ${e.message}")
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "RestaurantRepo"
        private const val BASE_URL = "https://places.googleapis.com/"
        private val API_KEY = BuildConfig.GMP_KEY

        @Volatile
        private var instance: RestaurantRepository? = null

        fun getInstance(): RestaurantRepository {
            return instance ?: synchronized(this) {
                instance ?: RestaurantRepository().also { instance = it }
            }
        }
    }
}
