package com.D121171507.taskmanagement.ui.addtask

import android.app.Application
import androidx.lifecycle.ViewModel
import com.D121171507.taskmanagement.data.TaskRepository
import com.D121171507.taskmanagement.data.model.Task

class AddUpdateTaskViewModel(application: Application): ViewModel() {
    private val mTaskRepository: TaskRepository = TaskRepository(application)
    fun insert(task: Task) {
        mTaskRepository.insert(task)
    }
    fun update(task: Task) {
        mTaskRepository.update(task)
    }
    fun delete(task: Task) {
        mTaskRepository.delete(task)
    }
}