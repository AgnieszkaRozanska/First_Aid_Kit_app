package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_update_dose_take_medicine_occur.*

class UpdateDoseTakeMedicineOccur : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_dose_take_medicine_occur)


        if (intent.hasExtra("nameMedTakeOcur")) UpdateDose_TakeMedicineOccur_MedicineName.setText(intent.getStringExtra("nameMedTakeOcur"))
        if (intent.hasExtra("Dose")) UpdateDose_TakeMedicineOccur_Dose.setText(intent.getStringExtra("Dose"))
        var id_UprageDoses:String=""
        if (intent.hasExtra("IDMedicine_TakeOccur"))  id_UprageDoses= intent.getStringExtra("IDMedicine_TakeOccur")



        Button_SaveUpdateDose_TakeMedOccur.setOnClickListener {
            UprageTakeMedOccurDoses(id_UprageDoses)
        }


    }

    override fun onBackPressed() {
        var activity: Intent = Intent(applicationContext, UpdateRemoveMenuTakeMedicinOccur::class.java)
        startActivity(activity)
    }


    fun UprageTakeMedOccurDoses(id:String) {

        val dbHelper = SQLConector(this)
        val count = UpdateDose_TakeMedicineOccur_Dose.text.toString()


        val ifsuccess = dbHelper.updateTakeMedicineOccurDoses(id, count.toInt())

        if (ifsuccess) {
            Toast.makeText(applicationContext, "Dawka została zaktualizowana", Toast.LENGTH_SHORT).show()

            var Activity: Intent = Intent(applicationContext, MedicinesMenu::class.java)
            startActivity(Activity)
        }
    }
}
