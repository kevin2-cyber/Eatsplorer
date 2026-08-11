package com.kimikevin.eatsplorer.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.FragmentMapBinding;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap mMap;
    private HomeViewModel viewModel;
    private FusedLocationProviderClient fusedLocationClient;

    private final ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if ((fineGranted != null && fineGranted) || (coarseGranted != null && coarseGranted)) {
                    fetchLocation();
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Build icon here — Maps SDK is fully initialised at this point
        BitmapDescriptor icon = buildMarkerIcon();

        mMap.setOnMarkerClickListener(marker -> {
            Restaurant restaurant = (Restaurant) marker.getTag();
            if (restaurant != null) {
                RestaurantBottomSheetFragment.newInstance(restaurant)
                        .show(getChildFragmentManager(), "restaurant_sheet");
            }
            return true;
        });

        // Draw any restaurants already loaded (e.g. from the Home tab)
        List<Restaurant> existing = viewModel.restaurants.getValue();
        if (existing != null && !existing.isEmpty()) {
            drawMarkers(existing, icon);
        }

        // Observe future restaurant updates (e.g. after fetch completes)
        viewModel.restaurants.observe(getViewLifecycleOwner(), restaurants -> {
            if (restaurants != null && !restaurants.isEmpty() && mMap != null) {
                drawMarkers(restaurants, icon);
            }
        });

        // Fetch current location → animate camera → load nearby restaurants
        if (hasLocationPermission()) {
            fetchLocation();
        } else {
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    private void fetchLocation() {
        if (mMap == null) return;
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException ignored) {}

        fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (!isAdded() || mMap == null) return;
            double lat, lng;
            if (task.isSuccessful() && task.getResult() != null) {
                lat = task.getResult().getLatitude();
                lng = task.getResult().getLongitude();
            } else {
                lat = 6.5244;
                lng = 3.3792;
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13f));
            viewModel.fetchNearbyRestaurants(lat, lng);
        });
    }

    private void drawMarkers(List<Restaurant> restaurants, BitmapDescriptor icon) {
        mMap.clear();
        for (Restaurant restaurant : restaurants) {
            LatLng position = new LatLng(restaurant.latitude(), restaurant.longitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(restaurant.name())
                    .icon(icon));
            if (marker != null) {
                marker.setTag(restaurant);
            }
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
    }

    private BitmapDescriptor buildMarkerIcon() {
        Drawable drawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_restaurant_pin);
        if (drawable == null) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }
        float density = getResources().getDisplayMetrics().density;
        int width = Math.round(32 * density);
        int height = Math.round(44 * density);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mMap = null;
    }
}
