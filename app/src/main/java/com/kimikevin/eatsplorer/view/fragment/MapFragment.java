package com.kimikevin.eatsplorer.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimikevin.eatsplorer.R;
import com.kimikevin.eatsplorer.databinding.FragmentMapBinding;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap mMap;
    private HomeViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        viewModel.restaurants.observe(getViewLifecycleOwner(), restaurants -> {
            if (restaurants != null && !restaurants.isEmpty()) {
                mMap.clear();
                for (Restaurant restaurant : restaurants) {
                    LatLng position = new LatLng(restaurant.latitude(), restaurant.longitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(restaurant.name())
                            .snippet(restaurant.category()));
                }
                
                // Optionally move camera to the first restaurant if it's the first load
                Restaurant first = restaurants.get(0);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(first.latitude(), first.longitude()), 12f));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
