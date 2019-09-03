package com.example.agnieszka.ar_apteczka.takeMedicineOccur.addTakeedicineOccur

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder.AddReminder
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
        validationData(button_ChooseDate_Start.text.toString(), editText_ForHowManyDays, textView_DateEnd)


        Button_Add_Med_Occour_Add_Reminder.setOnClickListener {
            val activity = Intent(applicationContext, AddReminder::class.java)
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
        Button_Add_Med_Occour_Add_Reminder.setOnClickListener {
            downloadDataAndGoToAddReminder()
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
        var dateStart =button_ChooseDate_Start.text.toString()
        var howManyDays = editText_ForHowManyDays.text.toString()
        var dateEnd =textView_DateEnd.text.toString()
        val id_MedType = dbHelper.getMedicieID(choosenMed)
        var ifExists = dbHelper.checkIfTakeMedOccurExists(choosenMed, timeOfDay.toString(),beforeAfterMeal.toString())
        if(dose.isEmpty() || dose.toInt() ==0 || Add_Med_Occour_TimeOfDay_radioGroup.checkedRadioButtonId == -1 ||Add_Med_Occour_BeforeAfterMeal_radioGroup.checkedRadioButtonId == -1 || dateStart=="Wybierz datę" || dateEnd == "@string/InformationAboutDateAndDays" || howManyDays.isEmpty()) {
            alertDialogNoData()
        }
        else{
            if(ifExists == true){
                alertDialogMedExists()
            }else {
                val id = UUID.randomUUID().toString()
                val takeMedOccur = TakeMedicineOccur(
                    id,
                    id_MedType,
                    choosenMed,
                    dose.toInt(),
                    timeOfDay.toString(),
                    beforeAfterMeal.toString(),
                    dateStart,
                    dateEnd
                )
                val success = dbHelper.addTakeMedicineOccur(takeMedOccur)
                AddMediciesToTake(takeMedOccur)
                if (success) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.TakeMedOccurMedAdded),
                        Toast.LENGTH_SHORT
                    ).show()

                    var activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
                    startActivity(activity)
                }
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

    private fun alertDialogMedExists(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertDialogTileMedExists))
        builder.setMessage("Wybrany lek o danej porze dnia juz istnieje, jeżeli chcesz edytować dawkę lub okres zażywania leku przejdź do listy wszystkich leków")
        builder.setNeutralButton(getString(R.string.alertDialogBack)){ _, _ ->
        }
        builder.show()
    }

    private fun alertDialogNoDataStart(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertDialogTitleNoDataStart))
        builder.setMessage(getString(R.string.alertDialogMessageNoDataStart))
        builder.setPositiveButton(getString(R.string.TakeMedOccurBack)) { dialog: DialogInterface, which: Int -> }
        builder.show()
    }

      private fun setDataEnd(dataStartString : String, howManyDays : Int) : String{
          var dataEnd = ""
          if(dataStartString != getString(R.string.chooseDate)) {
              var dateDate = LocalDate.parse(dataStartString)
              var period = Period.of(0, 0, howManyDays-1)
              var modifiedDate = dateDate.plus(period)
              dataEnd = modifiedDate.toString()
              textView_DateEnd.text = dataEnd
          } else alertDialogNoDataStart()
          return dataEnd
      }

      private fun AddMediciesToTake(takeMedicineOccour: TakeMedicineOccur){
           val dbHelper = SQLConector(this)
           var date =button_ChooseDate_Start.text
           var howManyDays = editText_ForHowManyDays.text.toString()

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
       }


    fun validationData(dataStart : String, textedit: EditText, warm_informations: TextView)
    {
        textedit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (textedit.length()==0 && button_ChooseDate_Start.text.toString() == getString(R.string.chooseDate)){
                    warm_informations.text = getString(R.string.validationDataDateAndPeriodOfTaken)
                    warm_informations.visibility= TextView.VISIBLE
                }
                if(textedit.length()==0 && button_ChooseDate_Start.text.toString() != getString(R.string.chooseDate)){
                    warm_informations.text = getString(R.string.validationDataPeriodOfTaken)
                    warm_informations.visibility= TextView.VISIBLE
                }
                if(textedit.length()!=0 && button_ChooseDate_Start.text.toString() == getString(R.string.chooseDate)){
                    warm_informations.text = getString(R.string.validationDataDateStart)
                    warm_informations.visibility= TextView.VISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (textedit.length()<1){
                    warm_informations.text = getString(R.string.onTextChangedDateAndPeriogOfTaken)
                    warm_informations.visibility= TextView.VISIBLE            }
                else if(textedit.length()!=0 && button_ChooseDate_Start.text.toString() != "Wybierz datę"){
                    warm_informations.text = setDataEnd(button_ChooseDate_Start.text.toString(), editText_ForHowManyDays.text.toString().toInt())
                    warm_informations.visibility= TextView.VISIBLE
                }
                else{
                    warm_informations.text = getString(R.string.onTextChangedDateStart)
                    warm_informations.visibility= TextView.VISIBLE
                }
            }

        })
    }

    fun checkTheCorrectnessOfTheData() : Boolean{
        var result : Boolean = true
        val dose = Add_Med_Occour_Dose_EditText.text.toString()
        var dateStart =button_ChooseDate_Start.text.toString()
        var howManyDays = editText_ForHowManyDays.text.toString()
        var dateEnd =textView_DateEnd.text.toString()

        if(dose.isEmpty() || dose.toInt() ==0 || Add_Med_Occour_TimeOfDay_radioGroup.checkedRadioButtonId == -1 ||Add_Med_Occour_BeforeAfterMeal_radioGroup.checkedRadioButtonId == -1 || dateStart=="Wybierz datę" || dateEnd == "@string/InformationAboutDateAndDays" || howManyDays.isEmpty()) {
            result = false
        }

        return result
    }


    fun downloadDataAndGoToAddReminder()
    {
        val dbHelper = SQLConector(this)
        val choosenMed= spinner!!.selectedItem.toString()
        var ifExists = dbHelper.checkIfTakeMedOccurExists(choosenMed, timeOfDay.toString(),beforeAfterMeal.toString())
       if(!checkTheCorrectnessOfTheData())
       {
           alertDialogNoData()
       }
       else {
           if (ifExists == true) {
               alertDialogMedExists()
           } else {
               val dbHelper = SQLConector(this)
               val choosenMed = spinner!!.selectedItem.toString()
               val dose = Add_Med_Occour_Dose_EditText.text.toString()
               val id_MedType = dbHelper.getMedicieID(choosenMed)
               val timeOfDay = timeOfDay.toString()
               val beforeAfterMeal = beforeAfterMeal.toString()
               val dateStart = button_ChooseDate_Start.text.toString()
               var howManyDays = editText_ForHowManyDays.text.toString()
               var dateEnd = textView_DateEnd.text.toString()


               val activityToAddReminder =
                   Intent(applicationContext, AddReminder::class.java)
               activityToAddReminder.putExtra("choosenMed", choosenMed)
               activityToAddReminder.putExtra("dose", dose)
               activityToAddReminder.putExtra("idMedType", id_MedType)
               activityToAddReminder.putExtra("timeOfDay", timeOfDay)
               activityToAddReminder.putExtra("beforeAfterMeal", beforeAfterMeal)
               activityToAddReminder.putExtra("dateStart", dateStart)
               activityToAddReminder.putExtra("howManyDays", howManyDays)
               activityToAddReminder.putExtra("dateEnd", dateEnd)
               startActivity(activityToAddReminder)
           }
       }
            }


}
