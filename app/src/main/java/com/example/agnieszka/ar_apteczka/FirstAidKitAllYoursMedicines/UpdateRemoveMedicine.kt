package com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_update_remove_medicine.*

class UpdateRemoveMedicine : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_remove_medicine)

        if (intent.hasExtra("name")) UpdateRemoveMedicine_MedicineName.setText(intent.getStringExtra("name"))
        if (intent.hasExtra("count")) updateRemoveTakeMedicineOccur_MedicineTimeofDay.setText(intent.getStringExtra("count"))
        if (intent.hasExtra("kind")) updateRemoveTakeMedicineOccur_Dose.setText(intent.getStringExtra("kind"))
        if (intent.hasExtra("description")) updateRemoveTakeMedicineOccur_MedicineAfterBeforeMeal.setText(intent.getStringExtra("description"))



        button_UpdateDoseTakeMedicineOccur.setOnClickListener {
            Download_Data()
        }

        Button_RemoveMedicine.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Czy jesteś pewien!")
            builder.setMessage("Czy chcesz usunąć ten lek?")
            builder.setPositiveButton("Tak", {dialog: DialogInterface, which: Int ->
                Remove_medicine()
            })
            builder.setNegativeButton("Nie", { dialogInterface: DialogInterface, i: Int -> })
            builder.show()

        }


    }

    override fun onBackPressed() {
        var Activity: Intent = Intent(applicationContext, AllMedicinesRecyclerView::class.java)
        startActivity(Activity)
    }


    fun Download_Data(){
        val intent_edit = Intent(applicationContext, UpdateCountofMedicines::class.java)
        val Med_Name_Count_edit=UpdateRemoveMedicine_MedicineName.text
        val Med_Count_Count_edit= updateRemoveTakeMedicineOccur_MedicineTimeofDay.text

        var id:String=""
        if (intent.hasExtra("IDMedicine"))  id= intent.getStringExtra("IDMedicine")


        intent_edit.putExtra("name", Med_Name_Count_edit)
        intent_edit.putExtra("count", Med_Count_Count_edit)
        intent_edit.putExtra("IDMedicine", id)
        startActivity(intent_edit)

    }

    fun Remove_medicine(){
        val intent_remove = Intent(applicationContext, FirstAidKitMenu::class.java)
        val dbHelper = SQLConector(applicationContext)
        var id_to_remove_med:String=""
        if (intent.hasExtra("IDMedicine"))  id_to_remove_med= intent.getStringExtra("IDMedicine")

        val ifsuccess = dbHelper.removeMedicineType(id_to_remove_med)


        if(ifsuccess)
        {
            Toast.makeText(applicationContext, "Lek został usunięty", Toast.LENGTH_SHORT).show()
        }
        startActivity(intent_remove)
    }
}
