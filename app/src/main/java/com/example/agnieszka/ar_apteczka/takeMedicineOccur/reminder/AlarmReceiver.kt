package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.R
import android.support.v4.app.NotificationCompat
import android.media.RingtoneManager
import android.app.PendingIntent
import android.support.v4.content.ContextCompat.getSystemService
import android.app.NotificationManager
import com.example.agnieszka.ar_apteczka.todaysMedicines.showAllMedicinesToday.ShowAllTodaysMedicines


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val `when` = System.currentTimeMillis()
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        val notificationIntent = Intent(context, ShowAllTodaysMedicines::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        var MID = 1
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mNotifyBuilder = NotificationCompat.Builder(
            context
        ).setSmallIcon(com.example.agnieszka.ar_apteczka.R.drawable.pills)
            .setContentTitle("Alarm Fired")
            .setContentText("Events to be Performed").setSound(alarmSound)
            .setAutoCancel(true).setWhen(`when`)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        notificationManager?.notify(MID, mNotifyBuilder.build())
        MID++

    }
}