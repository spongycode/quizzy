package com.spongycode.quizzy.screens.play.categorypick

sealed class CategoriesState {
    data object Loading : CategoriesState()
    data object Success : CategoriesState()
    data object Error : CategoriesState()
}