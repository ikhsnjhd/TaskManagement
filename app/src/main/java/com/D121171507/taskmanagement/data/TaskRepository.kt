package com.D121171507.taskmanagement.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.D121171507.taskmanagement.data.model.Task
import com.D121171507.taskmanagement.data.room.TaskDao
import com.D121171507.taskmanagement.data.room.TaskRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TaskRepository(application: Application) {
    private val mTaskDao: TaskDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = TaskRoomDatabase.getDatabase(application)
        mTaskDao = db.taskDao()
    }
    fun getAllTasks(): LiveData<List<Task>> = mTaskDao.getAllNotes()
    fun insert(task: Task) {
        executorService.execute { mTaskDao.insert(task) }
    }
    fun delete(task: Task) {
        executorService.execute { mTaskDao.delete(task) }
    }
    fun update(task: Task) {
        executorService.execute { mTaskDao.update(task) }
    }

}