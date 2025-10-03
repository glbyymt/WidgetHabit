package com.example.widgethabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.widgethabit.databinding.ActivityHabitDetailBinding

class HabitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHabitDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val habitTitle = intent.getStringExtra("HABIT_TITLE")
        binding.habitDetailTitle.text = habitTitle

        // 「達成！」ボタンがクリックされたときの処理
        binding.completeButton.setOnClickListener {
            // ここで「達成」したというデータを保存する（まだ未実装）
            Toast.makeText(this, "$habitTitle を達成しました！", Toast.LENGTH_SHORT).show()

            // 現在の画面を閉じて、前の画面（リスト）に戻る
            finish()
        }

        // 「言い訳を記録」ボタンがクリックされたときの処理
        binding.excuseButton.setOnClickListener {
            showExcuseDialog()
        }
    }

    // 言い訳を選択するためのダイアログを表示する関数
    private fun showExcuseDialog() {
        val excuses = arrayOf("疲労", "飲み会", "時間がない", "その他")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("できなかった理由は？")
        builder.setItems(excuses) { dialog, which ->
            val selectedExcuse = excuses[which]

            // ここで「言い訳」をデータとして保存する（まだ未実装）
            Toast.makeText(this, "理由「$selectedExcuse」を記録しました", Toast.LENGTH_SHORT).show()

            // 現在の画面を閉じて、前の画面（リスト）に戻る
            finish()
        }
        // ダイアログを表示
        builder.create().show()
    }
}