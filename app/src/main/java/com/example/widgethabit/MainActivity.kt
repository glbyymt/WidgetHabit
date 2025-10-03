package com.example.widgethabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        // データベースのインスタンスを取得
        database = AppDatabase.getInstance(this)
        habitDao = database.habitDao()

        // RecyclerViewのセットアップ
        binding.habitsRecyclerView.layoutManager = LinearLayoutManager(this)

        // 新しい習慣を追加するボタンの処理（サンプル）
        binding.addHabitButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val newHabit = Habit(title = "新しい習慣 ${System.currentTimeMillis() % 100}", isCompleted = false, excuse = null)
                habitDao.insertHabit(newHabit)
                loadHabits() // リストを再読み込み
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 画面が表示されるたびに、データベースから最新の習慣リストを読み込む
        loadHabits()
    }

    private fun loadHabits() {
        // Coroutineを使ってバックグラウンドでデータベースからデータを取得
        CoroutineScope(Dispatchers.IO).launch {
            val habitList = habitDao.getAllHabits()
            // UIの更新はメインスレッドで行う
            withContext(Dispatchers.Main) {
                adapter = HabitAdapter(habitList)
                binding.habitsRecyclerView.adapter = adapter
            }
        }
    }
}