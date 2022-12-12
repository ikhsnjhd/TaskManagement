package com.D121171507.taskmanagement.ui.addtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.D121171507.taskmanagement.R
import com.D121171507.taskmanagement.data.model.Task
import com.D121171507.taskmanagement.databinding.ActivityAddUpdateNoteBinding
import com.D121171507.taskmanagement.ui.ViewModelFactory

class AddUpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateNoteBinding
    private lateinit var viewModel: AddUpdateTaskViewModel
    private lateinit var listCategories: List<String>

    private var isEdit = false
    private var task: Task? = null
    private var categoryStatus: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this)
        setupCategoryList()

        task = intent.getParcelableExtra(EXTRA_TASK)
        if (task != null) {
            isEdit = true
        } else {
            task = Task()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (task != null) {
                task?.let { task ->
                    binding.edtTitle.setText(task.title)
                    binding.menuKategori.setText(task.category, false)
                    binding.edtDescription.setText(task.description)
                }
            }
        } else {
            categoryStatus = listCategories[0]
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle


        binding.btnSubmit.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding.edtTitle.error = getString(R.string.empty)
                }
                description.isEmpty() -> {
                    binding.edtDescription.error = getString(R.string.empty)
                }
                else -> {
                    task.let { task ->
                        task?.title = title
                        task?.category = categoryStatus
                        task?.description = description
                    }
                    if (isEdit) {
                        viewModel.update(task as Task)
                        showToast(getString(R.string.changed))
                    } else {
                        viewModel.insert(task as Task)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
    }

    private fun setupCategoryList() {
        listCategories = listOf(
            "Penting Mendesak",
            "Penting Tidak Mendesak",
            "Tidak Penting Mendesak",
            "Tidak Penting Tidak Mendesak",
        )

        val myAdapter = ArrayAdapter(
            this,
            R.layout.category_item,
            listCategories
        )

        binding.menuKategori.setAdapter(myAdapter)
        binding.menuKategori.setText(listCategories[0], false)
        binding.menuKategori.setOnItemClickListener { _, _, position, _ ->
            categoryStatus = listCategories[position]
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun obtainViewModel(activity: AppCompatActivity): AddUpdateTaskViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AddUpdateTaskViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    viewModel.delete(task as Task)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    companion object {
        const val EXTRA_TASK = "extra_task"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}
