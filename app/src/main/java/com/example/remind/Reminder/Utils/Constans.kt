package com.example.remind.Reminder.Utils

import java.util.concurrent.atomic.AtomicInteger

class Constans{


    companion object{

        private val seed = AtomicInteger()

        fun getRandomInt(): Int = seed.getAndIncrement() + System.currentTimeMillis().toInt()

        const val ACTION_SET_EXACT_ALARM = "ACTION_SET_EXACT_ALARM"
        const val ACTION_SET_REPETITIVE_ALARM = "ACTION_SET_REPETITIVE_ALARM"
        const val MESSAGE ="MESSAGE"

    }

}
