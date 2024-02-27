package com.spongycode.quizzy.screens.play.quizplay

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spongycode.quizzy.domain.model.Question
import com.spongycode.quizzy.screens.components.CustomButton
import com.spongycode.quizzy.screens.components.PlaceholderMessageText
import com.spongycode.quizzy.screens.play.quizplay.components.CircularTimer
import com.spongycode.quizzy.screens.play.quizplay.components.OptionsArea
import com.spongycode.quizzy.screens.play.quizplay.components.QuestionTitle
import com.spongycode.quizzy.screens.play.quizplay.components.updateCircularTransitionData
import com.spongycode.quizzy.ui.theme.OptionDarkGreen
import com.spongycode.quizzy.utils.Constants
import com.spongycode.quizzy.utils.Fonts

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuizPlayScreen(
    navController: NavHostController,
    viewModel: QuizPlayViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp
    val width = configuration.screenWidthDp
    Column {
        if (viewModel.isQuizOver.value) {
            Column {
                QuizOverScreenContent(
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(0)
                        }
                    },
                    quizOverState = viewModel.quizOverState.value
                )

                Text(text = "Quiz over")
                Button(onClick = {
                    navController.navigate("home") {
                        popUpTo(0)
                    }
                }) {
                    Text(text = "go Home")
                }
            }

        } else {

            QuizPlayScreenContent(
                modifier = Modifier.fillMaxSize(),
                height = height,
                width = width,
                remainingTime = viewModel.time.intValue.toLong(),
                currentQuestionIndex = viewModel.currentQuestionIndex.intValue,
                questions = viewModel.questions,
                questionsState = viewModel.questionsState.value,
                onStartTimer = { viewModel.startTimer() },
                optionTapState = viewModel.optionTapState.value,
                onTap = { id -> viewModel.tapButton(id) },
                tappedButtonId = viewModel.tappedButtonId.intValue
            )
        }
    }
}


@Composable
fun QuizPlayScreenContent(
    modifier: Modifier = Modifier,
    height: Int = 800,
    width: Int = 400,
    questionsState: QuizPlayState = QuizPlayState.Loading,
    questions: SnapshotStateList<Question> = SnapshotStateList(),
    remainingTime: Long = 30,
    currentQuestionIndex: Int = 0,
    onStartTimer: () -> Unit = {},
    optionTapState: OptionTapState,
    onTap: (Int) -> Unit,
    tappedButtonId: Int
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        when (questionsState) {
            QuizPlayState.Error -> PlaceholderMessageText("Error fetching questions")

            QuizPlayState.Loading -> PlaceholderMessageText("Loading questions..")

            QuizPlayState.Success -> PlayingScreenSuccess(
                remainingTime = remainingTime,
                currentQuestionIndex = currentQuestionIndex,
                questions = questions,
                onStartTimer = { onStartTimer() },
                optionTapState = optionTapState,
                onTap = { id -> onTap(id) },
                tappedButtonId = tappedButtonId
            )
        }
    }
}


@Composable
fun QuizOverScreenContent(
    quizOverState: QuizOverState,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            modifier = Modifier.padding(vertical = Constants.MEDIUM_HEIGHT),
            text = "Quiz Over",
            fontSize = 30.sp,
            fontWeight = FontWeight.W600,
            fontFamily = Fonts.poppinsFamily,
            color = MaterialTheme.colorScheme.primary
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (quizOverState) {
                QuizOverState.Error -> PlaceholderMessageText(text = "Oops, some error occurred.")
                QuizOverState.Loading -> PlaceholderMessageText(text = "Saving your quiz..")
                QuizOverState.Success -> {

                    Column {
                        PlaceholderMessageText(
                            text = "Quiz saved successfully!",
                            content = {
                                CustomButton(
                                    onClick = { onClick() },
                                    containerColor = OptionDarkGreen,
                                    contentColor = Color.White,
                                    text = "HOME"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PlayingScreenSuccess(
    remainingTime: Long = 30,
    currentQuestionIndex: Int = 0,
    questions: SnapshotStateList<Question> = SnapshotStateList(),
    onStartTimer: () -> Unit = {},
    optionTapState: OptionTapState,
    onTap: (Int) -> Unit,
    tappedButtonId: Int
) {
    val transitionData = updateCircularTransitionData(
        remainingTime = remainingTime,
        totalTime = Constants.TIME_PER_QUESTION.toLong()
    )
    if (currentQuestionIndex < questions.size) {
        LaunchedEffect(currentQuestionIndex) {
            onStartTimer()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularTimer(transitionData = transitionData, time = remainingTime.toInt())

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                QuestionTitle(questions[currentQuestionIndex].title)
                OptionsArea(
                    question = questions[currentQuestionIndex],
                    optionTapState = optionTapState,
                    onTap = { id -> onTap(id) },
                    tappedButtonId = tappedButtonId
                )
            }
        }
    }
}
