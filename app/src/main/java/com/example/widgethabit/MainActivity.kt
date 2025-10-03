package com.example.widgethabit

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.widgethabit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    private lateinit var habitDao: HabitDao
    private lateinit var adapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        habitDao = database.habitDao()

        binding.habitsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.addHabitButton.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_habit, null)
            val editText = dialogView.findViewById<android.widget.EditText>(R.id.editTextHabitTitle)

            AlertDialog.Builder(this)
                .setTitle("新しい習慣を追加")
                .setView(dialogView)
                .setPositiveButton("追加") { dialog, _ ->
                    val title = editText.text.toString()
                    if (title.isNotBlank()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val newHabit = Habit(title = title, isCompleted = false, excuse = null)
                            habitDao.insertHabit(newHabit)
                            loadHabits()
                        }
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("キャンセル", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        loadHabits()
    }

    private fun loadHabits() {
        CoroutineScope(Dispatchers.IO).launch {
            val habitList = habitDao.getAllHabits()
            withContext(Dispatchers.Main) {
                // Adapterに、長押しされた習慣を削除するための処理を渡す
                adapter = HabitAdapter(habitList) { habitToDelete ->
                    showDeleteConfirmationDialog(habitToDelete)
                }
                binding.habitsRecyclerView.adapter = adapter
            }
        }
    }

    // 削除を確認するダイアログを表示し、OKならDBから削除する関数
    private fun showDeleteConfirmationDialog(habit: Habit) {
        AlertDialog.Builder(this)
            .setTitle("習慣の削除")
            .setMessage("「${habit.title}」を本当に削除しますか？")
            .setPositiveButton("削除") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    habitDao.deleteHabit(habit)
                    loadHabits() // リストを再読み込みして画面に反映
                }
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }
}