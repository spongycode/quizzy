package com.spongycode.quizzy.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.spongycode.quizzy.utils.Fonts

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    labelText: String = "",
    placeHolderText: String = "",
    shape: Shape,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    enabled: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(text = labelText, fontFamily = Fonts.poppinsFamily)
            },
            placeholder = {
                Text(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    text = placeHolderText,
                    fontFamily = Fonts.poppinsFamily
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .focusRequester(focusRequester = focusRequester),
            shape = shape,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.LightGray,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            singleLine = singleLine,
            trailingIcon = null,
            textStyle = TextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                fontFamily = Fonts.poppinsFamily
            )
        )
    }
}