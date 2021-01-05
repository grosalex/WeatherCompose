package com.grosalex.weathercompose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grosalex.weathercompose.WeatherQuery
import com.grosalex.weathercompose.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherCityViewModel(application: Application) : AndroidViewModel(application) {
    val cityName: MutableLiveData<String> = MutableLiveData("")
    val cityByName: MutableLiveData<WeatherQuery.GetCityByName?> = MutableLiveData(null)

    fun cityWeatherNameChanges(name: String) {
        cityName.value = name
    }

    fun searchCityWeatherName(name: String) {
        this.viewModelScope.launch {
            cityByName.value = WeatherRepository.getCityByName(name)
        }
    }
}