package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_update_dose_take_medicine_occur.*

class ActivityUpdateDoseTakeMedicineOccur : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_dose_take_medicine_occur)
        val idToUprageDoses:String=setData()


        Button_SaveUpdateDose_TakeMedOccur.setOnClickListener {
            uprageTakeMedOccurDoses(idToUprageDoses)
        }


    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, UpdateRemoveMenuTakeMedicinOccur::class.java)
        startActivity(activity)
    }

    private fun uprageTakeMedOccurDoses(id:String) {

        val dbHelper = SQLConector(this)
        val count = UpdateDose_TakeMedicineOccur_Dose.text.toString()
        val success = dbHelper.updateTakeMedicineOccurDoses(id, count.toInt())
        if (success) {
            Toast.makeText(applicationContext, getString(R.string.DoseUpdateInformation), Toast.LENGTH_SHORT).show()

            val activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
            startActivity(activity)
        }
    }

    private fun setData(): String{
        if (intent.hasExtra("nameMedTakeOcur")) UpdateDose_TakeMedicineOccur_MedicineName.text = intent.getStringExtra("nameMedTakeOcur")
        if (intent.hasExtra("Dose")) UpdateDose_TakeMedicineOccur_Dose.setText(intent.getStringExtra("Dose"))
        var idUprageDoses=""
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idUprageDoses= intent.getStringExtra("IDMedicine_TakeOccur")
        return idUprageDoses
    }

}
