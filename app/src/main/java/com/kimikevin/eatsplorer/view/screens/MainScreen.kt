package com.kimikevin.eatsplorer.view.screens

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.google.android.gms.location.LocationServices
import com.kimikevin.eatsplorer.viewmodel.DetailViewModel
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

sealed class Screen(val route: String, val title: String, val icon: @Composable () -> Unit) {
    object List : Screen("list", "List", { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) })
    object Map : Screen("map", "Map", { Icon(Icons.Default.Map, contentDescription = null) })
}

@SuppressLint("MissingPermission")
@Composable
fun MainScreen(
    homeViewModel: HomeViewModel = viewModel(),
    detailViewModel: DetailViewModel = viewModel(),
    onboardingComplete: Boolean,
    onOnboardingFinished: () -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var locationPermissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        locationPermissionGranted = permissions.values.all { it }
        if (locationPermissionGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    homeViewModel.fetchNearbyRestaurants(location.latitude, location.longitude)
                } else {
                    // Fallback to fresh location request if lastLocation is null
                    fusedLocationClient.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        CancellationTokenSource().token
                    ).addOnSuccessListener { freshLocation ->
                        freshLocation?.let {
                            homeViewModel.fetchNearbyRestaurants(it.latitude, it.longitude)
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(onboardingComplete) {
        if (onboardingComplete) {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    if (!onboardingComplete) {
        OnboardingScreen(onFinished = onOnboardingFinished)
    } else {
        Scaffold(
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                // Only show bottom bar on top-level screens
                if (currentDestination?.route in listOf(Screen.List.route, Screen.Map.route)) {
                    NavigationBar {
                        val items = listOf(Screen.List, Screen.Map)
                        items.forEach { screen ->
                            NavigationBarItem(
                                icon = screen.icon,
                                label = { Text(screen.title) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.List.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.List.route) {
                    RestaurantListScreen(
                        viewModel = homeViewModel,
                        onRestaurantClick = { restaurant ->
                            navController.navigate("detail/${restaurant.id}")
                        }
                    )
                }
                composable(Screen.Map.route) {
                    MapScreen(
                        viewModel = homeViewModel,
                        onRestaurantClick = { restaurant ->
                            navController.navigate("detail/${restaurant.id}")
                        }
                    )
                }
                composable("detail/{restaurantId}") { backStackEntry ->
                    val restaurantId = backStackEntry.arguments?.getString("restaurantId")
                    val restaurants by homeViewModel.restaurants.collectAsStateWithLifecycle()
                    val restaurant = restaurants.find { it.id == restaurantId }
                    
                    if (restaurant != null) {
                        DetailScreen(
                            restaurant = restaurant,
                            viewModel = detailViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
