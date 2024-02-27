package com.spongycode.quizzy.screens.play.quizplay

sealed class QuizOverState {
    data object Loading : QuizOverState()
    data object Success : QuizOverState()
    data object Error : QuizOverState()
}