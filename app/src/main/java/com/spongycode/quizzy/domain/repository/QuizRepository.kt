package com.spongycode.quizzy.domain.repository

import com.spongycode.quizzy.domain.model.Category
import com.spongycode.quizzy.domain.model.Question
import com.spongycode.quizzy.utils.Resource
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String = "multiple"
    ): Flow<Resource<List<Question>>>

    fun getCategories(): Flow<Resource<List<Category>>>
}