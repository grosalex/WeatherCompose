package com.grosalex.weathercompose.viewmodels

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grosalex.weathercompose.ErrorText
import com.grosalex.weathercompose.NullTextOrEmpty
import com.grosalex.weathercompose.R
import com.grosalex.weathercompose.WeatherQuery
import com.grosalex.weathercompose.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherCityViewModel(application: Application) : AndroidViewModel(application) {

    sealed class State {
        object Empty : State()
        object Loading : State()
        class Error(val message: String?) : State()
        class Success(val cityWeather: WeatherQuery.GetCityByName) : State()
    }

    private val state = MutableLiveData<State>(State.Empty)
    val cityName: MutableLiveData<String> = MutableLiveData("Paris")

    fun cityWeatherNameChanges(name: String) {
        cityName.value = name
    }

    fun searchCityWeatherName(name: String) {
        state.value = State.Loading
        this.viewModelScope.launch {
            try {
                val cityByName = WeatherRepository.getCityByName(name)
                if (cityByName != null) {
                    state.value = State.Success(cityWeather = cityByName)
                } else {
                    state.value = State.Empty
                }

            } catch (e: Exception) {
                state.value = State.Error(e.message)
            }
        }
    }

    @Composable
    fun RenderCityWeather() {
        val state by state.observeAsState()

        when (state) {
            State.Empty -> EmptyCityWeather()
            State.Loading -> Loading()
            is State.Error -> Error((state as State.Error).message)
            is State.Success -> CityWeather(cityWeather = (state as State.Success).cityWeather)
            null -> EmptyCityWeather()
        }

    }

    @Composable
    private fun Error(message: String?) {
        Box(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ErrorText(text = message)
        }
    }

    @Composable
    private fun Loading() {
        Box(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    private fun CityWeather(cityWeather: WeatherQuery.GetCityByName) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                NullTextOrEmpty(
                    style = MaterialTheme.typography.h5,
                    text = "${cityWeather.name}, ${cityWeather.country}"
                )
            }
        }
    }

    @Composable
    private fun EmptyCityWeather() {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = MaterialTheme.typography.h6,
                text = stringResource(id = R.string.search_for_a_city_name)
            )
        }
    }
}