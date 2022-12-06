package com.example.remind.Reminder.Viewmodal

import com.example.remind.Reminder.Model.Task
import com.example.remind.Reminder.Model.TaskDao
import javax.inject.Inject

class TaskRepositery @Inject constructor(private val dao: TaskDao) {



    fun getAllTask() = dao.getAllTask()


    suspend fun insertTask(task: Task) = dao.insertTask(task)



    suspend fun updateTask(task: Task) = dao.updateTask(task)

    suspend fun deleteTask(task: Task) = dao.deleteTask(task)





}