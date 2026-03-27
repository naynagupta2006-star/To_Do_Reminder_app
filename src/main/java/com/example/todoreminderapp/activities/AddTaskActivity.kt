package com.example.todoreminderapp.activities

import android.app.*
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.todoreminderapp.R
import com.example.todoreminderapp.data.db.DatabaseHelper
import com.example.todoreminderapp.data.model.Task
import com.example.todoreminderapp.utils.NotificationHelper
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    lateinit var etTask: EditText
    lateinit var etTime: EditText
    lateinit var btnSave: Button
    lateinit var db: DatabaseHelper

    var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        etTask = findViewById(R.id.etTask)
        etTime = findViewById(R.id.etTime)
        btnSave = findViewById(R.id.btnSave)

        db = DatabaseHelper(this)

        etTime.setOnClickListener {
            val cal = Calendar.getInstance()

            DatePickerDialog(this, { _, y, m, d ->
                TimePickerDialog(this, { _, h, min ->
                    cal.set(y, m, d, h, min, 0)
                    time = cal.timeInMillis
                    etTime.setText("$d/${m + 1}/$y $h:$min")
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnSave.setOnClickListener {

            val title = etTask.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "Enter task!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (time == 0L) {
                Toast.makeText(this, "Select time!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.insert(Task(0, title, time))
            Toast.makeText(this, "Task Saved ✅", Toast.LENGTH_SHORT).show()

            // 🔥 UNIQUE ID (MULTIPLE TASK FIX)
            val requestCode = System.currentTimeMillis().toInt()

            NotificationHelper(this).setReminder(time, title, requestCode)
            finish()
        }
    }
}