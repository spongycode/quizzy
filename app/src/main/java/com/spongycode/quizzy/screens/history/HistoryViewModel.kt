package com.spongycode.quizzy.screens.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spongycode.quizzy.data.local.entity.QuizEntity
import com.spongycode.quizzy.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _historyState = mutableStateOf<HistoryState>(HistoryState.Loading)
    val historyState: State<HistoryState> = _historyState

    private val _quizzes = mutableStateListOf<QuizEntity>()
    val quizzes: SnapshotStateList<QuizEntity> = _quizzes

    init {
        fetchHistoryQuizzes()
    }

    private fun fetchHistoryQuizzes() {
        _historyState.value = HistoryState.Loading
        viewModelScope.launch {
            try {
                quizRepository.deleteOldQuizRecords()
                quizRepository.getQuiz()?.let { _quizzes.addAll(it) }
                delay(500)
                _historyState.value = HistoryState.Success
            } catch (e: Exception) {
                _historyState.value = HistoryState.Error
            }
        }
    }
}