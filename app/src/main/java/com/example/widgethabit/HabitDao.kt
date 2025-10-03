package com.example.widgethabit

import androidx.room.*

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY id DESC")
    suspend fun getAllHabits(): List<Habit>

    @Insert
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)
}