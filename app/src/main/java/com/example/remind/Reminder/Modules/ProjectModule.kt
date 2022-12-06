package com.example.remind.Reminder.Modules

import android.content.Context
import androidx.room.Room
import com.example.remind.Reminder.Model.TaskDao
import com.example.remind.Reminder.Model.TaskDatabase
import com.example.remind.Reminder.Viewmodal.TaskRepositery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProjectModule {

    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context):TaskDatabase
    {

        return Room.databaseBuilder(context,TaskDatabase::class.java,"task").build()

    }


    @Provides
    @Singleton
    fun provideTaskDaoInstance(taskDatabase: TaskDatabase):TaskDao{

        return taskDatabase.TaskDao()


    }


    @Provides
    @Singleton
    fun provideTaskRepoInstance(taskDao: TaskDao):TaskRepositery
    {
        return TaskRepositery(taskDao)
    }








}