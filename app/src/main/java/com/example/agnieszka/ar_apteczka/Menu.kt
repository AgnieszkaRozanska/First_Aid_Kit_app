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
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.ActivityMedicinesMenu
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.showAllTakeMedicineOccur.ActivityShowAllTakeMedicineOccur
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder.NotificationUtils
import kotlinx.android.synthetic.main.activity_add_update_notification.*
import kotlinx.android.synthetic.main.activity_menu.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class Menu : AppCompatActivity() {
    private val mNotificationTime = Calendar.getInstance().timeInMillis//Set after 1 seconds from the current time. ////////
    /*private val mNotificationTime = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 15)
        set(Calendar.MINUTE, 50)
    } ////////
*/
    private var mNotified = false/////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        Button_FirstAidKit.setOnClickListener {
            val activityToFirstAidKit = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
            startActivity(activityToFirstAidKit)
        }

        Button_YourAllMedicines.setOnClickListener {
            val activityToMEdicinesMenu = Intent(applicationContext, ActivityMedicinesMenu::class.java)
            startActivity(activityToMEdicinesMenu)
        }

        var textNotification=createTextToNotification()
        if(!textNotification.isEmpty()){
            createNotification()
        }

        /// powiadomienie
        if (!mNotified) {
          NotificationUtils()
                .setNotification(mNotificationTime, this@Menu)
        }
        /////////////////////


    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
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

       val intent = Intent(this, LauncherActivity::class.java)
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
