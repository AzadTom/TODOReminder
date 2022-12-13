package com.example.remind.Reminder.Ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.remind.R
import com.example.remind.Reminder.Model.Task
import com.example.remind.databinding.TaskItemBinding
import java.util.ArrayList

class TaskListAdapter(private val context: Context) : ListAdapter<Task, TaskListAdapter.taskViewholder>(diffcallback){


    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)
        return taskViewholder(binding)


    }

    override fun onBindViewHolder(holder: taskViewholder, position: Int) {


        holder.bind(getItem(position))




    }

    fun updateList(temp: List<Task>) {

       super.submitList(temp)


    }

    inner class taskViewholder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(task: Task) {
            binding.tvTitle.text = task.taskDescription
            binding.tvDate.text = "${task.date} ${task.time}"
            binding.ivMore.setOnClickListener {
                showPopup(task)
            }
        }

        private fun showPopup(item: Task) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> {
                        listenerEdit(item)

                        Toast.makeText(context, "Edit!", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        listenerDelete(item)
                        Toast.makeText(context, "Delete!", Toast.LENGTH_SHORT).show()
                    }


                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }


    }


    companion object {

        private val diffcallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        }

    }




}

