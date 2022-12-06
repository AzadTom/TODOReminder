package com.example.remind.Reminder.LocalNotification

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.remind.Reminder.Ui.HomeScreen
import android.app.NotificationManager as NotificationManager1


class NotificationHelper(private val context: Context) {

    companion object{

        private const val CHANNEL_ID ="channel_id"
        private const val NOTIFICATION_ID = 101
    }


    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, android.app.NotificationManager.IMPORTANCE_HIGH ).apply {
                description = "Reminder Channel Description"
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager1
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun createNotification(title: String, message: String){
        // 1
        createNotificationChannel()
        // 2
        val intent = Intent(context,HomeScreen:: class.java)


        // 3
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        // 5
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
//        notification.setLights(Color.BLUE, 500, 500)
//        val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500)
//        notification.setVibrate(pattern)
//        var alarmSound  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
//        notification.setSound(alarmSound)
//        notification.build()
        // 6
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID,notification)


    }
}