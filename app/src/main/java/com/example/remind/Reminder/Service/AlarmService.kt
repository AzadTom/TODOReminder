package com.example.remind.Reminder.Service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.remind.Reminder.Utils.Constans

class AlarmService(private val context: Context) {


    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?


    //exact alarm
    fun setExact(timeMills: Long, message: String, notificationId: Int) {

        setAlarm(timeMills, getPendingIntent(
            getIntent().apply {
                action = Constans.ACTION_SET_EXACT_ALARM
                putExtra(Constans.ACTION_SET_EXACT_ALARM,timeMills)
                putExtra(Constans.MESSAGE,message)
                putExtra(Constans.NOTIFICATIONID,notificationId)
                 }
              )
        )

    }


    //Every Week
    fun setRepetitiveAlarm(timeMills: Long) {

        setAlarm(timeMills, getPendingIntent(
            getIntent().apply {
                action = Constans.ACTION_SET_REPETITIVE_ALARM
                putExtra(Constans.ACTION_SET_REPETITIVE_ALARM,timeMills)
            }
        )
        )

    }


    private fun setAlarm(timeMills: Long, pendingIntent: PendingIntent) {

        alarmManager?.let {

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeMills,
                pendingIntent
            )


        }
    }


    private fun getIntent(): Intent = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent) = PendingIntent.getBroadcast(
        context,
        Constans.getRandomInt(),
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )


}