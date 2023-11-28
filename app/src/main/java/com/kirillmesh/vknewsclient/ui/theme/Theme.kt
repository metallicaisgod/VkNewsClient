package com.kirillmesh.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorScheme = darkColors(
    primary = Color.Black,
    secondary = Color.Black,
    onPrimary = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    onSecondary = Color.Gray
)

@SuppressLint("ConflictingOnColor")
private val LightColorScheme = lightColors(
    primary = Color.White,
    secondary = Color.White,
    onPrimary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    onSecondary = Color.Gray

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun VkNewsClientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}