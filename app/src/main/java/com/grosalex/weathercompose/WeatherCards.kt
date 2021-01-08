package com.grosalex.weathercompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.picasso.PicassoImage


@Composable
fun WindCard(wind: WeatherQuery.Wind) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
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
fun TemperatureCard(temperature: WeatherQuery.Temperature) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
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
fun SummaryCard(summary: WeatherQuery.Summary) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
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
