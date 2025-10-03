package com.example.widgethabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.widgethabit.databinding.ActivityHabitDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHabitDetailBinding
    private lateinit var habitDao: HabitDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        habitDao = AppDatabase.getInstance(this).habitDao()

        val habitId = intent.getIntExtra("HABIT_ID", -1)
        val habitIsCompleted = intent.getBooleanExtra("HABIT_IS_COMPLETED", false)
        val habitTitle = intent.getStringExtra("HABIT_TITLE")
        binding.habitDetailTitle.text = habitTitle

        // IDが不正なら画面を閉じる
        if (habitId == -1) {
            finish()
            return
        }

        // 達成状態に応じてボタンのテキストを変更
        if (habitIsCompleted) {
            binding.completeButton.text = "未達成に戻す"
        } else {
            binding.completeButton.text = "達成！"
        }

        binding.completeButton.setOnClickListener {
            // 現在の状態を反転させた新しい状態を作る
            val newIsCompleted = !habitIsCompleted
            CoroutineScope(Dispatchers.IO).launch {
                val habit = Habit(id = habitId, title = habitTitle ?: "", isCompleted = newIsCompleted, excuse = null)
                habitDao.updateHabit(habit)
            }
            val message = if (newIsCompleted) "達成しました！" else "未達成に戻しました"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.excuseButton.setOnClickListener {
            showExcuseDialog(habitId, habitTitle)
        }
    }

    private fun showExcuseDialog(habitId: Int, habitTitle: String?) {
        val excuses = arrayOf("疲労", "飲み会", "時間がない", "その他")
        AlertDialog.Builder(this)
            .setTitle("できなかった理由は？")
            .setItems(excuses) { _, which ->
                val selectedExcuse = excuses[which]
                CoroutineScope(Dispatchers.IO).launch {
                    // ★★★ id = habitId を追加 ★★★
                    val habit = Habit(id = habitId, title = habitTitle ?: "", isCompleted = false, excuse = selectedExcuse)
                    habitDao.updateHabit(habit)
                }
                Toast.makeText(this, "理由「$selectedExcuse」を記録しました", Toast.LENGTH_SHORT).show()
                finish()
            }
            .create()
            .show()
    }
}