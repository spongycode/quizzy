package com.spongycode.quizzy.screens.play

sealed class PlayEvent {
    data class EnteredName(val value: String) : PlayEvent()
    data class EnteredRegistrationNumber(val value: String) : PlayEvent()
    data class EnteredGrade(val value: String) : PlayEvent()
}
