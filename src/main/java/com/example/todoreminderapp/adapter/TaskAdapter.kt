package com.example.todoreminderapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoreminderapp.R
import com.example.todoreminderapp.data.model.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private var list: MutableList<Task>,
    val onDelete: (Task) -> Unit,
    val onEdit: (Task) -> Unit   // 🔥 NEW
) : RecyclerView.Adapter<TaskAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val time: TextView = itemView.findViewById(R.id.tvTime)
        val delete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return VH(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        val task = list[position]

        // 📌 Title
        holder.title.text = "📌 ${task.title}"

        // ⏰ Time format
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        holder.time.text = "⏰ ${sdf.format(Date(task.time))}"

        // 🗑 Delete
        holder.delete.setOnClickListener {
            onDelete(task)
        }

        // ✏️ Edit (click anywhere on item)
        holder.itemView.setOnClickListener {
            onEdit(task)
        }
    }

    fun update(newList: MutableList<Task>) {
        list = newList
        notifyDataSetChanged()
    }
}