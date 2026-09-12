package com.kimikevin.eatsplorer.di;

import static com.kimikevin.eatsplorer.util.Utils.DATABASE_NAME;
import static com.kimikevin.eatsplorer.util.Utils.PREF_KEY;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.room.Room;

import com.kimikevin.eatsplorer.data.database.FavoriteDatabase;
import com.kimikevin.eatsplorer.model.dao.FavoriteDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public FavoriteDatabase provideFavoriteDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, FavoriteDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration(true)
                .build();

    }

    @Provides
    @Singleton
    public FavoriteDao providesFavoriteDao(FavoriteDatabase eatsplorerDatabase) {
        return eatsplorerDatabase.favoriteDao();
    }

    @Provides
    @Singleton
    public RxDataStore<Preferences> provideDataStore(@ApplicationContext Context context) {
        return new RxPreferenceDataStoreBuilder(context, PREF_KEY).build();
    }
}
