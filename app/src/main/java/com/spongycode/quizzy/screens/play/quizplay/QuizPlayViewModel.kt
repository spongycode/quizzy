package com.spongycode.quizzy.screens.play.quizplay

import androidx.compose.runtime.IntState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spongycode.quizzy.data.local.entity.QuizEntity
import com.spongycode.quizzy.domain.model.Question
import com.spongycode.quizzy.domain.repository.QuizRepository
import com.spongycode.quizzy.utils.Constants.TIME_PER_QUESTION
import com.spongycode.quizzy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizPlayViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {
    private val _questions = mutableStateListOf<Question>()
    val questions: SnapshotStateList<Question> = _questions

    private val _questionsState = mutableStateOf<QuizPlayState>(QuizPlayState.Loading)
    val questionsState: State<QuizPlayState> = _questionsState

    private val _currentQuestionIndex = mutableIntStateOf(0)
    val currentQuestionIndex: IntState = _currentQuestionIndex

    private val _isCorrect = mutableStateOf(false)

    private val _optionTapState = mutableStateOf<OptionTapState>(OptionTapState.Idle)
    val optionTapState: State<OptionTapState> = _optionTapState

    private val _isQuizOver = mutableStateOf(false)
    val isQuizOver: State<Boolean> = _isQuizOver

    private val _time = mutableIntStateOf(TIME_PER_QUESTION)
    val time: IntState = _time

    private val _tappedButtonId = mutableIntStateOf(-1)
    val tappedButtonId: IntState = _tappedButtonId

    private var timerJob: Job? = null

    private val _quizOverState = mutableStateOf<QuizOverState>(QuizOverState.Loading)
    val quizOverState: State<QuizOverState> = _quizOverState


    init {
        getQuestions()
    }

    private fun getQuestions() {
        viewModelScope.launch {
            quizRepository.getQuestions(
                amount = 3,
                category = 10,
                difficulty = "easy"
            ).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _questionsState.value = QuizPlayState.Error
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _questions.addAll(result.data)
                        }
                        _questionsState.value = QuizPlayState.Success
                    }
                }
            }
        }
    }

    private fun checkAnswer(optionId: Int) {
        if (optionId == -1) {
            nextQuestion()
            return
        }
        _optionTapState.value = OptionTapState.Checking
        timerJob?.cancel()
        viewModelScope.launch {
            delay(500)
            _isCorrect.value = (questions[currentQuestionIndex.intValue].correctId == optionId)
            if (_isCorrect.value) {
                _optionTapState.value = OptionTapState.CorrectAnswer
            } else {
                _optionTapState.value = OptionTapState.WrongAnswer
            }

            if (_currentQuestionIndex.intValue == _questions.size - 1) {
                saveQuiz()
                timerJob?.cancel()
                _isQuizOver.value = true
            } else {
                delay(500)
                nextQuestion()
            }
        }
    }

    private fun nextQuestion() {
        _time.intValue = TIME_PER_QUESTION
        timerJob?.cancel()
        _optionTapState.value = OptionTapState.Idle
        _currentQuestionIndex.intValue++
    }

    fun tapButton(buttonId: Int) {
        _tappedButtonId.intValue = buttonId
        checkAnswer(buttonId)
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            _time.intValue = TIME_PER_QUESTION
            while (true) {
                delay(1000)
                _time.intValue--
            }
        }
    }

    private fun saveQuiz() {
        _quizOverState.value = QuizOverState.Loading
        viewModelScope.launch {
            try {
                quizRepository.insertQuiz(
                    QuizEntity(
                        name = "Adam",
                        registrationNumber = "12XFG",
                        grade = "XII",
                        totalQuestions = 25,
                        score = 50,
                        totalCorrectAnswers = 17,
                    )
                )
                delay(500)
                _quizOverState.value = QuizOverState.Success
            } catch (_: Exception) {
                _quizOverState.value = QuizOverState.Error
            }
        }
    }
}