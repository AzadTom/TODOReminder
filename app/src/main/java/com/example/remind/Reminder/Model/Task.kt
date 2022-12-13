package com.example.remind.Reminder.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var notificationid:Int,
    var taskDescription:String,
    var date:String,
    var time:String
)
