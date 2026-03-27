package com.example.todoreminderapp.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.todoreminderapp.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.getStringExtra("title") ?: "Task"
        val id = intent.getIntExtra("id", 0)

        val channelId = "task_channel"

        // ✅ Create notification channel (important for Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Task Reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // 🔔 Start Alarm Service (PASS ID 🔥)
        val serviceIntent = Intent(context, AlarmService::class.java)
        serviceIntent.putExtra("id", id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }

        // 🛑 Stop button (same ID)
        val stopIntent = Intent(context, StopReceiver::class.java)

        val stopPendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 🔥 Notification with sound + vibration
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("🔔 Reminder")
            .setContentText("📌 $title")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL) // 🔥 sound + vibration
            .addAction(0, "🛑 Stop", stopPendingIntent)
            .setAutoCancel(true)
            .build()

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(id, notification) // 🔥 unique notification
    }
}