package com.example.widgethabit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// このデータベースがどのEntityを持つか、バージョンは何かを定義
@Database(entities = [Habit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // このデータベースがどのDAOを持っているかを定義
    abstract fun habitDao(): HabitDao

    // シングルトンパターン：アプリ内でデータベースのインスタンスが一つだけ存在するようにする
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "habit_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}