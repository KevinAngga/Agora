package com.angga.agora.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.angga.agora.R

// Set of Material typography styles to start with
val InGlobal = FontFamily(
    Font(
        resId = R.font.inglobal,
        weight = FontWeight.Normal
    ),

    Font(
        resId = R.font.inglobal_bold,
        weight = FontWeight.Bold
    )
)

val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = InGlobal,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = InGlobal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = InGlobal,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = InGlobal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),

    headlineMedium = TextStyle(
        fontFamily = InGlobal,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
    ),
)
