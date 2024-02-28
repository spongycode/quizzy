package com.spongycode.quizzy.screens.play.categorypick

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spongycode.quizzy.R
import com.spongycode.quizzy.screens.components.TextBox
import com.spongycode.quizzy.ui.theme.DecentBlue
import com.spongycode.quizzy.ui.theme.DecentGreen
import com.spongycode.quizzy.utils.bounceClick

@Preview
@Composable
fun CategoryCard(
    isSelected: Boolean = true,
    name: String = "General Knowledge",
    containerColor: Color = DecentBlue,
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .bounceClick(0.96f)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .background(containerColor)
            .clickable { onClick(name) }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly)
    {
        TextBox(
            modifier = Modifier.weight(1f),
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            weight = FontWeight.W600
        )

        Icon(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(if (isSelected) DecentGreen else Color.White)
                .border(1.dp, Color.Gray, CircleShape)
                .padding(1.dp),
            painter = painterResource(id = if (isSelected) R.drawable.baseline_check_circle_24 else R.drawable.baseline_check_circle_trans_24),
            contentDescription = null,
            tint = Color.White
        )
    }
}