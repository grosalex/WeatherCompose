package com.grosalex.weathercompose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.grosalex.weathercompose.R
import com.grosalex.weathercompose.ui.WeatherComposeTheme

@Composable
fun SearchAppBar(
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


@Preview
@Composable
fun SearchAppBarPreview() {
    WeatherComposeTheme {
        SearchAppBar(value = "Paris", onCityNameChanged = {}, searchCityName = { })
    }
}