package com.D121171507.taskmanagement.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.D121171507.taskmanagement.databinding.ActivityMainBinding
import com.D121171507.taskmanagement.ui.ViewModelFactory
import com.D121171507.taskmanagement.ui.addtask.AddUpdateNoteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        val mainViewModel = obtainViewModel(this)
        mainViewModel.getAllTasks().observe(this) { tasksList ->
            if (tasksList != null) {
                adapter.setListNotes(tasksList)
            }
        }

        binding.fabAdd.setOnClickListener {
            startActivity(
                Intent(this, AddUpdateNoteActivity::class.java)
            )
        }
    }

    private fun setupAdapter() {
        adapter = TaskAdapter()
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}