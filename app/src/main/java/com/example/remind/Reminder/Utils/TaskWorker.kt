package com.example.remind.Reminder.Utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.remind.Reminder.LocalNotification.NotificationHelper

class TaskWorker(private val context: Context, private val params: WorkerParameters) : Worker(context, params){
    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString())

        return Result.success()
    }
}