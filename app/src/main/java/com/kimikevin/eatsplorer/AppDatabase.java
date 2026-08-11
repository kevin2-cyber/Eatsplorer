package com.kimikevin.eatsplorer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kimikevin.eatsplorer.model.dao.FavoriteDao;
import com.kimikevin.eatsplorer.model.entity.FavoriteRestaurant;

@Database(entities = {FavoriteRestaurant.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "eatsplorer.db"
                    ).build();
                }
            }
        }
        return instance;
    }
}
