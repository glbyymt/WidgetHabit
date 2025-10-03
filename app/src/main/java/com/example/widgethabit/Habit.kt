package com.example.widgethabit

import androidx.room.Entity
import androidx.room.PrimaryKey

// 「habits」という名前のテーブルになることを示す
@Entity(tableName = "habits")
data class Habit(
    // 主キー。採番は自動
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    var isCompleted: Boolean,
    var excuse: String?
)