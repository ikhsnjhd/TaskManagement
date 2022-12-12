package com.D121171507.taskmanagement.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.D121171507.taskmanagement.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskRoomDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): TaskRoomDatabase {
            if (INSTANCE == null) {
                synchronized(TaskRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TaskRoomDatabase::class.java, "tasks_database")
                        .build()
                }
            }
            return INSTANCE as TaskRoomDatabase
        }
    }
}