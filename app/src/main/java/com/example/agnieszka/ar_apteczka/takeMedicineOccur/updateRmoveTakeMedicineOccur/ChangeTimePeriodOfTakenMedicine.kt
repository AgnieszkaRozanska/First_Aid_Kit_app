package com.example.agnieszka.ar_apteczka.takeMedicineOccur.updateRmoveTakeMedicineOccur

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.showAllTakeMedicineOccur.ActivityShowAllTakeMedicineOccur
import com.example.agnieszka.ar_apteczka.todaysMedicines.objectMedicinesToTake.MedicineToTake
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
    private var afterBeforeMeal = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_time_period_of_taken_medicine)
        downloadAndSetData()

        buttonSaveNewDateEnd.setOnClickListener {
                SaveChangePeriodOfTakingMedicine()
        }

        buttonChooseNewDateEnd.setOnClickListener {
            datePikerNewDataEnd()
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

    private fun datePikerNewDataEnd(){
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

       if(buttonChooseNewDateEnd.text.toString() == "Wybierz datę "){
           noData()
       }else {

           if (intent.hasExtra("dateEnd")) dateEndOfPeriodTaken = intent.getStringExtra("dateEnd")
           dateEndOfPeriodTaken = dateEndOfPeriodTaken.replace(".", "-")
           val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
           var dateEndFormatDate =
               LocalDate.parse(dateEndOfPeriodTaken, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
           var newDateEnd = buttonChooseNewDateEnd.text
           var newdateEndFormatDate = LocalDate.parse(newDateEnd, formatDate)
           var todayDateLocalDate = takeTodayDate()
           val compareDateEndWithTodayDate = todayDateLocalDate.compareTo(newdateEndFormatDate)
           if (compareDateEndWithTodayDate > 0) alertDialogWrongNewDate()
           else {
               val compareDateEndWithCurrentDate = dateEndFormatDate.compareTo(newdateEndFormatDate)
               if (compareDateEndWithCurrentDate > 0) shortenPertiodOfTakingMedicine(
                   dateEndFormatDate,
                   newdateEndFormatDate
               )
               if (compareDateEndWithCurrentDate == 0) alertDialogTheSameDate()
               //if (compareDateStartWithNewDataEnd > 0) alertDialogWrongDateEnd()
               if (compareDateEndWithCurrentDate < 0) extendPeriodOfTakingMedicine(
                   dateEndFormatDate,
                   newdateEndFormatDate
               )
           }
       }
    }

    private fun extendPeriodOfTakingMedicine(dateEndFormatDateOld : LocalDate, newdateEndFormatDate :LocalDate){
        if (intent.hasExtra("timeOfDay")) timeOfDay = intent.getStringExtra("timeOfDay")
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idTakeMedOccur= intent.getStringExtra("IDMedicine_TakeOccur")
        if (intent.hasExtra("afterBeforeMeal")) afterBeforeMeal = intent.getStringExtra("afterBeforeMeal")
        val dbHelper = SQLConector(this)
        dbHelper.updateTakeMedicineOccurDateEnd(idTakeMedOccur, newdateEndFormatDate.toString())
        var dateEndFormatDate = LocalDate.parse(dateEndFormatDateOld.toString(), formatDate)
        var takeMedOccur = dbHelper.getTakeMedicieOccur(idTakeMedOccur, timeOfDay, afterBeforeMeal)
        do{
            var period = Period.of(0, 0, 1)
            var modifiedDate = dateEndFormatDate.plus(period)
            var dateString  = modifiedDate.toString()

            var id= UUID.randomUUID().toString()
                var medicineToTake=
                    MedicineToTake(
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

        Toast.makeText(applicationContext, getString(R.string.ToastExtendedPeriodOfTakingMed), Toast.LENGTH_SHORT).show()
        var activity = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        startActivity(activity)

    }

    private fun shortenPertiodOfTakingMedicine(dateEndFormatDateOld : LocalDate, newdateEndFormatDate :LocalDate){
        if (intent.hasExtra("timeOfDay")) timeOfDay = intent.getStringExtra("timeOfDay")
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idTakeMedOccur= intent.getStringExtra("IDMedicine_TakeOccur")
        if (intent.hasExtra("afterBeforeMeal")) afterBeforeMeal = intent.getStringExtra("afterBeforeMeal")
        val dbHelper = SQLConector(this)
        dbHelper.updateTakeMedicineOccurDateEnd(idTakeMedOccur, newdateEndFormatDate.toString())
        var takeMedOccur = dbHelper.getTakeMedicieOccur(idTakeMedOccur, timeOfDay, afterBeforeMeal)

        var listOfTakeMedicineTodayTheSameType= dbHelper.takeAllInstancesOfTheSameDrug(takeMedOccur)
        var success = dbHelper.removeSingleInstanceTakeTodayMedicie(newdateEndFormatDate, listOfTakeMedicineTodayTheSameType)
        val intentRemove = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        if(success)
        {
            Toast.makeText(applicationContext, getString(R.string.shortenedExtendedPeriodOfTakigMed), Toast.LENGTH_SHORT).show()
        }
        startActivity(intentRemove)
    }

    private fun alertDialogTheSameDate(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertDialogTitleTheSameDate))
        builder.setMessage(getString(R.string.alertDialogMessageTheSameDate))
        builder.setNeutralButton(getString(R.string.back)){_,_ ->
        }
        builder.show()
    }

    private fun alertDialogWrongNewDate(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertDialogTitleWrongDate))
        builder.setMessage(getString(R.string.alertDialogMessageWrongDate))
        builder.setNeutralButton(getString(R.string.back)){_,_ ->
        }
        builder.show()
    }

    private fun alertDialogWrongDateEnd(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertDialogTitleWrongDate))
        builder.setMessage(getString(R.string.AlertIformationShortenData))
        builder.setNeutralButton(getString(R.string.back)){_,_ ->
        }
        builder.show()
    }

    private fun noData(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.TitleAlertNoData))
        builder.setMessage(getString(R.string.AlertMessageChooseDataFirst))
        builder.setNeutralButton(getString(R.string.back)){_,_ ->
        }
        builder.show()
    }

    private fun takeTodayDate() : LocalDate{
        val current = LocalDateTime.now()
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var currenDateString= current.format(formatDate)
        return LocalDate.parse(currenDateString, formatDate)

    }
}
