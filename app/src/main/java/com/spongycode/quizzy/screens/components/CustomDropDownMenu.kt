package com.spongycode.quizzy.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spongycode.quizzy.utils.Fonts
import androidx.compose.material3.ExposedDropdownMenuBox as ExposedDropdownMenuBox1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    items: List<String> = emptyList(),
    selectedItem: String = "Default",
    onClick: (String) -> Unit = {},
    width: Int = 300
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        ExposedDropdownMenuBox1(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            OutlinedTextField(
                readOnly = true,
                value = selectedItem,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = Fonts.poppinsFamily,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .menuAnchor()
                    .width(width.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
                    .exposedDropdownSize()
                    .padding(horizontal = 10.dp)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                fontFamily = Fonts.poppinsFamily,
                                fontSize = 15.sp
                            )
                        },
                        onClick = {
                            onClick(item)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}


