package com.kimikevin.eatsplorer.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.kimikevin.eatsplorer.databinding.FragmentHomeBinding;
import com.kimikevin.eatsplorer.view.adapter.RestaurantAdapter;
import com.kimikevin.eatsplorer.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private RestaurantAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        observeViewModel();

        // Sample fetch - in real app, this would be based on location
        viewModel.fetchNearbyRestaurants(6.5244, 3.3792); 
    }

    private void setupRecyclerView() {
        adapter = new RestaurantAdapter(restaurant -> {
            // Using Safe Args to navigate
            NavDirections action =
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(restaurant.id());
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
        binding.rvRestaurants.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.restaurants.observe(getViewLifecycleOwner(), restaurants -> {
            if (restaurants != null) {
                adapter.setRestaurants(restaurants);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
