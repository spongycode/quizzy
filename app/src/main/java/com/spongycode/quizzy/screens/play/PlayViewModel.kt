package com.spongycode.quizzy.screens.play

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(

) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _registrationNumber = mutableStateOf("")
    val registrationNumber: State<String> = _registrationNumber

    private val _grade = mutableStateOf("")
    val grade: State<String> = _grade


    fun onEvent(event: PlayEvent) {
        when (event) {
            is PlayEvent.EnteredGrade -> _grade.value = event.value
            is PlayEvent.EnteredName -> _name.value = name.value
            is PlayEvent.EnteredRegistrationNumber -> _registrationNumber.value = event.value
        }
    }


}