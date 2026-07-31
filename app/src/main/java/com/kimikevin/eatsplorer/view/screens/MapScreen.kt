package com.kimikevin.eatsplorer.view.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*
import com.kimikevin.eatsplorer.model.entity.Restaurant
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel

@Composable
fun MapScreen(
    viewModel: HomeViewModel,
    onRestaurantClick: (Restaurant) -> Unit
) {
    val restaurants by viewModel.restaurants.collectAsStateWithLifecycle()
    
    // Default location if no restaurants are found
    val defaultLocation = LatLng(5.6545638, -0.18484) 
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }

    // Auto-zoom to fit all restaurants when they are loaded
    LaunchedEffect(restaurants) {
        if (restaurants.isNotEmpty()) {
            val builder = LatLngBounds.builder()
            restaurants.forEach { 
                builder.include(LatLng(it.latitude, it.longitude))
            }
            val bounds = builder.build()
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 100),
                durationMs = 1000
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true),
        uiSettings = MapUiSettings(myLocationButtonEnabled = true)
    ) {
        restaurants.forEach { restaurant ->
            key(restaurant.id) {
                val markerState = rememberUpdatedMarkerState(
                    position = LatLng(restaurant.latitude, restaurant.longitude)
                )
                Marker(
                    state = markerState,
                    title = restaurant.name,
                    snippet = restaurant.category,
                    onInfoWindowClick = { onRestaurantClick(restaurant) }
                )
            }
        }
    }
}
