package com.example.widgethabit

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.widgethabit.databinding.HabitItemBinding

class HabitAdapter(private val habits: List<Habit>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    // ViewHolderにクリック処理を追加！
    class HabitViewHolder(val binding: HabitItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // ViewHolderが作成された瞬間に、クリックリスナーを設定する
        init {
            itemView.setOnClickListener {
                // ここではクリックされたことだけを検知。具体的な処理はonBindViewHolderで行う
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.binding.habitTitleTextView.text = habit.title

        // 各行がクリックされたときの処理を、ここで定義する
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, HabitDetailActivity::class.java).apply {
                // ★ IDとタイトルの両方を渡す
                putExtra("HABIT_ID", habit.id)
                putExtra("HABIT_TITLE", habit.title)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return habits.size
    }
}