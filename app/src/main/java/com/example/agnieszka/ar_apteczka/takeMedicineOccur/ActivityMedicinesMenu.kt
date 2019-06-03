package com.example.agnieszka.ar_apteczka.takeMedicineOccur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import kotlinx.android.synthetic.main.activity_medicines.*

class ActivityMedicinesMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicines)

        Button_Add_Take_Med_Occur.setOnClickListener {
            val activityToAddMedOccur = Intent(applicationContext,ActivityAddTakeMedicineOccour::class.java)
            startActivity(activityToAddMedOccur)
        }


        Button_All_Med_Drugs.setOnClickListener {
            val activityshowAllMeds= Intent(this, ActivityShowAllTakeMedicineOccur::class.java)
           startActivity(activityshowAllMeds)
        }
    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, Menu::class.java)
        startActivity(activity)
    }

}
