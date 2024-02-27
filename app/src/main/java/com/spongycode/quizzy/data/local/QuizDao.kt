package com.spongycode.quizzy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spongycode.quizzy.data.local.entity.QuizEntity

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: QuizEntity)

    @Query("SELECT * FROM QuizEntity LIMIT 10")
    suspend fun getQuiz(): List<QuizEntity>?

    @Query("DELETE FROM QuizEntity WHERE id NOT IN (SELECT id FROM QuizEntity ORDER BY id DESC LIMIT 10)")
    suspend fun deleteOldQuizRecords()
}