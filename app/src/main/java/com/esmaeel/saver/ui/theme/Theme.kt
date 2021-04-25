package com.esmaeel.saver.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = HeavyGray,
    primaryVariant = Black,
    secondary = HeavyBlacky,

    onPrimary = Color.White,
    onSecondary = Color.White,

    background = HeavyGray,
    onBackground = Color.White,

    error = Color.Red,
    onError = Color.White,

    surface = Color.White,
    onSurface = Color.Black
)

private val LightColorPalette = lightColors(
    primary = HeavyGray,
    primaryVariant = Black,
    secondary = HeavyBlacky,

    onPrimary = Color.White,
    onSecondary = Color.Black,

    background = HeavyGray,
    onBackground = Color.White,

    error = Color.Red,
    onError = Color.White,

    surface = HeavyBlacky,
    onSurface = Color.Black
)

@Composable
fun SaverTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}