package com.kimikevin.eatsplorer.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.FragmentListRestaurantBinding;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.view.adapter.RestaurantAdapter;
import com.kimikevin.eatsplorer.view.util.PermissionUtils;
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel;

public class RestaurantListFragment extends Fragment
        implements ActivityCompat.OnRequestPermissionsResultCallback{

    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                if ( (fineLocationGranted != null && fineLocationGranted) ||
                        (coarseLocationGranted != null && coarseLocationGranted)) {
                    getDeviceLocation();
                } else {
                    PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getChildFragmentManager(), "dialog");
                }
            }
    );

    private FragmentListRestaurantBinding binding;
    private HomeViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RestaurantAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = binding.swipeRefresh;

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        setupRecyclerView();
        setupObservers();
        swipeRefreshLayout.setOnRefreshListener(this::getDeviceLocation);
        setupListeners();
        enableLocation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_restaurant, container, false);
        return binding.getRoot();
    }

    private void enableLocation() {
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            getDeviceLocation();
        } else {
            requestPermissionLauncher.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    private void getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if(location != null) {
                viewModel.fetchNearbyRestaurants(location.getLatitude(), location.getLongitude());
            } else {
                Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_SHORT).show();
                double sfLat = 5.6545638;
                double sfLng = -0.18484;
                viewModel.fetchNearbyRestaurants(sfLat, sfLng);
            }
        });
    }

    private void setupRecyclerView() {
        // Initialize Adapter with a Click Listener
        // Navigate to Detail Activity
        adapter = new RestaurantAdapter(requireContext(), this::navigateToDetails);

        binding.rvRestaurants.setAdapter(adapter);
        binding.rvRestaurants.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setupListeners() {
        // Handle "Spin the Wheel" Click
        binding.fabSpin.setOnClickListener(v -> viewModel.spinTheWheel());
    }

    private void setupObservers() {
        // 1. Observe the List of Restaurants
        viewModel.restaurants.observe(this, list -> {
            swipeRefreshLayout.setRefreshing(false);
            if (list == null || list.isEmpty()) {
                binding.tvError.setVisibility(VISIBLE);
                binding.tvError.setText("No restaurants found nearby.");
            } else {
                binding.tvError.setVisibility(GONE);
                adapter.submitList(list); // Updates RecyclerView efficiently
            }
        });

        // 2. Observe Errors (Show Toast)
        viewModel.errorMessage.observe(this, message -> {
            if (message != null) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        // 3. Observe the "Spin Winner" (Show Dialog)
        viewModel.spinWinner.observe(this, winner -> {
            if (winner != null) {
                showWinnerDialog(winner);
            }
        });
    }

    private void navigateToDetails(Restaurant restaurant) {
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        // Pass the serialized object to the next screen
        intent.putExtra("RESTAURANT", restaurant);
        startActivity(intent);
    }

    private void showWinnerDialog(Restaurant winner) {
        new AlertDialog.Builder(requireContext())
                .setTitle("🎰 Tonight's Choice!")
                .setMessage("We picked a high-rated spot for you:\n\n" +
                        winner.getName() + "\n" +
                        "Rating: " + winner.getRating() + " ⭐")
                .setIcon(android.R.drawable.ic_dialog_map)
                .setPositiveButton("Let's Go!", (dialog, which) -> navigateToDetails(winner))
                .setNegativeButton("Spin Again", (dialog, which) -> viewModel.spinTheWheel())
                .setNeutralButton("Cancel", null)
                .show();
    }
}