package com.spongycode.quizzy.screens.play.quizplay

sealed class QuizPlayState {
    data object Loading : QuizPlayState()
    data object Success : QuizPlayState()
    data object Error : QuizPlayState()
}