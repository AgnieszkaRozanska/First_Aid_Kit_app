package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.showAllMedicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.adapter.ShowAllMedicinesAdapter
import kotlinx.android.synthetic.main.activity_all__medicines.*
import java.lang.Exception

class ActivityShowAllMedicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all__medicines)

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                try {
                    setTextIfListIsEmpty()
                } catch (e: Exception) {}
                handler.postDelayed(this, 300)
            }
        }
        handler.postDelayed(runnable, 300)

    }


    override fun onBackPressed() {
        val activityGoToMenu = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
        startActivity(activityGoToMenu)
    }


    override fun onResume() {
        super.onResume()

        val sqlConector = SQLConector(this)
        sqlConector.writableDatabase
        val  medicinesList=sqlConector.getAllMedicineTypes()

        recyler_view_med.layoutManager = LinearLayoutManager(this)
        recyler_view_med.adapter = ShowAllMedicinesAdapter(this, medicinesList)
    }

    private fun  setTextIfListIsEmpty(){
        val sqlConector = SQLConector(this)
        val  medicinesList=sqlConector.getAllMedicineTypes()

        if(medicinesList.isNullOrEmpty()) {
            textViewInformationAboutLackOfMedicine.text = "Brak leków" + "\n" + "Przejdź do zakładki Dodaj lek do apteczki"
            textViewInformationAboutLackOfMedicine.visibility = TextView.VISIBLE
        }
        else textViewInformationAboutLackOfMedicine.visibility = TextView.INVISIBLE
    }
}
