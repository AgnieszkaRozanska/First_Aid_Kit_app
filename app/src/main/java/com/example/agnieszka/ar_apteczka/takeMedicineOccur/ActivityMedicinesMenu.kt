package com.example.agnieszka.ar_apteczka.takeMedicineOccur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.addTakeedicineOccur.ActivityAddTakeMedicineOccour
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder.MyService
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.showAllTakeMedicineOccur.ActivityShowAllTakeMedicineOccur
import com.example.agnieszka.ar_apteczka.todaysMedicines.showAllMedicinesToday.ShowAllTodaysMedicines
import kotlinx.android.synthetic.main.activity_medicines.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ActivityMedicinesMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicines)

        Button_AddTakeMedOccur_addMedToTake.setOnClickListener {
            val activityToAddMedicieOccur = Intent(applicationContext, ActivityAddTakeMedicineOccour::class.java)
            startActivity(activityToAddMedicieOccur)

        }
        Button_All_Med_Drugs.setOnClickListener {
            val activityshowAllMeds= Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
           startActivity(activityshowAllMeds)
        }

        Button_ShowAllMedicinesToday.setOnClickListener {
            val activityshowAllMedsToday= Intent(applicationContext, ShowAllTodaysMedicines::class.java)
            startActivity(activityshowAllMedsToday)
        }
    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, Menu::class.java)
        startActivity(activity)
    }

}
