package com.spongycode.quizzy.screens.play

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spongycode.quizzy.domain.model.Category
import com.spongycode.quizzy.domain.model.Question
import com.spongycode.quizzy.domain.repository.QuizRepository
import com.spongycode.quizzy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _registrationNumber = mutableStateOf("")
    val registrationNumber: State<String> = _registrationNumber

    private val _grade = mutableStateOf("")
    val grade: State<String> = _grade

    private val _amount = mutableIntStateOf(20)
    val amount: State<Int> = _amount

    private val _selectedCategory = mutableIntStateOf(0)
    val selectedCategory: State<Int> = _selectedCategory

    private val _difficulty = mutableStateOf(Difficulty.ANY)
    val difficulty: State<Difficulty> = _difficulty

    private val _categories = mutableStateListOf<Category>()
    val categories: SnapshotStateList<Category> = _categories

    private val _questions = mutableStateListOf<Question>()
    val questions: SnapshotStateList<Question> = _questions


    fun onEvent(event: PlayEvent) {
        when (event) {
            is PlayEvent.EnteredGrade -> _grade.value = event.value
            is PlayEvent.EnteredName -> _name.value = name.value
            is PlayEvent.EnteredRegistrationNumber -> _registrationNumber.value = event.value
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            quizRepository.getCategories().collect { result ->
                when (result) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _categories.addAll(result.data)
                        }
                    }
                }
            }
        }
    }

    fun getQuestions() {
        viewModelScope.launch {
            quizRepository.getQuestions(
                amount = amount.value,
                category = selectedCategory.value,
                difficulty = difficulty.value.toString().lowercase(Locale.getDefault())
            ).collect { result ->
                when (result) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _questions.addAll(result.data)
                        }
                    }
                }
            }
        }
    }

    enum class Difficulty {
        EASY,
        MEDIUM,
        HARD,
        ANY
    }
}