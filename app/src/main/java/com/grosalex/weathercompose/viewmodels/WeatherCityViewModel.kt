package com.grosalex.weathercompose.viewmodels

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grosalex.weathercompose.ErrorText
import com.grosalex.weathercompose.NullTextOrEmpty
import com.grosalex.weathercompose.R
import com.grosalex.weathercompose.WeatherQuery
import com.grosalex.weathercompose.repository.WeatherRepository
import dev.chrisbanes.accompanist.picasso.PicassoImage
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
        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ErrorText(text = message)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                cityName.value?.let { searchCityWeatherName(it) }
            }) {
                Text(text = stringResource(id = R.string.retry))
            }
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
            cityWeather.weather?.summary?.let {
                SummaryCard(it)
            }

            cityWeather.weather?.temperature?.let {
                TemperatureCard(it)
            }

            cityWeather.weather?.wind?.let {
                WindCard(it)
            }
        }
    }

    @Composable
    private fun WindCard(wind: WeatherQuery.Wind) {
        Card(Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        style = MaterialTheme.typography.h6,
                        text = stringResource(id = R.string.wind_speed)
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "${wind.speed} km/h"
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        style = MaterialTheme.typography.h6,
                        text = stringResource(id = R.string.wind_direction)
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "${wind.deg}°"
                    )
                }

            }

        }
    }

    @Composable
    private fun TemperatureCard(temperature: WeatherQuery.Temperature) {
        Card(Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        style = MaterialTheme.typography.h6,
                        text = stringResource(id = R.string.actual_temp)
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "${temperature.actual}°C"
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        style = MaterialTheme.typography.h6,
                        text = stringResource(id = R.string.feeling_temp)
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "${temperature.feelsLike}°C"
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        style = MaterialTheme.typography.h6,
                        text = stringResource(id = R.string.minimal)
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "${temperature.min}°C"
                    )
                    Text(
                        style = MaterialTheme.typography.h6,
                        text = stringResource(id = R.string.maximal)
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "${temperature.max}°C"
                    )
                }
            }
        }
    }

    @Composable
    private fun SummaryCard(summary: WeatherQuery.Summary) {
        Card(Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PicassoImage(data = "https://openweathermap.org/img/wn/${summary.icon}@2x.png")
                Text(
                    style = MaterialTheme.typography.h6,
                    text = stringResource(id = R.string.its_currently)
                )
                Text(
                    style = MaterialTheme.typography.body1,
                    text = summary.description.orEmpty()
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