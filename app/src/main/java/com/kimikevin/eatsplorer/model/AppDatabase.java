package com.kimikevin.eatsplorer.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kimikevin.eatsplorer.model.dao.UserDAO;
import com.kimikevin.eatsplorer.model.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // Linking the DAOs to the database
    public abstract UserDAO getUserDAO();
}
