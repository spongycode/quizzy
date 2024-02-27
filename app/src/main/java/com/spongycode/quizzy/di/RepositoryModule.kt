package com.spongycode.quizzy.di

import com.spongycode.quizzy.data.local.QuizDatabase
import com.spongycode.quizzy.data.remote.QuizApi
import com.spongycode.quizzy.data.repository.QuizRepositoryImpl
import com.spongycode.quizzy.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun bindQuizRepository(
        api: QuizApi,
        db: QuizDatabase,
    ): QuizRepository {
        return QuizRepositoryImpl(api = api, dao = db.dao)
    }
}