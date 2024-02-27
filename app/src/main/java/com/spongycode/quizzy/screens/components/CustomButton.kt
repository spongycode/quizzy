package com.spongycode.quizzy.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spongycode.quizzy.utils.Fonts
import com.spongycode.quizzy.utils.bounceClick

@Composable
fun CustomButton(
    onClick: () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    text: String = "Hello"
) {
    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            color = contentColor,
            modifier = Modifier.padding(8.dp),
            text = text,
            fontFamily = Fonts.poppinsFamily,
            fontWeight = FontWeight.W600,
            fontSize = 15.sp
        )
    }
}