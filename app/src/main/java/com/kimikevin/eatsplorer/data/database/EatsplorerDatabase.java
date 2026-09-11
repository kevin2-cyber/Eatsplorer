package com.kimikevin.eatsplorer.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kimikevin.eatsplorer.model.dao.FavoriteDao;
import com.kimikevin.eatsplorer.model.entity.FavoriteRestaurant;

@Database(entities = {FavoriteRestaurant.class}, version = 1, exportSchema = false)
public abstract class EatsplorerDatabase extends RoomDatabase {

    private static volatile EatsplorerDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public static EatsplorerDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (EatsplorerDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            EatsplorerDatabase.class,
                            "eatsplorer.db"
                    ).build();
                }
            }
        }
        return instance;
    }
}
