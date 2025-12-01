package com.kimikevin.eatsplorer.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.kimikevin.eatsplorer.PermissionUtils;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.ActivityHomeBinding;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.view.adapter.RestaurantAdapter;
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private HomeViewModel viewModel;
    private ActivityHomeBinding binding;
    private RestaurantAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int REQUEST_LOCATION_PERMISSION = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setLifecycleOwner(this);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        setupRecyclerView();
        setupObservers();
        setupListeners();

        enableLocation();

    }

    private void enableLocation() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            getDeviceLocation();
        } else {
            PermissionUtils.requestLocationPermissions(this, REQUEST_LOCATION_PERMISSION, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != REQUEST_LOCATION_PERMISSION) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            getDeviceLocation();
        } else {
            PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        binding.progressBar.setVisibility(VISIBLE);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if(location != null) {
                viewModel.fetchNearbyRestaurants(location.getLatitude(), location.getLongitude());
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
                double sfLat = 5.6545638;
                double sfLng = -0.18484;
                viewModel.fetchNearbyRestaurants(sfLat, sfLng);
            }
        });
    }

    private void setupRecyclerView() {
        // Initialize Adapter with a Click Listener
        // Navigate to Detail Activity
        adapter = new RestaurantAdapter(this, this::navigateToDetails);

        binding.rvRestaurants.setAdapter(adapter);
        binding.rvRestaurants.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupListeners() {
        // Handle "Spin the Wheel" Click
        binding.fabSpin.setOnClickListener(v -> {
            viewModel.spinTheWheel();
        });
    }

    private void setupObservers() {
        // 1. Observe the List of Restaurants
        viewModel.restaurants.observe(this, list -> {
            binding.progressBar.setVisibility(GONE);
            if (list == null || list.isEmpty()) {
                binding.tvError.setVisibility(VISIBLE);
                binding.tvError.setText("No restaurants found nearby.");
            } else {
                binding.tvError.setVisibility(GONE);
                adapter.submitList(list); // Updates RecyclerView efficiently
            }
        });

        // 2. Observe Loading State (Show/Hide Spinner)
        viewModel.isLoading.observe(this, isLoading -> binding.progressBar.setVisibility(isLoading ? VISIBLE : GONE));

        // 3. Observe Errors (Show Toast)
        viewModel.errorMessage.observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });

        // 4. Observe the "Spin Winner" (Show Dialog)
        viewModel.spinWinner.observe(this, winner -> {
            if (winner != null) {
                showWinnerDialog(winner);
            }
        });
    }

    private void navigateToDetails(Restaurant restaurant) {
        Intent intent = new Intent(this, DetailActivity.class);
        // Pass the serialized object to the next screen
        intent.putExtra("RESTAURANT", restaurant);
        startActivity(intent);
    }

    private void showWinnerDialog(Restaurant winner) {
        new AlertDialog.Builder(this)
                .setTitle("🎰 Tonight's Choice!")
                .setMessage("We picked a high-rated spot for you:\n\n" +
                        winner.getName() + "\n" +
                        "Rating: " + winner.getRating() + " ⭐")
                .setIcon(android.R.drawable.ic_dialog_map)
                .setPositiveButton("Let's Go!", (dialog, which) -> {
                    navigateToDetails(winner);
                })
                .setNegativeButton("Spin Again", (dialog, which) -> {
                    viewModel.spinTheWheel();
                })
                .setNeutralButton("Cancel", null)
                .show();
    }
}