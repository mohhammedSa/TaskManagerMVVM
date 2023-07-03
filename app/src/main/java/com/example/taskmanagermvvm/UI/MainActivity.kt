package com.example.taskmanagermvvm.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagermvvm.DataModel.DataModel
import com.example.taskmanagermvvm.MyAdapter
import com.example.taskmanagermvvm.ViewModel.MyViewModel
import com.example.taskmanagermvvm.R
import com.example.taskmanagermvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var myViewModel: MyViewModel = MyViewModel()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        binding.showAddAlertBtn.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            val view: View =
                LayoutInflater.from(this).inflate(R.layout.add_alert_layout, null)
            alert.setView(view)
            val alertDialog = alert.create()
            alertDialog.show()

            val taskEt: EditText = view.findViewById(R.id.taskEt)
            val addBtn: Button = view.findViewById(R.id.addBtn)
            addBtn.setOnClickListener {
                val taskText = taskEt.text.toString()
                val newTask = DataModel(taskText)
                myViewModel.setDataToMutableLiveData(newTask).observe(this) { list ->
                    binding.recyclerView.layoutManager =
                        LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    binding.recyclerView.adapter = MyAdapter(this, R.layout.task_card_layout, list)
                }
                taskEt.text.clear()
                alertDialog.dismiss()
            }
        }
    }
}






















