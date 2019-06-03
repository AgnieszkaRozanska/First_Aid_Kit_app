package com.example.agnieszka.ar_apteczka.takeMedicineOccur

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
import kotlin.collections.ArrayList

class ActivityAddTakeMedicineOccour : AppCompatActivity() {

    private var timeOfDay:CharSequence ?= null
    private var beforeAfterMeal:CharSequence ?= null
    private var spinner: Spinner ?= null
    private var id_MedType: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__take_medicine_occour)
        val dbHelper = SQLConector(applicationContext)
        val medicinesListNamesOfMed= dbHelper.getMedListOfName()

        setSpinner(medicinesListNamesOfMed)

        Add_Med_Occour_Next_Button.setOnClickListener {
            val activity = Intent(applicationContext, ActivityAddReminder::class.java)
            startActivity(activity)
        }

        Add_Med_Occour_TimeOfDay_radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val timeOfDayRG : RadioButton= findViewById(checkedId)
            timeOfDay=timeOfDayRG.text
        }

        Add_Med_Occour_BeforeAfterMeal_radioGroup.setOnCheckedChangeListener { group, checkedId ->

            val beforeAfetrMealRG : RadioButton= findViewById(checkedId)
            beforeAfterMeal=beforeAfetrMealRG.text
        }
    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
        startActivity(activity)
    }

    private  fun setSpinner(list:ArrayList<String>){
        spinner = this.Add_Med_Occour_Medicine_Spinner
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = arrayAdapter
    }


    fun addMedOccur(view: View){ //Nie usuwac, bo wysypuje aplikacje !!!!
        val dbHelper = SQLConector(this)
        val id= UUID.randomUUID().toString()
       id_MedType= spinner!!.selectedItem.toString()
        val dose = Add_Med_Occour_Dose_EditText.text.toString()
        val data =""
        val hourReminders= ""
        val descriptionReminder=""

        if(dose.isNullOrEmpty()  || Add_Med_Occour_TimeOfDay_radioGroup.checkedRadioButtonId == -1 ||Add_Med_Occour_BeforeAfterMeal_radioGroup.checkedRadioButtonId == -1) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.TakeMedOccurAttention))
            builder.setMessage(getString(R.string.TakeMedOccurInformation))
            builder.setPositiveButton(getString(R.string.TakeMedOccurBack)) { dialog: DialogInterface, which: Int -> }

            builder.show()
        }
        else{

            val takeMedOccur= TakeMedicineOccur(id,id_MedType.toString(), dose.toInt(), timeOfDay.toString(), beforeAfterMeal.toString(), data , hourReminders,descriptionReminder  )
            val success= dbHelper.addTakeMedicineOccur(takeMedOccur)

            if(success)
            {
                Toast.makeText(applicationContext, getString(R.string.TakeMedOccurMedAdded), Toast.LENGTH_SHORT).show()

                var activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
                startActivity(activity)
            }
        }

    }

}
