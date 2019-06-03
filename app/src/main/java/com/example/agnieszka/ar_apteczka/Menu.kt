package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.ActivityMedicinesMenu
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {

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
    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, MainActivity::class.java)
        startActivity(activity)
    }
}
