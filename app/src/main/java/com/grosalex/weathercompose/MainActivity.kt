package com.grosalex.weathercompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import com.grosalex.weathercompose.components.SearchAppBar
import com.grosalex.weathercompose.ui.WeatherComposeTheme
import com.grosalex.weathercompose.viewmodels.WeatherCityViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherComposeTheme {
                WeatherCityScaffold()
            }
        }

    }
}

@Composable
fun WeatherCityScaffold(scaffoldState: ScaffoldState = rememberScaffoldState()) {
    val weatherCityViewModel = WeatherCityViewModel(WeatherComposeApp.app)
    val cityName: String by weatherCityViewModel.cityName.observeAsState("")
    Scaffold(
        topBar = {
            SearchAppBar(
                cityName,
                onCityNameChanged = {
                    weatherCityViewModel.cityWeatherNameChanges(it)
                },
                searchCityName = {
                    weatherCityViewModel.searchCityWeatherName(it)
                }
            )
        }
    ) {
        weatherCityViewModel.RenderCityWeather()
    }
}
