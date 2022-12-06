package com.example.remind.Reminder.Ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.remind.R
import com.example.remind.databinding.FragmentSplashScreenBinding


@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {


    private lateinit var binding: FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater,container,false)

        activity?.window?.statusBarColor = Color.WHITE



         Handler(Looper.getMainLooper()).postDelayed({


              Navigation.findNavController(requireView()).navigate(R.id.action_splashScreen_to_homeScreen)


         },2000)



        return binding.root
    }


}