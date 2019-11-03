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
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.drugsDataBase.Drug
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder.ForegroundService
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStreamReader


class Menu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)



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

       // buttonStopServiceTemp.setOnClickListener {
       //     stopService()
      //  }

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

       var textNotification=createTextToNotification().toString()
       if(textNotification.contains('[') && textNotification.contains(']')){
           textNotification = textNotification.replace('[', ' ')
           textNotification = textNotification.replace(']', ' ')
       }
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

    private fun readTxtFiles(): ArrayList<String> {
        val outputArrayList = ArrayList<String>()
        val inputStream = applicationContext.resources.openRawResource(R.raw.writeparse)

        try {

            val input = DataInputStream(inputStream)
            val br = BufferedReader(InputStreamReader(input))

            var strLine: String?

            val listOfEntries = br.readLines()
            for(i in listOfEntries){
                outputArrayList.add(i)
            }

            br.close()
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return outputArrayList
    }

    //  0               1    2   3        4     5                  6                   7
    //Nalgesin Forte|ludzki|moc|550 mg|postac|tabletki powlekane|substancjaCzynna|Naproxenum natricum,

    private fun splitAndCreateListofDrugs(listOfLines : ArrayList<String>) : ArrayList<Drug>{
        val arrayListOfDrugs = ArrayList<Drug>()
        var id = 1
        for (i  in listOfLines){
            var drugName = ""
            var drugPower = ""
            var drugKind = ""
            var activeSubstance = ""
            var tempTab = i.split('|')
            drugName += tempTab[0]

            var sizeOfTempTab = tempTab.size
            for (j in 0..sizeOfTempTab - 1){

                if(tempTab[j] == "moc") drugPower += tempTab[j+1]
                if(tempTab[j] == "postac") drugKind += tempTab[j+1]
                if(tempTab[j] == "substancjaCzynna") activeSubstance += tempTab[j+1]
            }

            var drug =
                Drug(
                    id.toString(),
                    drugName,
                    drugPower,
                    drugKind,
                    activeSubstance
                )
            arrayListOfDrugs.add(drug)

            id +=1
        }
        return arrayListOfDrugs
    }

    private fun addDrugToDatabase(listOfDrugs : ArrayList<Drug>){
        val dbHelper = SQLConector(this)
        for(i: Drug in listOfDrugs){
            var id = ""
            var nameOfDrug = ""
            var power = ""
            var kind = ""
            var activedose = ""

             id += i.idDrug
             nameOfDrug += i.nameDrug
             power += i.power
             kind += i.kind
             activedose += i.activeDose

            var drug =
                Drug(
                    id,
                    nameOfDrug,
                    power,
                    kind,
                    activedose
            )
            dbHelper.addDrug(drug)
        }

    }
}
