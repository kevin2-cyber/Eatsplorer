package com.kimikevin.eatsplorer.view.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.kimikevin.eatsplorer.R

val SamsungFontFamily = FontFamily(
    Font(R.font.samsung, FontWeight.Normal),
    Font(R.font.samsung_bold, FontWeight.Bold)
)

val baseline = Typography()

// Set of Material typography styles to start with
val EatsplorerTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = SamsungFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = SamsungFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = SamsungFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = SamsungFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = SamsungFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = SamsungFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = SamsungFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = SamsungFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = SamsungFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = SamsungFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = SamsungFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = SamsungFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = SamsungFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = SamsungFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = SamsungFontFamily),
)
