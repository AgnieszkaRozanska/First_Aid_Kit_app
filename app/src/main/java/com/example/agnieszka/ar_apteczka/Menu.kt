package com.example.agnieszka.ar_apteczka

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.showAllMedicines.ActivityShowAllMedicines
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.ActivityMedicinesMenu
import com.example.agnieszka.ar_apteczka.todaysMedicines.showAllMedicinesToday.ShowAllTodaysMedicines
import kotlinx.android.synthetic.main.activity_menu.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList
import android.app.PendingIntent
import android.support.v4.content.ContextCompat
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder.ForegroundService


class Menu : AppCompatActivity() {
    // private val mNotificationTime =
    // Calendar.getInstance().timeInMillis//Set after 1 seconds from the current time. ////////

    //private var mNotified = false/////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val dbHelper = SQLConector(this)
        var howMach = dbHelper.checkIfTableHaveRecords()
        var howMath2 = howMach

        Button_FirstAidKit.setOnClickListener {
            val activityToFirstAidKit =
                Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
            startActivity(activityToFirstAidKit)
        }

        Button_YourAllMedicines.setOnClickListener {
            val activityToMEdicinesMenu =
                Intent(applicationContext, ActivityMedicinesMenu::class.java)
            startActivity(activityToMEdicinesMenu)
        }

        var textNotification = createTextToNotification()
        if (!textNotification.isEmpty()) {
            createNotification()
        }

        startService()

        buttonStopServiceTemp.setOnClickListener {
            stopService()
        }

}

    private fun startService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        serviceIntent.putExtra("time", 1)

        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        stopService(serviceIntent)
    }

    private fun takeTimeNow() : String{
        val current = LocalDateTime.now()
        val formatTime = DateTimeFormatter.ofPattern("HH:mm")
        var timeResult = current.format(formatTime).toString()
        return  timeResult

    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, ShowAllTodaysMedicines::class.java)
        startActivity(activity)
    }


   private fun createNotification(){

       lateinit var notificationManager : NotificationManager
       lateinit var notificationChannel: NotificationChannel
       lateinit var builder: Notification.Builder
       val channelID = "com.example.agnieszka.ar_apteczka"
       val description = " description"

       var textNotification=createTextToNotification()

       notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

       val intent = Intent(this, ActivityShowAllMedicines::class.java)
       val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           notificationChannel = NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_HIGH)
           notificationChannel.enableLights(true)
           notificationChannel.lightColor = Color.GREEN
           notificationChannel.enableVibration(true)
           notificationManager.createNotificationChannel(notificationChannel)

           builder = Notification.Builder(this, channelID)
               .setContentTitle(getString(R.string.TitleNotificationAmoundMed))
               .setContentText(textNotification.toString())
               .setSmallIcon(R.drawable.pills)
               .setShowWhen(true)
               .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.attention))
               .setContentIntent(pendingIntent)
               .setStyle(Notification.BigTextStyle().bigText(textNotification.toString()))

       }else{
           builder = Notification.Builder(this)
               .setContentTitle(getString(R.string.TitleNotificationAmoundMed))
               .setContentText(textNotification.toString())
               .setSmallIcon(R.drawable.pills)
               .setShowWhen(true)
               .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.attention))
               .setContentIntent(pendingIntent)
       }
        notificationManager.notify(1234, builder.build())
   }

    fun createTextToNotification() :ArrayList<String>{

        val dbHelper = SQLConector(this)
        var textNotification = dbHelper.downloadMedsForLowAmountNotification()

        return  textNotification
    }

}
