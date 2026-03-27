package com.example.todoreminderapp.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StopReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val stopIntent = Intent(context, AlarmService::class.java)
        context.stopService(stopIntent)

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = intent.getIntExtra("notification_id", -1)

        if (id != -1) {
            manager.cancel(id)
        }
    }
}