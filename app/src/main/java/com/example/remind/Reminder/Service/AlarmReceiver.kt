package com.example.remind.Reminder.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.remind.R
import com.example.remind.Reminder.LocalNotification.NotificationHelper
import com.example.remind.Reminder.Ui.HomeScreen
import com.example.remind.Reminder.Utils.Constans


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


        when (intent.action) {

            Constans.ACTION_SET_EXACT_ALARM -> {

                createNotification(
                    context,
                    "REMIND",
                    intent.getStringExtra(Constans.MESSAGE).toString(),
                    intent.getIntExtra(Constans.NOTIFICATIONID,0)
                )

            }
        }

    }


    private fun createNotificationChannel(context: Context) {


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




        }
    }


    fun createNotification(context: Context, title: String, message: String , notificationId:Int) {
        // 1
        createNotificationChannel(context)
        // 2
        val intent = Intent(context, HomeScreen::class.java)


        val pendingIntent = PendingIntent.getActivity(
            context,
            Constans.getRandomInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val builder = NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.icon)
            .setAutoCancel(true)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationId, builder)





    }




}