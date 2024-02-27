package com.spongycode.quizzy.data.repository

import com.spongycode.quizzy.data.local.QuizDao
import com.spongycode.quizzy.data.local.entity.QuizEntity
import com.spongycode.quizzy.data.remote.QuizApi
import com.spongycode.quizzy.domain.model.Category
import com.spongycode.quizzy.domain.model.Question
import com.spongycode.quizzy.domain.repository.QuizRepository
import com.spongycode.quizzy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val api: QuizApi,
    private val dao: QuizDao
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
                val questions = api.getQuestions(
                    amount = amount.toString(),
                    category = if (category != 0) category.toString() else "",
                    difficulty = if (difficulty != "mix") difficulty else "",
                    type = type
                )
                    .toListQuestions()
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

    override suspend fun insertQuiz(quiz: QuizEntity) {
        dao.insertQuiz(quiz = quiz)
    }

    override suspend fun getQuiz(): List<QuizEntity>? {
        return dao.getQuiz()
    }

    override suspend fun deleteOldQuizRecords() {
        dao.deleteOldQuizRecords()
    }
}