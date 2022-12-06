package com.example.remind.Reminder.Ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.remind.R
import com.example.remind.Reminder.Model.Task
import com.example.remind.Reminder.Utils.TaskWorker
import com.example.remind.Reminder.Viewmodal.TaskViewModel
import com.example.remind.databinding.FragmentHomeScreenBinding
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class HomeScreen : Fragment() {


    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var mDateSplit: Array<String>
    private lateinit var mTimeSplit: Array<String>
    private var mYear = 0
    private var mMonth = 0
    private var mHour = 0
    private var mMinute = 0
    private var mDay = 0
    private lateinit var mCalendar: Calendar

    private var list : List<Task> = ArrayList()
    private val sharedViewModel by activityViewModels<TaskViewModel>()
    private val adapter: TaskListAdapter by lazy { TaskListAdapter(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment return inflater.inflate(R.layout.fragment_home_screen, container, false)

        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)



        updated()

        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeScreen_to_addTask)
        }


        return binding.root
    }

    private fun updated() {



        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        lifecycle.coroutineScope.launch {
            sharedViewModel.getAllTask().collect() {
                    list = it

                if (list.isEmpty())
                {
                    binding.recyclerView.visibility = View.INVISIBLE
                }else
                {
                    binding.imageView2.visibility = View.INVISIBLE
                }
                adapter.submitList(list)
            }

           setTask(list)

        }








    }

    private fun setTask(list: List<Task>) {

        for (item in list){

            mDateSplit = item.date.split("-").toTypedArray()
            mTimeSplit = item.time.split(":").toTypedArray()
            mDay = mDateSplit[0].toInt()
            mMonth = mDateSplit[1].toInt()
            mYear = mDateSplit[2].toInt()
            mHour = mTimeSplit[0].toInt()
            mMinute = mTimeSplit[1].toInt()
            mCalendar.set(Calendar.MONTH, --mMonth)
            mCalendar.set(Calendar.YEAR, mYear)
            mCalendar.set(Calendar.DAY_OF_MONTH, mDay)
            mCalendar.set(Calendar.HOUR_OF_DAY, mHour)
            mCalendar.set(Calendar.MINUTE, mMinute)
            mCalendar.set(Calendar.SECOND, 0)

            // Calculate notification time
            val c: Calendar = Calendar.getInstance()
            val currentTime: Long = c.getTimeInMillis()
            val diffTime: Long = mCalendar.getTimeInMillis() - currentTime


            createWorkRequest(item.taskDescription, diffTime)



            Toast.makeText(requireContext(), "Set Reminder!", Toast.LENGTH_SHORT).show()


        }

    }

    fun createWorkRequest(message: String, timeDelayInSeconds: Long) {

        val myWorkRequest = OneTimeWorkRequestBuilder<TaskWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to "Reminder",
                    "message" to message,
                )
            )
            .build()

        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
    }

}