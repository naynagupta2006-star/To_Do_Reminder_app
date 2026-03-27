package com.example.todoreminderapp.data.db

import android.content.*
import android.database.sqlite.*
import com.example.todoreminderapp.data.model.Task

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "TaskDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS tasks(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "time LONG)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun insert(task: Task) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("title", task.title)
        cv.put("time", task.time)
        db.insert("tasks", null, cv)
    }

    fun getAll(): MutableList<Task> {
        val list = mutableListOf<Task>()
        val db = readableDatabase

        try {
            val c = db.rawQuery("SELECT * FROM tasks", null)

            if (c.moveToFirst()) {
                do {
                    val id = c.getInt(0)
                    val title = c.getString(1)
                    val time = c.getLong(2)

                    list.add(Task(id, title, time))
                } while (c.moveToNext())
            }

            c.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

    fun delete(id: Int) {
        writableDatabase.delete("tasks", "id=?", arrayOf(id.toString()))
    }

    // 🔥 NEW: UPDATE FUNCTION (EDIT FEATURE)
    fun update(task: Task) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("title", task.title)
        cv.put("time", task.time)

        db.update(
            "tasks",
            cv,
            "id=?",
            arrayOf(task.id.toString())
        )
    }
}