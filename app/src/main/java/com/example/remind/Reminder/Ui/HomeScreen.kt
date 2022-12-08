package com.example.remind.Reminder.Ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remind.R
import com.example.remind.Reminder.Model.Task
import com.example.remind.Reminder.Service.AlarmService
import com.example.remind.Reminder.Viewmodal.TaskViewModel
import com.example.remind.databinding.FragmentHomeScreenBinding
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeScreen : Fragment() {


    private lateinit var binding: FragmentHomeScreenBinding
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



        }










    }




}