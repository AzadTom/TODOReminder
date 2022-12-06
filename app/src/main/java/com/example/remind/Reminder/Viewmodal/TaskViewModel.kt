package com.example.remind.Reminder.Viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remind.Reminder.Model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepositery: TaskRepositery) : ViewModel() {


    //GetAlltask
    fun getAllTask() = taskRepositery.getAllTask()

    //insertTask
    fun insertTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {

        taskRepositery.insertTask(task)
    }

    //updateTask

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {

        taskRepositery.updateTask(task)
    }


    //deleteTask
    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskRepositery.deleteTask(task)
    }





}