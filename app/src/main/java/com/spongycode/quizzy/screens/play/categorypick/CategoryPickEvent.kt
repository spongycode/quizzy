package com.spongycode.quizzy.screens.play.categorypick

import com.spongycode.quizzy.domain.model.Category

sealed class CategoryPickEvent {
    data class TappedAmount(val value: String) : CategoryPickEvent()
    data class TappedDifficulty(val value: String) : CategoryPickEvent()
    data class TappedCategory(val value: Category) : CategoryPickEvent()
}
