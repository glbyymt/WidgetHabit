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

        // IntentからIDとタイトルを受け取る
        val habitId = intent.getIntExtra("HABIT_ID", -1)
        val habitTitle = intent.getStringExtra("HABIT_TITLE")
        binding.habitDetailTitle.text = habitTitle

        // IDが不正なら画面を閉じる
        if (habitId == -1) {
            finish()
            return
        }

        binding.completeButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // ★★★ id = habitId を追加 ★★★
                val habit = Habit(id = habitId, title = habitTitle ?: "", isCompleted = true, excuse = null)
                habitDao.updateHabit(habit)
            }
            Toast.makeText(this, "$habitTitle を達成しました！", Toast.LENGTH_SHORT).show()
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