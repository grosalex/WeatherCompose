package com.grosalex.weathercompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.grosalex.weathercompose.ui.WeatherComposeTheme
import com.grosalex.weathercompose.ui.primaryColor
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
    val cityWeather by weatherCityViewModel.cityByName.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
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

        cityWeather?.weather?.summary?.description?.let { it1 -> Text(text = it1) }

    }
}

@Composable
fun TopAppBar(
    value: String,
    onCityNameChanged: ((String) -> Unit),
    searchCityName: ((String) -> Unit)
) {
    TopAppBar(
        title = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.primarySurface,
                value = value,
                onValueChange = onCityNameChanged,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                label = {
                    Text(
                        text = stringResource(id = R.string.search_by_city_name),
                        color = Color.White
                    )
                },
                inactiveColor = Color.White,
                leadingIcon = {
                    Icon(imageVector = vectorResource(id = R.drawable.ic_city))
                },
                trailingIcon = {
                    IconButton(
                        onClick = { searchCityName.invoke(value) }
                    ) {
                        Icon(imageVector = vectorResource(id = R.drawable.ic_search))
                    }
                },
                onImeActionPerformed = { action, controller ->
                    if (action == ImeAction.Search) {
                        searchCityName.invoke(value)
                        controller?.hideSoftwareKeyboard()
                    }
                }
            )
        }
    )
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