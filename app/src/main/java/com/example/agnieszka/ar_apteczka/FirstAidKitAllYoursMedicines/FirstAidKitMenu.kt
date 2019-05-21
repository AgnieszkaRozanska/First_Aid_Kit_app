package com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.MainActivity
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import kotlinx.android.synthetic.main.activity_first_and_kit.*

class FirstAidKitMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_and_kit)




        Button_Add_Med_toFAidK.setOnClickListener {
            var Activity1: Intent = Intent(applicationContext, AddMedicineFirstAidKit::class.java)
            startActivity(Activity1)
        }

        Button_All_Med_INFaidK.setOnClickListener {
            var Activity2: Intent = Intent(applicationContext, AllMedicinesRecyclerView::class.java)
            startActivity(Activity2)
        }

    }

    override fun onBackPressed() {
        var Activity: Intent = Intent(applicationContext, Menu::class.java)
        startActivity(Activity)
    }

}
