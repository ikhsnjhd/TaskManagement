package com.D121171507.taskmanagement.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.D121171507.taskmanagement.data.TaskRepository
import com.D121171507.taskmanagement.data.model.Task

class MainViewModel(application: Application) : ViewModel() {

    private val mTaskRepository: TaskRepository = TaskRepository(application)
    fun getAllTasks(): LiveData<List<Task>> = mTaskRepository.getAllTasks()

}