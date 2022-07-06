package com.immo.findTheDifferences.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.immo.findTheDifferences.R

val fontFamily = FontFamily(
    Font(R.font.asap_regular),
    Font(R.font.asap_medium, FontWeight.Medium),
    Font(R.font.asap_bold, FontWeight.Bold)
)

val Typography = Typography(

    h1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic
    ),

    body1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic
    ),

    button = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp
    ),
    caption = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)