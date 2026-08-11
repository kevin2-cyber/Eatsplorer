package com.kimikevin.eatsplorer.model.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kimikevin.eatsplorer.AppDatabase;
import com.kimikevin.eatsplorer.model.dao.FavoriteDao;
import com.kimikevin.eatsplorer.model.entity.FavoriteRestaurant;
import com.kimikevin.eatsplorer.model.entity.Restaurant;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteRepository {

    private final FavoriteDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public FavoriteRepository(Context context) {
        dao = AppDatabase.getInstance(context).favoriteDao();
    }

    public LiveData<List<FavoriteRestaurant>> getAllFavorites() {
        return dao.getAll();
    }

    public void addFavorite(Restaurant restaurant) {
        executor.execute(() -> dao.insert(FavoriteRestaurant.fromRestaurant(restaurant)));
    }

    public void removeFavorite(Restaurant restaurant) {
        executor.execute(() -> dao.delete(FavoriteRestaurant.fromRestaurant(restaurant)));
    }

    public void isFavorite(String id, IsFavoriteCallback callback) {
        executor.execute(() -> callback.onResult(dao.exists(id) > 0));
    }

    public interface IsFavoriteCallback {
        void onResult(boolean isFavorite);
    }
}
