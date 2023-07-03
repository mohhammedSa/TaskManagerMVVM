package com.example.taskmanagermvvm

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagermvvm.DataModel.DataModel

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

    class MyViewHold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTV: TextView = itemView.findViewById(R.id.taskTV)
        val delBtn: ImageButton = itemView.findViewById(R.id.delBtn)
        val editBtn: ImageButton = itemView.findViewById(R.id.editBtn)
    }

    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onBindViewHolder(holder: MyViewHold, position: Int) {
        val item = list[position]
        holder.taskTV.text = item.task

        holder.delBtn.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
        }
        holder.editBtn.setOnClickListener {
            val alert = AlertDialog.Builder(context)
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.update_alert_layout, null)
            alert.setView(view)
            val updateAlert = alert.create()
            updateAlert.show()

            val updateET: EditText = view.findViewById(R.id.updateTaskEt)
            val updateBtn: Button = view.findViewById(R.id.updateBtn)
            val editableOldText = Editable.Factory().newEditable(holder.taskTV.text.toString())
            updateET.text = editableOldText

            updateBtn.setOnClickListener {
                val newTask = updateET.text.toString()
                holder.taskTV.text = newTask
                updateET.text.clear()
                updateAlert.dismiss()
            }
        }
    }
}