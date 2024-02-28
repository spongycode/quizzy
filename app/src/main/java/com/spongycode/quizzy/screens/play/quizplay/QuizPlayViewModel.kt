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
import com.spongycode.quizzy.utils.Constants
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

    private val _score = mutableIntStateOf(0)
    val score = _score

    private val _totalCorrectAnswers = mutableIntStateOf(0)

    private val _tappedButtonId = mutableIntStateOf(-1)
    val tappedButtonId: IntState = _tappedButtonId

    private var timerJob: Job? = null

    private val _quizOverState = mutableStateOf<QuizOverState>(QuizOverState.Loading)
    val quizOverState: State<QuizOverState> = _quizOverState

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _registrationNumber = mutableStateOf("")
    val registrationNumber: State<String> = _registrationNumber

    private val _grade = mutableStateOf("")
    val grade: State<String> = _grade

    private val _triggered = mutableStateOf(false)

    fun getQuestions(amount: Int, categoryId: Int, difficulty: String) {
        if (!_triggered.value) {
            _triggered.value = true
            viewModelScope.launch {
                quizRepository.getQuestions(
                    amount = amount,
                    category = categoryId,
                    difficulty = difficulty.lowercase()
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
                _totalCorrectAnswers.intValue++
                when (questions[currentQuestionIndex.intValue].difficulty.lowercase()) {
                    "easy" -> _score.intValue += Constants.SCORE_EASY_QUESTION
                    "medium" -> _score.intValue += Constants.SCORE_MEDIUM_QUESTION
                    "hard" -> _score.intValue += Constants.SCORE_HARD_QUESTION
                }
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
        if (_currentQuestionIndex.intValue == _questions.size - 1) {
            saveQuiz()
            timerJob?.cancel()
            _isQuizOver.value = true
        } else {
            _currentQuestionIndex.intValue++
        }
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
                if (_time.intValue == 0) {
                    delay(500)
                    nextQuestion()
                }
            }
        }
    }

    private fun saveQuiz() {
        _quizOverState.value = QuizOverState.Loading
        viewModelScope.launch {
            try {
                quizRepository.insertQuiz(
                    QuizEntity(
                        name = name.value,
                        registrationNumber = registrationNumber.value,
                        grade = grade.value,
                        totalQuestions = questions.size,
                        score = _score.intValue,
                        totalCorrectAnswers = _totalCorrectAnswers.intValue,
                    )
                )
                delay(500)
                _quizOverState.value = QuizOverState.Success
            } catch (_: Exception) {
                _quizOverState.value = QuizOverState.Error
            }
        }
    }

    fun updateInfo(name: String, registrationNumber: String, grade: String) {
        _name.value = name
        _registrationNumber.value = registrationNumber
        _grade.value = grade
    }
}