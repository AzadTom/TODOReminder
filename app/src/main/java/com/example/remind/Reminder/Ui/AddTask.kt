package com.example.remind.Reminder.Ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.remind.R
import com.example.remind.Reminder.Model.Task
import com.example.remind.Reminder.Utils.TaskWorker
import com.example.remind.Reminder.Viewmodal.TaskViewModel
import com.example.remind.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext


class AddTask : Fragment() {


    private lateinit var binding: FragmentAddTaskBinding
    private var myear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 0
    private var mMinute = 0
    private var chooseyear = 0
    private var chooseMonth = 0
    private var chooseDay = 0
    private var chooseHour = 0
    private var chooseMinute = 0
    private val sharedViewModel by activityViewModels<TaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)


        listener()
        return binding.root
    }

    private fun listener() {
        binding.tilTimer.setOnClickListener{

            getTime()
        }
        binding.tilDate.setOnClickListener{

            getDate()
        }
        binding.buttonNewTask.setOnClickListener{

            addTask()




        }
        binding.buttonCancel.setOnClickListener{

          Navigation.findNavController(it).popBackStack()
        }
    }







    private fun getTime() {


        //GetCurrentTime
        val calendar = Calendar.getInstance()
        mHour = calendar.get(Calendar.HOUR_OF_DAY)
        mMinute = calendar.get(Calendar.MINUTE)

        // Launch Time Picker Dialog

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            OnTimeSetListener { view, hourOfDay, minute ->
                binding.tilTimer.text = "$hourOfDay:$minute"
                chooseHour =hourOfDay
                chooseMinute = minute

            },
            mHour,
            mMinute,
            false
        )
        timePickerDialog.show()


    }


    private fun getDate() {

        //get CurrentDate

        val calendar = Calendar.getInstance()
        myear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                binding.tilDate.text = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                chooseMonth = monthOfYear
                chooseDay = dayOfMonth
                chooseyear = year

            }, myear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun addTask() {


        val task = Task(id = null,binding.tilTitle.text.toString(),binding.tilDate.text.toString(),binding.tilTimer.text.toString())

        sharedViewModel.insertTask(task)

        Toast.makeText(requireContext(), "Task save successfully!", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(requireView()).popBackStack()




    }





}


