package com.example.widgethabit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.widgethabit.databinding.HabitItemBinding

// habitsという文字列のリストを受け取るアダプター
class HabitAdapter(private val habits: List<String>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    // 各行の見た目を保持するViewHolder
    class HabitViewHolder(val binding: HabitItemBinding) : RecyclerView.ViewHolder(binding.root)

    // 新しいViewHolder（一行分の見た目）を作成する時に呼ばれる
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    // ViewHolderにデータを表示する時に呼ばれる
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habitTitle = habits[position]
        holder.binding.habitTitleTextView.text = habitTitle
    }

    // リストの項目数を返す
    override fun getItemCount(): Int {
        return habits.size
    }
}