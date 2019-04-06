package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import kotlinx.android.synthetic.main.activity_medicines.*

class MedicinesMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicines)


        Button_Back.setOnClickListener {

            var Activity1: Intent = Intent(applicationContext, Menu::class.java)
            startActivity(Activity1)
        }

        Button_Add_Take_Med_Occur.setOnClickListener {
            var Activity2:Intent = Intent(applicationContext,AddTakeMedicineOccour::class.java)
            startActivity(Activity2)
        }

    }
}
