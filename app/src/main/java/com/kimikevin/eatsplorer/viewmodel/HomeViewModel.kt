package com.kimikevin.eatsplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kimikevin.eatsplorer.model.entity.Restaurant
import com.kimikevin.eatsplorer.model.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel : ViewModel() {
    private val repository = RestaurantRepository.getInstance()

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

    private val _spinWinner = MutableStateFlow<Restaurant?>(null)
    val spinWinner: StateFlow<Restaurant?> = _spinWinner.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchNearbyRestaurants(lat: Double, lng: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                repository.searchNearby(lat, lng).fold(
                    onSuccess = { list ->
                        _restaurants.value = list
                    },
                    onFailure = { exception ->
                        _errorMessage.value = exception.message
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun spinTheWheel() {
        val currentList = _restaurants.value
        if (currentList.isEmpty()) {
            _errorMessage.value = "No restaurants found near you"
            return
        }

        val candidates = currentList.filter { it.rating >= 4.0 }.ifEmpty { currentList }
        val randomIndex = Random.nextInt(candidates.size)
        _spinWinner.value = candidates[randomIndex]
    }

    fun clearSpinWinner() {
        _spinWinner.value = null
    }
}
