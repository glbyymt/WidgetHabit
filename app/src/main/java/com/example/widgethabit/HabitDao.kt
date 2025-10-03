package com.example.widgethabit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY id DESC")
    suspend fun getAllHabits(): List<Habit> // suspend を追加

    @Insert
    suspend fun insertHabit(habit: Habit) // suspend を追加

    @Update
    suspend fun updateHabit(habit: Habit) // suspend を追加
}