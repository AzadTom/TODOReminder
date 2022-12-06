package com.example.remind.Reminder.Model

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {



    @Query("SELECT * FROM task ORDER BY date ASC")
     fun getAllTask():Flow<List<Task>>

     @Query("SELECT * FROM task WHERE taskDescription =:description")
     fun getTaskByDes(description:String):Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)


    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)




}