package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.FirstAidKitMenu
import com.example.agnieszka.ar_apteczka.TakeMedicineOccur.MedicinesMenu
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        Button_FirstAidKit.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do drugiego okna

            var Activity2: Intent = Intent(applicationContext, FirstAidKitMenu::class.java)
            startActivity(Activity2)
        }


        Button_YourAllMedicines.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do drugiego okna

            var Activity3: Intent = Intent(applicationContext, MedicinesMenu::class.java)
            startActivity(Activity3)
        }

    }
}
