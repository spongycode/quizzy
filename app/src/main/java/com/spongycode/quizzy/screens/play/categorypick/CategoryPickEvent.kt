package com.spongycode.quizzy.screens.play.categorypick

sealed class CategoryPickEvent {
    data class TappedAmount(val value: String) : CategoryPickEvent()
    data class TappedDifficulty(val value: String) : CategoryPickEvent()
    data class TappedCategory(val value: String) : CategoryPickEvent()
}
