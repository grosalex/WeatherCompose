package com.grosalex.weathercompose

import androidx.compose.material.AmbientTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

@Composable
fun NullTextOrEmpty(
    modifier: Modifier = Modifier,
    style: TextStyle = AmbientTextStyle.current,
    text: String?
) {
    Text(modifier = modifier, style = style, text = text.orEmpty())
}

@Composable
fun ErrorText(
    modifier: Modifier = Modifier, text: String?
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.body2,
        color = Color.Red,
        text = stringResource(id = R.string.oops_something_went_wrong, text.orEmpty())
    )
}