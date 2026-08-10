package com.kimikevin.eatsplorer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kimikevin.eatsplorer.model.entity.Restaurant;
import com.kimikevin.eatsplorer.model.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeViewModel extends ViewModel {
    private final RestaurantRepository repository;

    private final MutableLiveData<List<Restaurant>> _restaurants = new MutableLiveData<>();
    public LiveData<List<Restaurant>> restaurants = _restaurants;

    private final MutableLiveData<Restaurant> _spinWinner = new MutableLiveData<>();
    public LiveData<Restaurant> spinWinner = _spinWinner;

    private final MediatorLiveData<Boolean> _isLoading = new MediatorLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> errorMessage = _errorMessage;

    public HomeViewModel() {
        repository = RestaurantRepository.getInstance();
        _isLoading.setValue(false);
        _isLoading.addSource(_restaurants, r -> _isLoading.setValue(false));
        _isLoading.addSource(_errorMessage, e -> _isLoading.setValue(false));
    }

    // fetch the list
    public void fetchNearbyRestaurants(double lat, double lng) {
        _isLoading.setValue(true);
        repository.searchNearby(lat, lng, _restaurants, _errorMessage);
    }

    // spin the wheel
    public void spinTheWheel() {
        List<Restaurant> currentList = _restaurants.getValue();
        if(currentList == null || currentList.isEmpty()) {
            _errorMessage.setValue("No restaurants found near you");
            return;
        }

        List<Restaurant> candidates = new ArrayList<>();
        for (Restaurant restaurant : currentList) {
            if (restaurant.rating() >= 4.0) {
                candidates.add(restaurant);
            }
        }

        if (candidates.isEmpty()) {
            candidates = currentList;
        }

        int randomIndex = new Random().nextInt(candidates.size());
        _spinWinner.setValue(candidates.get(randomIndex));
    }
}
