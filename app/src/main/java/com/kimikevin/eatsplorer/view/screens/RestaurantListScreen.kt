package com.kimikevin.eatsplorer.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.kimikevin.eatsplorer.BuildConfig
import com.kimikevin.eatsplorer.R
import com.kimikevin.eatsplorer.model.entity.Restaurant
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantListScreen(
    viewModel: HomeViewModel,
    onRestaurantClick: (Restaurant) -> Unit
) {
    val restaurants by viewModel.restaurants.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val spinWinner by viewModel.spinWinner.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val pullToRefreshState = rememberPullToRefreshState()

    var showWinnerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(spinWinner) {
        if (spinWinner != null) {
            showWinnerDialog = true
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.spinTheWheel() }) {
                Icon(Icons.Default.Casino, contentDescription = "Spin the wheel")
            }
        }
    ) { padding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = pullToRefreshState,
            isRefreshing = isLoading,
            onRefresh = {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        viewModel.fetchNearbyRestaurants(it.latitude, it.longitude)
                    }
                }
            }
        ) {
            if (restaurants.isEmpty()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    Text(
                        text = stringResource(R.string.no_restaurants),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(restaurants, key = { it.id }) { restaurant ->
                        RestaurantItem(restaurant, onRestaurantClick)
                    }
                }
            }

            if (showWinnerDialog && spinWinner != null) {
                AlertDialog(
                    onDismissRequest = {
                        showWinnerDialog = false
                        viewModel.clearSpinWinner()
                    },
                    title = { Text("🎰 Tonight's Choice!") },
                    text = {
                        Text("We picked a high-rated spot for you:\n\n${spinWinner!!.name}\nRating: ${spinWinner!!.rating} ⭐")
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showWinnerDialog = false
                            onRestaurantClick(spinWinner!!)
                            viewModel.clearSpinWinner()
                        }) {
                            Text("Let's Go!")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            viewModel.spinTheWheel()
                        }) {
                            Text("Spin Again")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    onClick: (Restaurant) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(restaurant) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            if (restaurant.photoRef != null) {
                val imageUrl = "https://places.googleapis.com/v1/${restaurant.photoRef}/media?maxHeightPx=400&maxWidthPx=400&key=${BuildConfig.GMP_KEY}"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = restaurant.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${restaurant.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = restaurant.category,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.small)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
