package com.example.remind.Reminder.Ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remind.R
import com.example.remind.Reminder.LocalNotification.NotificationHelper
import com.example.remind.Reminder.Model.Task
import com.example.remind.Reminder.Viewmodal.TaskViewModel
import com.example.remind.databinding.FragmentHomeScreenBinding
import kotlinx.coroutines.launch

class HomeScreen : Fragment() {


    private lateinit var binding: FragmentHomeScreenBinding
    private var list: List<Task> = ArrayList()
    private val sharedViewModel by activityViewModels<TaskViewModel>()
    private val adapter: TaskListAdapter by lazy { TaskListAdapter(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment return inflater.inflate(R.layout.fragment_home_screen, container, false)
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = adapter


        insertOperation()
        updated()







        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeScreen_to_addTask)
        }


        return binding.root
    }


    private fun insertOperation() {

        adapter.listenerDelete = {

            createNotificationChannel(requireContext(), it.notificationid)

            sharedViewModel.deleteTask(it)


        }
    }

    private fun createNotificationChannel(context: Context, notificationid: Int) {


        val vibrate = longArrayOf(200, 500, 200, 500, 200, 500)
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()

        val sound = Uri.parse(
            "android.resource://"
                    + context.packageName + "/" + R.raw.notification
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationHelper.CHANNEL_ID,
                NotificationHelper.CHANNEL_ID,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminder Channel Description"
                enableLights(true)
                enableVibration(true)
                vibrationPattern = vibrate
                setSound(
                    sound,
                    audioAttributes
                )
                lightColor = Color.YELLOW
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
            notificationManager.cancel(notificationid)


        }
    }

    private fun updated() {


        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())


        lifecycle.coroutineScope.launch {
            sharedViewModel.getAllTask().collect() {

                list = it


                if (binding.tilTitle.text.isNullOrEmpty())
                {
                    adapter.updateList(list)
                }
                 search()

            }




        }


    }

    private fun search() {
        binding.tilTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {


                filter(s.toString())


            }


        })
    }

    private fun filter(text: String) {

        val temp: ArrayList<Task> = ArrayList()

        for (i in list) {

            if (i.taskDescription.lowercase().contains(text.lowercase())) {
                temp.add(i)


            }

        }

        adapter.updateList(temp)


    }




}