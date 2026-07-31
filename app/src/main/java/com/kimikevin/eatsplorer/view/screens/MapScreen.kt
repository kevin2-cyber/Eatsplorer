package com.kimikevin.eatsplorer.view.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.kimikevin.eatsplorer.model.entity.Restaurant
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel

@Composable
fun MapScreen(
    viewModel: HomeViewModel,
    onRestaurantClick: (Restaurant) -> Unit
) {
    val restaurants by viewModel.restaurants.observeAsState(emptyList())
    
    // Default to San Francisco or a known location if needed, 
    // but ideally we'd use the last known location from the ViewModel or a location provider.
    val defaultLocation = LatLng(5.6545638, -0.18484) 
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true), // Needs permission handling
        uiSettings = MapUiSettings(myLocationButtonEnabled = true)
    ) {
        restaurants.forEach { restaurant ->
            val markerState = rememberMarkerState(key = restaurant.id, position = LatLng(restaurant.latitude, restaurant.longitude))
            Marker(
                state = markerState,
                title = restaurant.name,
                snippet = restaurant.category,
                onInfoWindowClick = { onRestaurantClick(restaurant) }
            )
        }
    }
}
