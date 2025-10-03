package com.example.widgethabit

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.widgethabit.databinding.HabitItemBinding

class HabitAdapter(
    private val habits: List<Habit>,
    private val onHabitLongClicked: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(val binding: HabitItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.binding.habitTitleTextView.text = habit.title

        if (habit.isCompleted) {
            holder.binding.habitTitleTextView.paintFlags = holder.binding.habitTitleTextView.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.habitTitleTextView.setTextColor(android.graphics.Color.GRAY)
        } else {
            holder.binding.habitTitleTextView.paintFlags = holder.binding.habitTitleTextView.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.binding.habitTitleTextView.setTextColor(android.graphics.Color.BLACK)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, HabitDetailActivity::class.java).apply {
                putExtra("HABIT_ID", habit.id)
                putExtra("HABIT_IS_COMPLETED", habit.isCompleted)
                putExtra("HABIT_TITLE", habit.title)
            }
            context.startActivity(intent)
        }

        // 長押しされたときの処理
        holder.itemView.setOnLongClickListener {
            onHabitLongClicked(habit)
            true // trueを返すと、イベントがここで完了したことを示す
        }
    }

    override fun getItemCount(): Int {
        return habits.size
    }
}