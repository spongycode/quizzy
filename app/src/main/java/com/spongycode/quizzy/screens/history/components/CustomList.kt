package com.spongycode.quizzy.screens.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.spongycode.quizzy.ui.theme.OptionLightYellow
import com.spongycode.quizzy.utils.Constants

@Composable
fun CustomList(quizzes: SnapshotStateList<QuizEntity>, topPadding: Dp) {
    val configuration = LocalConfiguration.current
    val width = (configuration.screenWidthDp - 20) / 2

    Column(Modifier.padding(start = 10.dp, end = 10.dp, top = topPadding)) {
        LazyColumn {
            itemsIndexed(quizzes) { _, quiz ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .height((width / 3).dp)
                        .clip(RoundedCornerShape(Constants.VERY_SMALL_HEIGHT))
                        .background(OptionLightYellow),
                    contentAlignment = Alignment.CenterStart
                ) {

                    Column(
                        Modifier.fillMaxWidth(),
                    ) {
                        Text(text = quiz.name)
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = quiz.registrationNumber)
                            Text(text = quiz.grade)
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = quiz.totalQuestions.toString())
                            Text(text = quiz.score.toString())
                            Text(text = "${(quiz.totalCorrectAnswers / quiz.totalCorrectAnswers)} %")
                        }
                    }
                }
            }
        }
    }
}
