package com.kimikevin.eatsplorer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kimikevin.eatsplorer.model.entity.FavoriteRestaurant;
import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.model.repository.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final FavoriteRepository repository;
    public final LiveData<List<FavoriteRestaurant>> favorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
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
