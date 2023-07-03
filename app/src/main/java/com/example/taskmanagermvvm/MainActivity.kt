package com.example.taskmanagermvvm

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagermvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
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

class MyAdapter(
    private val context: Context,
    private val layoutRes: Int,
    private val list: ArrayList<DataModel>
) :
    RecyclerView.Adapter<MyAdapter.MyViewHold>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHold {
        return MyViewHold(LayoutInflater.from(context).inflate(layoutRes, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHold(itemView: View) : ViewHolder(itemView) {
        val taskTV: TextView = itemView.findViewById(R.id.taskTV)
    }

    override fun onBindViewHolder(holder: MyViewHold, position: Int) {
        val item = list[position]
        holder.taskTV.text = item.task
    }
}

data class DataModel(val task: String)

class MyViewModel : ViewModel() {
    private val repo: RepoInter = Repo()
    fun setDataToMutableLiveData(data: DataModel): LiveData<ArrayList<DataModel>> {
        return repo.sendDataToViewModel(data)
    }
}

class Repo : RepoInter {
    private val mutableData = MutableLiveData<ArrayList<DataModel>>()
    private val list = ArrayList<DataModel>()
    override fun sendDataToViewModel(data: DataModel): LiveData<ArrayList<DataModel>> {
        list.add(data)
        mutableData.postValue(list)
        return mutableData
    }
}

interface RepoInter {
    fun sendDataToViewModel(data: DataModel): LiveData<ArrayList<DataModel>>
}
















