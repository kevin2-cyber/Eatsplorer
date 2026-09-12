package com.kimikevin.eatsplorer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kimikevin.eatsplorer.model.entity.FavoriteRestaurant;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.model.repository.FavoriteRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoriteViewModel extends ViewModel {

    private final FavoriteRepository repository;
    public final LiveData<List<FavoriteRestaurant>> favorites;

    @Inject
    public FavoriteViewModel(FavoriteRepository repository) {
        this.repository = repository;
        favorites = repository.getAllFavorites();
    }

    public void toggleFavorite(Restaurant restaurant) {
        repository.isFavorite(restaurant.id(), isFav -> {
            if (isFav) {
                repository.removeFavorite(restaurant);
            } else {
                repository.addFavorite(restaurant);
            }
        });
    }
}
