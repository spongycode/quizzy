package com.spongycode.quizzy.domain.model

data class Question(
    val type: String,
    val difficulty: String,
    val category: String,
    val title: String,
    val options: List<Option>,
    val correctId: Int,
)

data class Option(
    val id: Int,
    val value: String,
)