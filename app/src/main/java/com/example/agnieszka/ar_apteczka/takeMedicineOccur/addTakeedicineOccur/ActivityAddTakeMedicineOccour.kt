package com.example.agnieszka.ar_apteczka.takeMedicineOccur.addTakeedicineOccur

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder.ActivityAddReminder
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.ActivityMedicinesMenu
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.TakeMedicineOccur
import kotlinx.android.synthetic.main.activity_add__take_medicine_occour.*
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ArrayAdapter
import java.time.LocalDate
import java.time.Period


class ActivityAddTakeMedicineOccour : AppCompatActivity() {

    private var timeOfDay:CharSequence ?= null
    private var beforeAfterMeal:CharSequence ?= null
    private var spinner: Spinner ?= null
    private var id_MedType: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__take_medicine_occour)
        val dbHelper = SQLConector(applicationContext)
        val medicinesListNamesOfMed = dbHelper.getMedListOfName()
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
            val beforeAfterMeal : RadioButton= findViewById(checkedId)
            this.beforeAfterMeal = beforeAfterMeal.text
        }

        button_ChooseDate_TakeMedOccur.setOnClickListener {
            datePiker()
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

        val choosenMed= spinner!!.selectedItem.toString()
        val dose = Add_Med_Occour_Dose_EditText.text.toString()
        var date =button_ChooseDate_TakeMedOccur.text
        val howManyDays = EditText_ChooseHowMayDays.text.toString()
        val hourReminders= ""
        val descriptionReminder=""
        val id_MedType = dbHelper.getMedicieID(choosenMed)

        if(dose.isEmpty() || dose.toInt() ==0 || Add_Med_Occour_TimeOfDay_radioGroup.checkedRadioButtonId == -1 ||Add_Med_Occour_BeforeAfterMeal_radioGroup.checkedRadioButtonId == -1 || date=="Wybierz datę" || howManyDays.isEmpty() || howManyDays.toInt() <=0  ) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.TakeMedOccurAttention))
            builder.setMessage(getString(R.string.TakeMedOccurInformation))
            builder.setPositiveButton(getString(R.string.TakeMedOccurBack)) { dialog: DialogInterface, which: Int -> }

            builder.show()
        }
        else{
            repeat(howManyDays.toInt()){

                val id= UUID.randomUUID().toString()
                val takeMedOccur= TakeMedicineOccur(
                    id,
                    id_MedType,
                    choosenMed,
                    dose.toInt(),
                    timeOfDay.toString(),
                    beforeAfterMeal.toString(),
                    date.toString()
                )
                val success= dbHelper.addTakeMedicineOccur(takeMedOccur)

                var dateDate=LocalDate.parse(date)
                var period = Period.of(0, 0, 1)
                var modifiedDate = dateDate.plus(period)
                 date=modifiedDate.toString()
                if(success)
                {
                    Toast.makeText(applicationContext, getString(R.string.TakeMedOccurMedAdded), Toast.LENGTH_SHORT).show()

                    var activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
                    startActivity(activity)
                }
            }

        }
    }


    private fun datePiker(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                view, myear, mmonth, mday ->
            var date = "" + myear + "-" + (mmonth + 1) + "-" + mday
            var monthString: String
            var dayString: String
            var yearString = myear.toString()
            monthString = formatMonth(mmonth)
            dayString=formatDays(mday)
            var dataString = yearString+"-"+monthString+"-"+dayString
            var dateDate= LocalDate.parse(dataString)
            button_ChooseDate_TakeMedOccur.setText(dateDate.toString())
        }, year, month, day)
        datePicker.show()
    }


    private fun formatMonth(mmonth : Int) : String{
        var monthString: String
        if((mmonth+1)<10){
            monthString="0"+(mmonth+1).toString()
        }else{
            monthString=(mmonth+1).toString()
        }
        return monthString
    }

    private fun formatDays(mday : Int) : String{
        var dayString: String
        if(mday<10){
            dayString="0"+(mday.toString())
        }else{
            dayString=mday.toString()
        }
        return dayString
    }

}
