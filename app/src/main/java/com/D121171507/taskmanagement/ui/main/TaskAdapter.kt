package com.D121171507.taskmanagement.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.D121171507.taskmanagement.data.model.Task
import com.D121171507.taskmanagement.databinding.TaskItemBinding
import com.D121171507.taskmanagement.helper.TaskDiffCallback
import com.D121171507.taskmanagement.ui.addtask.AddUpdateNoteActivity

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val listTasks = ArrayList<Task>()
    fun setListNotes(listNotes: List<Task>) {
        val diffCallback = TaskDiffCallback(this.listTasks, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listTasks.clear()
        this.listTasks.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(listTasks[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, AddUpdateNoteActivity::class.java)
            intent.putExtra(AddUpdateNoteActivity.EXTRA_TASK, listTasks[position])
            it.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return listTasks.size
    }
    inner class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            with(binding) {
                taskItemTitle.text = task.title
                taskItemDescription.text = task.description
                taskItemStatus.apply {
                    text = task.category
                    
                }
            }
        }
    }
}