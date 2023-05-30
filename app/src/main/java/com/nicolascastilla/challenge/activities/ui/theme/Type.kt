package com.nicolascastilla.challenge.activities.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nicolascastilla.challenge.R

val Rubik = FontFamily(
    Font(resId = R.font.rubik_normal, weight = FontWeight.Normal),
    Font(resId = R.font.rubik_meduim, weight = FontWeight.Medium),
    Font(resId = R.font.rubik_bold, weight = FontWeight.Bold)
)
// Set of Material typography styles to start with
val Typography = androidx.compose.material3.Typography(
    bodyLarge = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)