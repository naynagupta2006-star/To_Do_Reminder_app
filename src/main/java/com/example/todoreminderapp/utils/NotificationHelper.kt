package com.example.todoreminderapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.todoreminderapp.receiver.AlarmReceiver

class NotificationHelper(private val context: Context) {


    fun setReminder(time: Long, title: String, requestCode: Int) {

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("title", title)
        intent.putExtra("id", requestCode)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode, // 🔥 same id use hogi
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        am.set(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }
    }
