package com.kimikevin.eatsplorer.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashViewModel extends ViewModel {
    private static final String TAG = "SplashViewModel";
    private final MutableLiveData<Boolean> _isDataReady = new MutableLiveData<>(false);

    // Professional Tip: Use ExecutorService for background work instead of raw 'new Thread()'
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SplashViewModel() {
        checkAppInitialization();
    }

    private void checkAppInitialization() {
        executorService.execute(() -> {
            try {
                // Simulate heavy initialization (e.g., checking user session, warming cache)
                Thread.sleep(1500);
                _isDataReady.postValue(true);
            } catch (InterruptedException e) {
                Log.e(TAG, "Initialization interrupted", e);
                // In case of error, you might still want to proceed or show an error state
                _isDataReady.postValue(true);
            }
        });
    }

    public LiveData<Boolean> isDataReady() {
        return _isDataReady;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Crucial: Clean up threads to prevent memory leaks
        executorService.shutdown();
    }
}