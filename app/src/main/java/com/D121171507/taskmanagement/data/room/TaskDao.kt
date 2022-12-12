package com.D121171507.taskmanagement.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.D121171507.taskmanagement.data.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * from task ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Task>>
}