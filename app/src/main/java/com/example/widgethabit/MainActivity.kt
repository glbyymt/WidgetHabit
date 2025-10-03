package com.example.widgethabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.widgethabit.databinding.ActivityMainBinding
import com.example.widgethabit.databinding.HabitItemBinding

class MainActivity : AppCompatActivity() {

    // viewBindingのための設定
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewBindingを初期化して画面をセット
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 表示するダミーデータを作成
        val habitList = listOf("筋トレ", "勉強", "水を飲む", "散歩する", "読書")

        // 2. アダプターを作成して、RecyclerViewにセットする
        val adapter = HabitAdapter(habitList)
        binding.habitsRecyclerView.adapter = adapter
        binding.habitsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}