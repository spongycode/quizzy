package com.spongycode.quizzy.data.remote

import com.spongycode.quizzy.data.remote.dto.CategoryDto
import com.spongycode.quizzy.data.remote.dto.QuizDto
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 10,
        @Query("category") category: Int = 9,
        @Query("difficulty") difficulty: String = "easy",
        @Query("type") type: String = "multiple",
    ): QuizDto

    @GET("api_category.php")
    suspend fun getCategories(): CategoryDto

    companion object {
        const val BASE_URL = "https://opentdb.com/"
    }
}