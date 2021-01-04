package com.grosalex.weathercompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import com.grosalex.weathercompose.ui.WeatherComposeTheme
import com.grosalex.weathercompose.viewmodels.WeatherCityViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherCityViewModel = WeatherCityViewModel(application)

        setContent {
            WeatherComposeTheme {
                val cityByName = weatherCityViewModel.cityByName.observeAsState()
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android ${cityByName.value?.weather?.summary?.description ?: "none"}")
                }
            }
        }

        weatherCityViewModel.getCityWeather("Paris")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherComposeTheme {
        Greeting("Android")
    }
}