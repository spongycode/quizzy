package com.spongycode.quizzy.data.remote.dto

import com.spongycode.quizzy.domain.model.Category

data class CategoryDto (
    val trivia_categories: List<Category>
)