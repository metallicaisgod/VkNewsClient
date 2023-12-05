package com.kirillmesh.vknewsclient.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.kirillmesh.vknewsclient.R


val RalewayFontFamily = FontFamily(
    Font(R.font.raleway_regular),
    Font(R.font.raleway_bold, weight = FontWeight.Bold),
    Font(R.font.raleway_light, weight = FontWeight.Light),
    Font(R.font.raleway_thin, weight = FontWeight.Thin),
    Font(R.font.raleway_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = RalewayFontFamily
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
