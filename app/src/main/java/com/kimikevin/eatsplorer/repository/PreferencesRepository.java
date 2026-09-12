package com.kimikevin.eatsplorer.repository;

import static com.kimikevin.eatsplorer.util.Utils.KEY_COMPLETE;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class PreferencesRepository {
    private final RxDataStore<Preferences> dataStore;
    private final Preferences.Key<Boolean> KEY_ONBOARDING_COMPLETE = PreferencesKeys.booleanKey(KEY_COMPLETE);

    @Inject
    public PreferencesRepository(RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }

    public void setOnboardingComplete(boolean isComplete) {
        dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePrefs = prefsIn.toMutablePreferences();
            mutablePrefs.set(KEY_ONBOARDING_COMPLETE, isComplete);
            return Single.just(mutablePrefs);
        }).subscribe();
    }

    public Single<Boolean> isOnboardingComplete() {
        return dataStore.data().firstOrError()
                .map(prefs -> {
                    Boolean isComplete = prefs.get(KEY_ONBOARDING_COMPLETE);
                    return isComplete != null ? isComplete : false;
                });
    }
}
