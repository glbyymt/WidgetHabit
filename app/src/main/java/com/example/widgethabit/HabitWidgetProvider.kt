package com.example.widgethabit

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

// アクションを識別するための文字列
private const val ACTION_HABIT_CLICK = "com.example.widgethabit.ACTION_HABIT_CLICK"

class HabitWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // 複数のウィジェットが置かれている場合、すべてを更新する
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // ウィジェットがタップされたときなど、ブロードキャストを受け取ったときに呼ばれる
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        // 私たちが設定したクリックアクションの場合のみ、処理を行う
        if (intent.action == ACTION_HABIT_CLICK) {
            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

            // ウィジェットの見た目を操作するための準備
            val views = RemoteViews(context.packageName, R.layout.habit_widget_provider)
            views.setTextViewText(R.id.habit_status, "達成！ ✅") // テキストを「達成！」に変更

            // ウィジェットを更新するための「リモコン」を取得
            val appWidgetManager = AppWidgetManager.getInstance(context)
            appWidgetManager.updateAppWidget(appWidgetId, views) // 特定のウィジェットを更新
        }
    }
}

// ウィジェットの見た目を更新し、クリックイベントを設定する関数
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // ウィジェットのレイアウト(見た目)を取得
    val views = RemoteViews(context.packageName, R.layout.habit_widget_provider)
    // ↓↓↓ ここでエラーが出ていないことに注目！
    views.setTextViewText(R.id.habit_title, "筋トレ")
    views.setTextViewText(R.id.habit_status, "未達成")

    // ウィジェット全体がクリックされたときに発行されるIntentを作成
    val intent = Intent(context, HabitWidgetProvider::class.java).apply {
        action = ACTION_HABIT_CLICK
        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    }

    // PendingIntentを作成。「保留中のインテント」という意味で、すぐに実行せず、特定のタイミング（今回はタップ時）で実行される
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        appWidgetId, // リクエストコードをウィジェットIDごとにユニークにする
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // ウィジェットのルートレイアウトにクリックリスナーを設定
    views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent)

    // ウィジェットマネージャーに、このウィジェットの更新を指示
    appWidgetManager.updateAppWidget(appWidgetId, views)
}