package com.kimikevin.eatsplorer.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kimikevin.eatsplorer.model.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    long createUser(User user);
    @Update
    void updateUser(User user);
    @Delete
    void deleteUser(User user);
    @Query("select * from user")
    List<User> getUsers();
    @Query("select * from user where id ==:userId")
    User getUserById(long userId);
}
