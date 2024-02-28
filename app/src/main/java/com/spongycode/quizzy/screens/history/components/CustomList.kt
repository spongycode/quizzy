package com.spongycode.quizzy.screens.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spongycode.quizzy.data.local.entity.QuizEntity
import com.spongycode.quizzy.screens.components.TextBox
import com.spongycode.quizzy.ui.theme.OptionLightYellow
import com.spongycode.quizzy.utils.Constants

@Composable
fun CustomList(quizzes: SnapshotStateList<QuizEntity>, topPadding: Dp) {

    Column(Modifier.padding(start = 10.dp, end = 10.dp, top = topPadding)) {
        LazyColumn {
            itemsIndexed(quizzes) { _, quiz ->
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(Constants.VERY_SMALL_HEIGHT))
                        .background(OptionLightYellow),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    ) {
                        Row {
                            TextBox(text = "Name: ")
                            TextBox(text = quiz.name)
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                        ) {
                            TextBox(text = "Regn. No: ")
                            TextBox(text = quiz.registrationNumber)
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                        ) {
                            TextBox(text = "Grade: ")
                            TextBox(text = quiz.grade)
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                        ) {
                            TextBox(text = "Total Questions: ")
                            TextBox(text = quiz.totalQuestions.toString())
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                        ) {
                            TextBox(text = "Score: ")
                            TextBox(text = quiz.score.toString())
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                        ) {
                            TextBox(text = "Accuracy: ")
                            TextBox(
                                text = "${(quiz.totalCorrectAnswers * 100f / quiz.totalQuestions.toFloat()).toInt()} %"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}
