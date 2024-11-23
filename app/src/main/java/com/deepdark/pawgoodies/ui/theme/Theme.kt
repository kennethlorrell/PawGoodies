package com.deepdark.pawgoodies.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
    primary = WarmOrange,
    onPrimary = Color.White,
    inversePrimary = VibrantComplimentary,
    secondary = SoftGreen,
    onSecondary = Color.White,
    tertiary = VibrantComplimentary,
    onTertiary = Color.White,
    error = PastelYellow,
    onError = DarkGray,
    background = OffWhite,
    onBackground = DarkGray,
    surface = OffWhite,
    onSurface = DarkGray,
    surfaceVariant = LightGray,
    outline = LightGray,
)

val DarkColors = darkColorScheme(
    primary = WarmOrange,
    onPrimary = Color.Black,
    inversePrimary = VibrantComplimentary,
    secondary = SoftGreen,
    onSecondary = Color.Black,
    tertiary = LightBlue,
    onTertiary = Color.Black,
    error = PastelYellow,
    onError = DarkGray,
    background = DarkGray,
    onBackground = OffWhite,
    surface = DarkGray,
    onSurface = OffWhite,
    surfaceVariant = LightGray,
    outline = LightGray
)

@Composable
fun PawGoodiesTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = {
            Surface(color = colors.background) {
                content()
            }
        }
    )
}