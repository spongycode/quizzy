package com.spongycode.quizzy.screens.play.quizplay

sealed class OptionTapState {
    data object Idle : OptionTapState()
    data object Checking : OptionTapState()
    data object CorrectAnswer : OptionTapState()
    data object WrongAnswer : OptionTapState()
}