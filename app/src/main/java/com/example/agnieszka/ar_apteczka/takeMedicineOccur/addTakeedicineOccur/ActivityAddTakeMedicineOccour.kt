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
import com.example.agnieszka.ar_apteczka.todaysMedicines.MedicineToTake
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter


class ActivityAddTakeMedicineOccour : AppCompatActivity() {

    private var timeOfDay:CharSequence ?= null
    private var beforeAfterMeal:CharSequence ?= null
    private var spinner: Spinner ?= null





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
            this.timeOfDay=timeOfDayRG.text
        }

        Add_Med_Occour_BeforeAfterMeal_radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val beforeAfterMeal : RadioButton= findViewById(checkedId)
            this.beforeAfterMeal = beforeAfterMeal.text
        }

        button_ChooseDate_Start.setOnClickListener {
           datePikerStart()
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
        var dateStart =button_ChooseDate_Start.text
        // var howManyDays = EditText_HowManyDays.text.toString()
        //var dateEnd =setDataEnd(dateStart.toString(), howManyDays.toInt())
        //var dateTodayString = setDataToday()
        //var dateDateStart=LocalDate.parse(dateStart)
        // var dateDateEnd=LocalDate.parse(dateEnd)
        //var dateToday = LocalDate.parse(dateTodayString)
        val id_MedType = dbHelper.getMedicieID(choosenMed)

        //if(dose.isEmpty() || dose.toInt() ==0 || Add_Med_Occour_TimeOfDay_radioGroup.checkedRadioButtonId == -1 ||Add_Med_Occour_BeforeAfterMeal_radioGroup.checkedRadioButtonId == -1 || dateStart=="Wybierz datę" || howManyDays.isEmpty()) {
        if(dose.isEmpty() || dose.toInt() ==0 || Add_Med_Occour_TimeOfDay_radioGroup.checkedRadioButtonId == -1 ||Add_Med_Occour_BeforeAfterMeal_radioGroup.checkedRadioButtonId == -1 || dateStart=="Wybierz datę" ) {
            alertDialogNoData()
        }
        // else if(dateToday>dateDateEnd) alertDialogWrongData()
        else{
            val id= UUID.randomUUID().toString()
            val takeMedOccur= TakeMedicineOccur(
                id,
                id_MedType,
                choosenMed,
                dose.toInt(),
                timeOfDay.toString(),
                beforeAfterMeal.toString(),
                dateStart.toString(),
                "2019-10-10"
            )
            val success= dbHelper.addTakeMedicineOccur(takeMedOccur)

            if(success)
            {
                //AddMediciesToTake(takeMedOccur)
                Toast.makeText(applicationContext, getString(R.string.TakeMedOccurMedAdded), Toast.LENGTH_SHORT).show()

                var activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
                startActivity(activity)
            }

        }
    }

    private fun datePikerStart(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                view, myear, mmonth, mday ->
            //var date = "" + myear + "-" + (mmonth + 1) + "-" + mday
            var monthString: String
            var dayString: String
            var yearString = myear.toString()
            monthString = formatMonth(mmonth)
            dayString=formatDays(mday)
            var dataString = yearString+"-"+monthString+"-"+dayString
            var dateDate= LocalDate.parse(dataString)
            button_ChooseDate_Start.text=dateDate.toString()
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

    private fun alertDialogNoData(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.TakeMedOccurAttention))
        builder.setMessage(getString(R.string.TakeMedOccurInformation))
        builder.setPositiveButton(getString(R.string.TakeMedOccurBack)) { dialog: DialogInterface, which: Int -> }
        builder.show()
    }

    private fun alertDialogWrongData(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nieprawidłowa data")
        builder.setMessage("Data końcowa nie może być mniejsza od daty dzisiejszej")
        builder.setPositiveButton(getString(R.string.TakeMedOccurBack)) { dialog: DialogInterface, which: Int -> }
        builder.show()
    }
    private fun alertDialogNoDataStart(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nieprawidłowa data")
        builder.setMessage("Musisz podać datę początkową oraz ilość dni przez które dany lek będzie zażywany")
        builder.setPositiveButton(getString(R.string.TakeMedOccurBack)) { dialog: DialogInterface, which: Int -> }
        builder.show()
    }

    /*  private fun setDataEnd(dataStartString : String, howManyDays : Int) : String{
          var dataEnd = ""
          if(dataStartString != "Wybierz datę") {
              var dateDate = LocalDate.parse(dataStartString)
              var period = Period.of(0, 0, howManyDays)
              var modifiedDate = dateDate.plus(period)
              dataEnd = modifiedDate.toString()
              textView_ChooseDate_End.text = dataEnd
              //textView_ChooseDate_End.visibility= TextView.VISIBLE
              //TextView_dateTo_info.visibility= TextView.VISIBLE
          } else alertDialogNoDataStart()
          return dataEnd
      } */

    private fun setDataToday() : String{
        val current = LocalDateTime.now()
        val formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var dateTodayString =current.format(formatDate)
        return dateTodayString.toString()
    }

    /*   private fun AddMediciesToTake(takeMedicineOccour: TakeMedicineOccur){
           val dbHelper = SQLConector(this)
           var date =button_ChooseDate_Start.text
           var howManyDays = EditText_HowManyDays.text.toString()

           repeat(howManyDays.toInt()){

           var id= UUID.randomUUID().toString()
           var medicineToTake= MedicineToTake(
               id,
               takeMedicineOccour.iD,
               takeMedicineOccour.iD_MedicineType,
               takeMedicineOccour.medicineType_Name,
               takeMedicineOccour.dose,
               takeMedicineOccour.timeOfDay,
               takeMedicineOccour.beforeAfterMeal,
               date.toString(),
               "No"
           )
           dbHelper.addMedicineToTake(medicineToTake)
               var dateDate = LocalDate.parse(date)
               var period = Period.of(0, 0, 1)
               var modifiedDate = dateDate.plus(period)
               date  = modifiedDate.toString()
           }

       } */


    private fun datePikerEnd(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                view, myear, mmonth, mday ->
            //var date = "" + myear + "-" + (mmonth + 1) + "-" + mday
            var monthString: String
            var dayString: String
            var yearString = myear.toString()
            monthString = formatMonth(mmonth)
            dayString=formatDays(mday)
            var dataString = yearString+"-"+monthString+"-"+dayString
            var dateDate= LocalDate.parse(dataString)
            //textView_ChooseDate_End.text=dateDate.toString()
        }, year, month, day)
        datePicker.show()
    }


}
