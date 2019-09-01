package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.MedicineType
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.todaysMedicines.ShowAllTodaysMedicines
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NotificationService : IntentService("NotificationService") {
    private lateinit var mNotification: Notification
    private val mNotificationId: Int = 1000
    var timeformat = SimpleDateFormat("HH:mm", Locale.US)

    @SuppressLint("NewApi")
    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val context = this.applicationContext
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.parseColor("#e8334a")
            notificationChannel.description = "Alaaa"
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
            Log.d("Menu", "Działa ")
        }
    }

    companion object {

        const val CHANNEL_ID = "samples.notification.devdeeds.com.CHANNEL_ID"
        const val CHANNEL_NAME = "Sample Notification"
    }

    override fun onHandleIntent(intent: Intent?) {
        //Create Channel
        createChannel()

        var timestamp: Long = 0
        if (intent != null && intent.extras != null) {
            timestamp = intent.extras!!.getLong("timestamp")
        }

        if (timestamp > 0) {
            val context = this.applicationContext
            var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifyIntent = Intent(this, ShowAllTodaysMedicines::class.java)

            var message : String = "Nadszedł czas by zażyć: "
            var listOfReminders = reminderForNow()
            if(listOfReminders.count() > 0){
                for (i: Reminder in listOfReminders) {
                    message += i.medicineName + ", "
                }
            }

            notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp


            val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val res = this.resources
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                mNotification = Notification.Builder(this, CHANNEL_ID)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.pills)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setContentTitle("Weź leki")
                    .setStyle(
                        Notification.BigTextStyle()
                            .bigText(message))
                    .setContentText("a to wiadomość").build()
            } else {

                mNotification = Notification.Builder(this)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.pills)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentTitle("Weż leki")
                    .setStyle(
                        Notification.BigTextStyle()
                            .bigText("Duży tekst"))
                    .setSound(uri)
                    .setContentText("a to wiadomość").build()

            }

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(mNotificationId, mNotification)
        }
    }

    fun reminderForNow() : ArrayList<Reminder> {
        var listOfReminder : ArrayList<Reminder> = ArrayList()
        var timetoday = takeTimeNow()
        var dateToday = takeTodayDate()

        val dbHelper = SQLConector(this)
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


    private fun takeTodayDate():String{
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

    }
}
