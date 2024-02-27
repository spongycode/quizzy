package com.spongycode.quizzy.data.remote.dto

import com.spongycode.quizzy.domain.model.Option
import com.spongycode.quizzy.domain.model.Question

data class QuizDto(
    val responseCode: Int,
    val results: List<QuizQuestion>
) {
    fun toListQuestions(): List<Question> = results.map {
        var idx = 0
        val options = (it.incorrect_answers + it.correct_answer).shuffled().map { value ->
            Option(id = idx++, value = value)
        }
        var correctId = 0
        for (option in options) {
            if (option.value == it.correct_answer) {
                correctId = option.id
                break
            }
        }
        Question(
            type = it.type,
            difficulty = it.difficulty,
            category = it.category,
            title = it.question,
            options = options,
            correctId = correctId
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