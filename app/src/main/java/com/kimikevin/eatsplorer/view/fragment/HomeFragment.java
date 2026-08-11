package com.kimikevin.eatsplorer.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.kimikevin.eatsplorer.databinding.FragmentHomeBinding;
import com.kimikevin.eatsplorer.model.entity.FavoriteRestaurant;
import com.kimikevin.eatsplorer.view.adapter.RestaurantAdapter;
import com.kimikevin.eatsplorer.view.utils.PermissionUtils;
import com.kimikevin.eatsplorer.viewmodel.FavoriteViewModel;
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private FavoriteViewModel favoriteViewModel;
    private RestaurantAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;

    private final ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if ((fineGranted != null && fineGranted) || (coarseGranted != null && coarseGranted)) {
                    getCurrentLocationAndFetch();
                } else {
                    PermissionUtils.PermissionDeniedDialog.newInstance(false)
                            .show(getChildFragmentManager(), "location_denied");
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        favoriteViewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupSwipeRefresh();
        observeViewModel();
        checkLocationPermissionAndFetch();
    }

    private void setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(this::checkLocationPermissionAndFetch);
    }

    private void setupRecyclerView() {
        adapter = new RestaurantAdapter(
                restaurant -> {
                    NavDirections action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(restaurant);
                    Navigation.findNavController(binding.getRoot()).navigate(action);
                },
                restaurant -> favoriteViewModel.toggleFavorite(restaurant)
        );
        binding.rvRestaurants.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.restaurants.observe(getViewLifecycleOwner(), restaurants -> {
            if (restaurants != null) adapter.setRestaurants(restaurants);
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading ->
                binding.swipeRefresh.setRefreshing(isLoading != null && isLoading));

        favoriteViewModel.favorites.observe(getViewLifecycleOwner(), this::updateFavoriteIds);
    }

    private void updateFavoriteIds(List<FavoriteRestaurant> favorites) {
        Set<String> ids = new HashSet<>();
        if (favorites != null) {
            for (FavoriteRestaurant fav : favorites) ids.add(fav.id);
        }
        adapter.setFavoriteIds(ids);
    }

    private void checkLocationPermissionAndFetch() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocationAndFetch();
        } else {
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    private void getCurrentLocationAndFetch() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                viewModel.fetchNearbyRestaurants(location.getLatitude(), location.getLongitude());
            } else {
                viewModel.fetchNearbyRestaurants(6.5244, 3.3792);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
