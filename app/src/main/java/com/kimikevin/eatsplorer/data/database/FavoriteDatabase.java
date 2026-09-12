package com.kimikevin.eatsplorer.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kimikevin.eatsplorer.model.dao.FavoriteDao;
import com.kimikevin.eatsplorer.model.entity.FavoriteRestaurant;

@Database(entities = {FavoriteRestaurant.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();
}
