package com.spongycode.quizzy.screens.history

sealed class HistoryState {
    data object Loading : HistoryState()
    data object Success : HistoryState()
    data object Error : HistoryState()
}