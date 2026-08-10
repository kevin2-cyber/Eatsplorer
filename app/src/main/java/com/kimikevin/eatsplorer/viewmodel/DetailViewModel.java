package com.kimikevin.eatsplorer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kimikevin.eatsplorer.model.entity.PlaceDetailsResponse;
import com.kimikevin.eatsplorer.model.repository.RestaurantRepository;

public class DetailViewModel extends ViewModel {
    private final RestaurantRepository repository;

    private final MutableLiveData<PlaceDetailsResponse> _details = new MutableLiveData<>();
    public LiveData<PlaceDetailsResponse> details = _details;

    private final MediatorLiveData<Boolean> _isLoading = new MediatorLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> errorMessage = _errorMessage;

    public DetailViewModel() {
        repository = RestaurantRepository.getInstance();
        _isLoading.setValue(false);
        _isLoading.addSource(_details, d -> _isLoading.setValue(false));
        _isLoading.addSource(_errorMessage, e -> _isLoading.setValue(false));
    }

    // fetch restaurant details
    public void fetchRestaurantDetails(String placeId) {
        _isLoading.setValue(true);
        repository.getPlaceDetails(placeId, _details, _errorMessage);
    }
}
