package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_add__take_medicine_occour.*
import java.util.*

class AddTakeMedicineOccour : AppCompatActivity() {

    private var timeOfDay:CharSequence ?= null
    private var beforeAfterMeal:CharSequence ?= null
    private var spinner: Spinner ?= null
    private var id_MedType: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__take_medicine_occour)
        val dbHelper = SQLConector(applicationContext)
        val medicines_list_names_of_med= dbHelper.getMedListOfName()

        spinner = this.Add_Med_Occour_Medicine_Spinner
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, medicines_list_names_of_med)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //Ustawia układ, który będzie używany, gdy pojawi się lista opcji
        spinner!!.adapter = arrayAdapter //Ustaw adapter na Spinnerze

        Add_Med_Occour_Next_Button.setOnClickListener {
            var Activity: Intent = Intent(applicationContext, AddTakeMedicineOccur2::class.java)
            startActivity(Activity)
        }

        Add_Med_Occour_TimeOfDay_radioGroup.setOnCheckedChangeListener { group, checkedId ->

            val timeOfDay_RG : RadioButton= findViewById(checkedId)
            timeOfDay=timeOfDay_RG.text
        }

        Add_Med_Occour_BeforeAfterMeal_radioGroup.setOnCheckedChangeListener { group, checkedId ->

            val beforeAfetrMeal_RG : RadioButton= findViewById(checkedId)
            beforeAfterMeal=beforeAfetrMeal_RG.text
        }
    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, MedicinesMenu::class.java)
        startActivity(activity)
    }




    fun addMedOccur(view: View){
        val dbHelper = SQLConector(this)
        val id= UUID.randomUUID().toString()
       // val id_MedType= dbHelper.getId(spinner!!.selectedItem.toString())
       id_MedType= spinner!!.getSelectedItem().toString();
        val dose = Add_Med_Occour_Dose_EditText.text.toString()
        val data =""
        val hourReminders= ""
        val descriptionReminder=""

        if(dose.isNullOrEmpty()  || Add_Med_Occour_TimeOfDay_radioGroup.checkedRadioButtonId == -1 ||Add_Med_Occour_BeforeAfterMeal_radioGroup.checkedRadioButtonId == -1) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Uwaga!")
            builder.setMessage("Uzupełnij brakujące dane!")
            builder.setPositiveButton("Wróć") { dialog: DialogInterface, which: Int -> }

            builder.show()
        }
        else{

            val success= dbHelper.addTakeMedicineOccour(id, id_MedType.toString(), dose.toInt(), timeOfDay.toString(), beforeAfterMeal.toString(), data , hourReminders,descriptionReminder )

            if(success)
            {
                Toast.makeText(applicationContext, "Lek został dodany", Toast.LENGTH_SHORT).show()

                var Activity: Intent = Intent(applicationContext, MedicinesMenu::class.java)
                startActivity(Activity)
            }
        }

    }

}
