package com.kimikevin.eatsplorer.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kimikevin.eatsplorer.data.local.dao.FavoriteDao;
import com.kimikevin.eatsplorer.data.local.entity.FavoriteRestaurant;

@Database(entities = {FavoriteRestaurant.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();
}
