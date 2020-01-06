package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.todaysMedicines.showAllMedicinesToday.ShowAllTodaysMedicines
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.notificationManager
import org.jetbrains.anko.uiThread
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.os.PowerManager



class ForegroundService : Service() {
    companion object {
        val CHANNEL_ID = "ForegroundServiceChannel"
        val CHANNEL_ID_CHILD = "ForegroundServiceChannelCHILD"
        private var isRunning = true
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getIntExtra("time",15)
        createNotificationChannel()
        val notificationIntent = Intent(this, Menu::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )




        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Twoja Elektroniczna Apteczka")
            .setContentText("Dbamy o Twoje zdrowie")
            .setSmallIcon(com.example.agnieszka.ar_apteczka.R.drawable.pills)
            .setOnlyAlertOnce(true)
            //.setContentIntent(pendingIntent)
            //.setSound(null)
            .build()



        startForeground(1, notification)
        isRunning = true
        val context = this
        val intent = Intent(this, ShowAllTodaysMedicines::class.java)
        val pendingIntentNotification = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        doAsync {
            while(true)
            {
                var message= createReminderMessage(context)

                uiThread {
                        if(true) {
                            if (message != "Nadszedł czas by zażyć: ") {
                                val notification =
                                    NotificationCompat.Builder(context, CHANNEL_ID_CHILD)
                                        .setContentTitle("Zażyj leki")
                                        .setContentText(message)
                                        .setSmallIcon(com.example.agnieszka.ar_apteczka.R.drawable.pills)
                                        .setContentIntent(pendingIntentNotification)
                                        .setAutoCancel(true)
                                        .build()
                                with(NotificationManagerCompat.from(context)) {
                                    notificationManager.notify(2, notification)
                                }
                            }
                        }
                }
                SystemClock.sleep(10000)
                //SystemClock.sleep(60*1000-SystemClock.elapsedRealtime()%1000)
               // SystemClock.sleep(50000) //10*1000-SystemClock.elapsedRealtime()%1000
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                //NotificationManager.IMPORTANCE_DEFAULT
                NotificationManager.IMPORTANCE_DEFAULT
            )
            //serviceChannel.setSound(null, null) //
            val serviceChannel2 = NotificationChannel(
                CHANNEL_ID_CHILD,
                "Foreground Service ChannelChild ",
                NotificationManager.IMPORTANCE_DEFAULT
                //NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
            manager.createNotificationChannel(serviceChannel2)
        }
    }

    fun reminderForNow(context: Context) : ArrayList<Reminder> {
        var listOfReminder : ArrayList<Reminder> = ArrayList()
        var timetoday = takeTimeNow()
        var dateToday = takeTodayDate()

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
        var message = "Nadszedł czas by zażyć: "
        var listOfReminders = reminderForNow(p0)
        if(listOfReminders.count() > 0){
            for (i: Reminder in listOfReminders) {
                message +=  i.medicineName + ", "
            }
        }
        return message
    }

 private fun takeTodayDate():String{
        val current = LocalDateTime.now()
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
     var currentDate = current.format(formatDate).toString()
        return  currentDate
    }

    private fun takeTimeNow() : String{
        val current = LocalDateTime.now()
        val formatTime = DateTimeFormatter.ofPattern("HH:mm")
        return current.format(formatTime).toString()
    }
}