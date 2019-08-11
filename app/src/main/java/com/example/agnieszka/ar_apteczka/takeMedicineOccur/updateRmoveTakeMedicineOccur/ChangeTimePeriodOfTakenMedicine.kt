package com.example.agnieszka.ar_apteczka.takeMedicineOccur.updateRmoveTakeMedicineOccur

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.showAllTakeMedicineOccur.ActivityShowAllTakeMedicineOccur
import com.example.agnieszka.ar_apteczka.todaysMedicines.MedicineToTake
import kotlinx.android.synthetic.main.activity_change_time_period_of_taken_medicine.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*


class ChangeTimePeriodOfTakenMedicine : AppCompatActivity() {
    val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private var dateStartOfPeriodTaken = ""
    private var dateEndOfPeriodTaken = ""
    private var idTakeMedOccur = ""
    private var timeOfDay = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_time_period_of_taken_medicine)
        downloadAndSetData()

        buttonSaveNewDateEnd.setOnClickListener {
            SaveChangePeriodOfTakingMedicine()
        }

        buttonChooseNewDateEnd.setOnClickListener {
            datePikerStart()
        }
    }


   private fun downloadAndSetData(){
       if (intent.hasExtra("nameMedTakeOcur")) textViewMedicineName.text=intent.getStringExtra("nameMedTakeOcur")
       if (intent.hasExtra("dateStart")) {
           var dateStart = intent.getStringExtra("dateStart")
           dateStartOfPeriodTaken= dateStart.replace("-", ".")
       }
       if (intent.hasExtra("dateEnd")) {
           var dateEnd = intent.getStringExtra("dateEnd")
           dateEndOfPeriodTaken = dateEnd.replace("-", ".")
       }
       textViewTimePeriodCurrent.text = "$dateStartOfPeriodTaken  -  $dateEndOfPeriodTaken"
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
            buttonChooseNewDateEnd.text=dateDate.toString()
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

    private fun SaveChangePeriodOfTakingMedicine(){
        if (intent.hasExtra("dateEnd")) dateEndOfPeriodTaken = intent.getStringExtra("dateEnd")
        dateEndOfPeriodTaken = dateEndOfPeriodTaken.replace(".", "-")
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        //parsedDate = LocalDate.parse("14/02/2018", DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        var dateEndFormatDate = LocalDate.parse(dateEndOfPeriodTaken, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        var newDateEnd = buttonChooseNewDateEnd.text
        var newdateEndFormatDate = LocalDate.parse(newDateEnd, formatDate)
        val compareDateEndWithCurrentDate = dateEndFormatDate.compareTo(newdateEndFormatDate)
        if(compareDateEndWithCurrentDate > 0) shortenPertiodOfTakingMedicine()
        if(compareDateEndWithCurrentDate == 0) alertDialogTheSameDate()
        if(compareDateEndWithCurrentDate < 0) extendPeriodOfTakingMedicine(dateEndFormatDate, newdateEndFormatDate)
    }

    private fun extendPeriodOfTakingMedicine(dateEndFormatDateOld : LocalDate, newdateEndFormatDate :LocalDate){
        if (intent.hasExtra("timeOfDay")) timeOfDay = intent.getStringExtra("timeOfDay")
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idTakeMedOccur= intent.getStringExtra("IDMedicine_TakeOccur")
        val dbHelper = SQLConector(this)
        dbHelper.updateTakeMedicineOccurDateEnd(idTakeMedOccur, newdateEndFormatDate.toString())
        var dateEndFormatDate = LocalDate.parse(dateEndFormatDateOld.toString(), formatDate)
        var takeMedOccur = dbHelper.getTakeMedicieOccur(idTakeMedOccur, timeOfDay)
        do{

            var period = Period.of(0, 0, 1)
            var modifiedDate = dateEndFormatDate.plus(period)
            var dateString  = modifiedDate.toString()


            var id= UUID.randomUUID().toString()
                var medicineToTake= MedicineToTake(
                id,
                    takeMedOccur.iD,
                    takeMedOccur.iD_MedicineType,
                    takeMedOccur.medicineType_Name,
                    takeMedOccur.dose,
                    takeMedOccur.timeOfDay,
                    takeMedOccur.beforeAfterMeal,
                    dateString,
                "No"
            )
            dbHelper.addMedicineToTake(medicineToTake)
             dateEndFormatDate = LocalDate.parse(dateString, formatDate)
            var compareDateEndWithCurrentDate = dateEndFormatDate.compareTo(newdateEndFormatDate)
        }while(compareDateEndWithCurrentDate < 0 )

        Toast.makeText(applicationContext, "Wydłużono okres żażywania leku", Toast.LENGTH_SHORT).show()

        var activity = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        startActivity(activity)

    }

    private fun shortenPertiodOfTakingMedicine(){

    }

    private fun alertDialogTheSameDate(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Wybrana data jest nieprawidłowa")
        builder.setMessage("Nowa data nie rónżi się od obecej daty zakończeia brania leku")
        builder.setNeutralButton("Wróć"){_,_ ->

        }
        builder.show()
    }

}