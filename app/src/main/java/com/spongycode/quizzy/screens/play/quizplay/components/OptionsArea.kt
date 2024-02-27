package com.spongycode.quizzy.screens.play.quizplay.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spongycode.quizzy.R
import com.spongycode.quizzy.domain.model.Question
import com.spongycode.quizzy.screens.play.quizplay.OptionTapState
import com.spongycode.quizzy.ui.theme.OptionDarkGreen
import com.spongycode.quizzy.ui.theme.OptionDarkRed
import com.spongycode.quizzy.ui.theme.OptionDarkYellow
import com.spongycode.quizzy.ui.theme.OptionLightGreen
import com.spongycode.quizzy.ui.theme.OptionLightRed
import com.spongycode.quizzy.ui.theme.OptionLightYellow
import com.spongycode.quizzy.utils.Constants

@Composable
fun OptionsArea(
    question: Question,
    optionTapState: OptionTapState,
    onTap: (Int) -> Unit,
    tappedButtonId: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(question.options) { option ->
            OptionField(
                text = option.value,
                onClick = {
                    if ((optionTapState == OptionTapState.Idle)) {
                        onTap(option.id)
                    }
                },
                fillColor = if (option.id != tappedButtonId) Color.White else {
                    when (optionTapState) {
                        OptionTapState.Checking -> OptionLightYellow
                        OptionTapState.CorrectAnswer -> OptionLightGreen
                        OptionTapState.Idle -> Color.White
                        OptionTapState.WrongAnswer -> OptionLightRed
                    }
                },
                tint = if (option.id != tappedButtonId) MaterialTheme.colorScheme.primary else {
                    when (optionTapState) {
                        OptionTapState.Checking -> OptionDarkYellow
                        OptionTapState.CorrectAnswer -> OptionDarkGreen
                        OptionTapState.Idle -> MaterialTheme.colorScheme.primary
                        OptionTapState.WrongAnswer -> OptionDarkRed
                    }
                },
                iconId = if (option.id != tappedButtonId) null else {
                    when (optionTapState) {
                        OptionTapState.Checking -> null
                        OptionTapState.CorrectAnswer -> R.drawable.baseline_check_circle_24
                        OptionTapState.Idle -> null
                        OptionTapState.WrongAnswer -> R.drawable.baseline_cancel_24
                    }
                }
            )
            Spacer(modifier = Modifier.height(Constants.SMALL_HEIGHT))
        }
    }
}