package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent?) {
        Log.i(MyService.TAG, "Otrzymano intencję: " + p1?.getIntExtra("ID",-1))
        //  Toast.makeText(p0, "Powiadomienie",Toast.LENGTH_LONG).show()

        var message : String = createReminderMessage(p0)
        if(message != "Nadszedł czas by zażyć: ") createReminder(p0,p1)

    }

    fun createReminder(p0: Context?, p1:Intent?){
        val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val NOTIFICATION_CHANNEL_ID = "my_channel_id_01"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH)

            // Configure the notification channel.
            notificationChannel.description = "Channel description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager!!.createNotificationChannel(notificationChannel)
        }


        val notificationBuilder = NotificationCompat.Builder(p0!!, NOTIFICATION_CHANNEL_ID)

        //
        var message : String = createReminderMessage(p0)
        //


        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.pills)
            .setTicker("Hearty365")
            //     .setPriority(Notification.PRIORITY_MAX)
            .setContentTitle("Zażyj leki")
            .setContentText(message)
            .setContentInfo("Info")

        notificationManager!!.notify(/*notification id*/1, notificationBuilder.build()

        )
    }

    fun reminderForNow(context: Context) : ArrayList<Reminder> {
        var listOfReminder : ArrayList<Reminder> = ArrayList()
        var timetoday = MyService.takeTimeNow()
        var dateToday = MyService.takeTodayDate()

        val dbHelper = SQLConector(context)
        val allRemindersList = dbHelper.getAllReminders()
        for (i: Reminder in allRemindersList) {

            if (i.reminderDate == dateToday && i.ReminderTime == timetoday) {
                var reminder = Reminder(
                    i.idReminder,
                    i.idTakeMedToday,
                    i.idTakeMedOccur,
                    i.idMedicineType,
                    i.medicineName,
                    i.reminderDate,
                    i.ReminderTime
                )
                listOfReminder.add(reminder)
            }
        }
        return listOfReminder
    }

    private fun createReminderMessage(p0: Context) : String{
        var message : String = "Nadszedł czas by zażyć: "
        var listOfReminders = reminderForNow(p0)
        if(listOfReminders.count() > 0){
            for (i: Reminder in listOfReminders) {
                message += i.medicineName + ", "
            }
        }
        return message
    }


  /*  private fun takeTodayDate():String{
        val current = LocalDateTime.now()
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var dateResult = current.format(formatDate).toString()
        return  dateResult
    }

    private fun takeTimeNow() : String{
        val current = LocalDateTime.now()
        val formatTime = DateTimeFormatter.ofPattern("HH:mm")
        var timeResult = current.format(formatTime).toString()
        return  timeResult

    } */
}