package com.spongycode.quizzy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spongycode.quizzy.data.local.entity.QuizEntity

@Database(
    entities = [QuizEntity::class],
    version = 1
)
abstract class QuizDatabase : RoomDatabase() {
    abstract val dao: QuizDao
}