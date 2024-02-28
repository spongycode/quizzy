package com.spongycode.quizzy.screens.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.spongycode.quizzy.utils.Fonts

@Composable
fun TextBox(
    modifier: Modifier = Modifier,
    text: String = "",
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = MaterialTheme.colorScheme.primary,
    fontFamily: FontFamily = Fonts.poppinsFamily,
    weight: FontWeight = FontWeight.W400,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = style,
        fontFamily = fontFamily,
        fontWeight = weight,
        textDecoration = textDecoration,
        textAlign = textAlign
    )
}