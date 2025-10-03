package com.example.widgethabit

// データクラス
data class Habit(
    val title: String,
    var isCompleted: Boolean,
    var excuse: String? // null許容型
)