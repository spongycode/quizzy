package com.spongycode.quizzy.screens.play.personaldetails

sealed class PersonalDetailsEvent {
    data class EnteredName(val value: String) : PersonalDetailsEvent()
    data class EnteredRegistrationNumber(val value: String) : PersonalDetailsEvent()
    data class EnteredGrade(val value: String) : PersonalDetailsEvent()
}
