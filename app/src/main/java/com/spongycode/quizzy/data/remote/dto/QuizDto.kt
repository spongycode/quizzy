package com.spongycode.quizzy.data.remote.dto

import com.spongycode.quizzy.domain.model.Question

data class QuizDto(
    val responseCode: Int,
    val results: List<QuizQuestion>
) {
    fun toListQuestions(): List<Question> = results.map {
        Question(
            type = it.type,
            difficulty = it.difficulty,
            category = it.category,
            question = it.question,
            correctAnswer = it.correct_answer,
            incorrectAnswers = it.incorrect_answers
        )
    }
}

data class QuizQuestion(
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)