package com.spongycode.quizzy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuizEntity(
    val name: String,
    val registrationNumber: String,
    val grade: String,
    val totalQuestions: Int,
    val score: Int,
    val totalCorrectAnswers: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)