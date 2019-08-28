package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock
import java.util.*
import android.content.Context.ALARM_SERVICE
import android.support.v4.content.ContextCompat.getSystemService



class NotificationUtils {
    fun setNotification(timeInMilliSeconds: Long, activity: Activity) {

        //if (timeInMilliSeconds > 0) {

            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java)

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)


            val calendar: Calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()

            }

            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)


            alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000,
            pendingIntent
        )
        //}





    }
}