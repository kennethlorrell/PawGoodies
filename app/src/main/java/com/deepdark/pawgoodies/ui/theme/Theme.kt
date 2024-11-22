package com.deepdark.pawgoodies.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val WarmOrange = Color(0xFFFFA726)
val SoftGreen = Color(0xFF66BB6A)
val LightBlue = Color(0xFF42A5F5)
val PastelYellow = Color(0xFFFFEB3B)
val OffWhite = Color(0xFFFAFAFA)
val DarkGray = Color(0xFF424242)
val LightGray = Color(0xFF757575)

val LightColors = lightColorScheme(
    primary = WarmOrange,
    onPrimary = Color.White,
    secondary = SoftGreen,
    onSecondary = Color.White,
    tertiary = LightBlue,
    onTertiary = Color.White,
    error = PastelYellow,
    onError = DarkGray,
    background = OffWhite,
    onBackground = DarkGray,
    surface = OffWhite,
    onSurface = DarkGray,
    surfaceVariant = LightGray,
    outline = LightGray
)

val DarkColors = darkColorScheme(
    primary = WarmOrange,
    onPrimary = Color.Black,
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