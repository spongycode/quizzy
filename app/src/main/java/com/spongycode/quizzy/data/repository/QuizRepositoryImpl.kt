package com.spongycode.quizzy.data.repository

import com.spongycode.quizzy.data.remote.QuizApi
import com.spongycode.quizzy.domain.model.Category
import com.spongycode.quizzy.domain.model.Question
import com.spongycode.quizzy.domain.repository.QuizRepository
import com.spongycode.quizzy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class QuizRepositoryImpl(
    private val api: QuizApi
) : QuizRepository {
    override fun getQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Flow<Resource<List<Question>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Loading(data = null))
            try {
                val questions = api.getQuestions().toListQuestions()
                emit(Resource.Success(questions))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "Something went wrong."
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Internet connection error."
                    )
                )
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Something went wrong."
                    )
                )
            }
        } catch (_: Exception) {
        }
    }

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Loading(data = null))
            try {
                val categories = api.getCategories().trivia_categories
                emit(Resource.Success(categories))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "Something went wrong."
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Internet connection error."
                    )
                )
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Something went wrong."
                    )
                )
            }
        } catch (_: Exception) {
        }
    }
}