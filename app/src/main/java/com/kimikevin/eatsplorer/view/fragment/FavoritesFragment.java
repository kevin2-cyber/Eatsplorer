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

import com.kimikevin.eatsplorer.databinding.FragmentFavoritesBinding;
import com.kimikevin.eatsplorer.model.entity.FavoriteRestaurant;
import com.kimikevin.eatsplorer.view.adapter.RestaurantAdapter;
import com.kimikevin.eatsplorer.viewmodel.FavoriteViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FavoriteViewModel favoriteViewModel;
    private RestaurantAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteViewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RestaurantAdapter(
                restaurant -> {
                    NavDirections action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(restaurant);
                    Navigation.findNavController(binding.getRoot()).navigate(action);
                },
                restaurant -> favoriteViewModel.toggleFavorite(restaurant)
        );
        binding.rvFavorites.setAdapter(adapter);

        favoriteViewModel.favorites.observe(getViewLifecycleOwner(), this::onFavoritesChanged);
    }

    private void onFavoritesChanged(List<FavoriteRestaurant> favorites) {
        if (favorites == null || favorites.isEmpty()) {
            binding.rvFavorites.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        binding.rvFavorites.setVisibility(View.VISIBLE);
        binding.tvEmpty.setVisibility(View.GONE);

        Set<String> ids = favorites.stream().map(f -> f.id).collect(Collectors.toSet());
        adapter.setFavoriteIds(ids);
        adapter.setRestaurants(favorites.stream()
                .map(FavoriteRestaurant::toRestaurant)
                .collect(Collectors.toList()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
