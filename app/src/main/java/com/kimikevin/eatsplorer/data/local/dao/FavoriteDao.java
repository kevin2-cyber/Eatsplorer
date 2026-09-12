package com.kimikevin.eatsplorer.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kimikevin.eatsplorer.data.local.entity.FavoriteRestaurant;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteRestaurant restaurant);

    @Delete
    void delete(FavoriteRestaurant restaurant);

    @Query("SELECT * FROM favorites ORDER BY name ASC")
    LiveData<List<FavoriteRestaurant>> getAll();

    @Query("SELECT COUNT(*) FROM favorites WHERE id = :id")
    int exists(String id);
}
