package com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_update_remove_medicine.*

class UpdateRemoveMedicine : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_remove_medicine)

        if (intent.hasExtra("name")) UpdateRemoveMedicine_MedicineName.setText(intent.getStringExtra("name"))
        if (intent.hasExtra("count")) updateRemoveMedicine_MedicineCount.setText(intent.getStringExtra("count"))
        if (intent.hasExtra("kind")) updateRemoveMedicine_MedicineKind.setText(intent.getStringExtra("kind"))
        if (intent.hasExtra("description")) updateRemoveMedicine_MedicineDescription.setText(intent.getStringExtra("description"))



        button_UpdateMedicine.setOnClickListener {
            Download_Data()
        }

        Button_RemoveMedicine.setOnClickListener {
            Remove_medicine()
        }


    }

    fun Download_Data(){
        val intent_edit = Intent(applicationContext, UpdateCountofMedicines::class.java)
        val Med_Name_Count_edit=UpdateRemoveMedicine_MedicineName.text
        val Med_Count_Count_edit= updateRemoveMedicine_MedicineCount.text

        var id:String=""
        if (intent.hasExtra("IDMedicine"))  id= intent.getStringExtra("IDMedicine")


        intent_edit.putExtra("name", Med_Name_Count_edit)
        intent_edit.putExtra("count", Med_Count_Count_edit)
        intent_edit.putExtra("IDMedicine", id)
        startActivity(intent_edit)

    }

    fun Remove_medicine(){
        val intent_remove = Intent(applicationContext, AllMedicinesRecyclerView::class.java)
        val dbHelper = SQLConector(applicationContext)
        var id_to_remove_med:String=""
        if (intent.hasExtra("IDMedicine"))  id_to_remove_med= intent.getStringExtra("IDMedicine")

        val ifsuccess = dbHelper.removeMedicineType(id_to_remove_med)


        if(ifsuccess)
        {
            Toast.makeText(applicationContext, "Lek został usunięty", Toast.LENGTH_SHORT).show()
            //arrayOf(medicineTypeList[])
            //medicineTypeList.removeAt(holder.adapterPosition)
            //notifyItemRemoved(holder.adapterPosition)
        }
        startActivity(intent_remove)
    }
}
