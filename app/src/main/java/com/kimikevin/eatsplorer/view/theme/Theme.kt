package com.kimikevin.eatsplorer.view.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = RedRibbon,
    secondary = BlueVogue,
    tertiary = Lynch
)

private val LightColorScheme = lightColorScheme(
    primary = RedRibbon,
    secondary = BlueVogue,
    tertiary = Lynch,
    background = AthensGray,
    surface = AthensGray,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    onBackground = TextBlack,
    onSurface = TextBlack
)

@Composable
fun EatsplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
