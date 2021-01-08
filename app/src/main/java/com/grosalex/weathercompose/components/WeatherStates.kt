package com.grosalex.weathercompose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.grosalex.weathercompose.*
import com.grosalex.weathercompose.R


@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyCityWeather() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            style = MaterialTheme.typography.h6,
            text = stringResource(id = R.string.search_for_a_city_name)
        )
    }
}


@Composable
fun Error(message: String?, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ErrorText(text = message)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            onRetry.invoke()
        }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun CityWeather(cityWeather: WeatherQuery.GetCityByName) {
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