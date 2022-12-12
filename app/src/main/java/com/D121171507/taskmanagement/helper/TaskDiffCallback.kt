package com.D121171507.taskmanagement.helper

import androidx.recyclerview.widget.DiffUtil
import com.D121171507.taskmanagement.data.model.Task

class TaskDiffCallback(private val mOldTaskList: List<Task>, private val mNewTaskList: List<Task>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldTaskList.size
    }
    override fun getNewListSize(): Int {
        return mNewTaskList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldTaskList[oldItemPosition].id == mNewTaskList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTask = mOldTaskList[oldItemPosition]
        val newTask = mNewTaskList[newItemPosition]
        return oldTask.title == newTask.title && oldTask.description == newTask.description && oldTask.category == newTask.category
    }
}