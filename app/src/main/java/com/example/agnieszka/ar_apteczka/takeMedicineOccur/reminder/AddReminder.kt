package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.ActivityMedicinesMenu
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.TakeMedicineOccur
import com.example.agnieszka.ar_apteczka.todaysMedicines.MedicineToTake
import kotlinx.android.synthetic.main.activity_add__reminder.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class AddReminder : AppCompatActivity() {

    var timeformat = SimpleDateFormat("HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__reminder)

        buttonReminderChooseTime.setOnClickListener {
            timePiker()
        }
        AddReminder.setOnClickListener {
            if(textViewReminderTime.text=="..."){
                alertDialogLackOfTime()
            }
           else{
                var flaga = true
                //saveMedicineOccur(flaga)
                saveMedicineOcc(flaga)
           }
       }

    }

    override fun onBackPressed() {
        alertDialog()
    }

    private fun timePiker(){
        val now = Calendar.getInstance()
        try {
            if(textViewReminderTime.text !="Wybrana godzina")
            {val date=timeformat.parse(textViewReminderTime.text.toString())
                now.time =date
            }
        } catch (e: Exception){ e.printStackTrace()}
        val timePiker = TimePickerDialog( this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
            val selectedTime= Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedTime.set(Calendar.MINUTE, minute)
            Toast.makeText(this, "Ustawiono: " + timeformat.format(selectedTime.time), Toast.LENGTH_LONG).show()

            textViewReminderTime.text=timeformat.format(selectedTime.time)
        },
            now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
        timePiker.show()
    }

    private fun saveMedicineOcc(flaga : Boolean){
        Toast.makeText(applicationContext, getString(R.string.TakeMedOccurMedAdded), Toast.LENGTH_SHORT).show()
        var id_MedType=""
        if (intent.hasExtra("idMedType"))  id_MedType= intent.getStringExtra("idMedType")
        var choosenMed=""
        if (intent.hasExtra("choosenMed"))  choosenMed= intent.getStringExtra("choosenMed")
        var dose=""
        if (intent.hasExtra("dose"))  dose= intent.getStringExtra("dose")
        var timeOfDay=""
        if (intent.hasExtra("timeOfDay"))  timeOfDay= intent.getStringExtra("timeOfDay")
        var beforeAfterMeal=""
        if (intent.hasExtra("beforeAfterMeal"))  beforeAfterMeal= intent.getStringExtra("beforeAfterMeal")
        var dateStart=""
        if (intent.hasExtra("dateStart"))  dateStart= intent.getStringExtra("dateStart")
        var howManyDays=""
        if (intent.hasExtra("howManyDays"))  howManyDays= intent.getStringExtra("howManyDays")
        var dateEnd=""
        if (intent.hasExtra("dateEnd"))  dateEnd= intent.getStringExtra("dateEnd")

        val dbHelper = SQLConector(this)
        val id= UUID.randomUUID().toString()
        val takeMedOccur= TakeMedicineOccur(
            id,
            id_MedType,
            choosenMed,
            dose.toInt(),
            timeOfDay.toString(),
            beforeAfterMeal.toString(),
            dateStart,
            dateEnd
        )
        val success= dbHelper.addTakeMedicineOccur(takeMedOccur)
        AddMediciesToTake(takeMedOccur, howManyDays, dateStart, flaga)
        if(success)
        {
            Toast.makeText(applicationContext, getString(R.string.TakeMedOccurMedAdded), Toast.LENGTH_SHORT).show()

            var activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
            startActivity(activity)
        }
    }



    private fun AddMediciesToTake(takeMedicineOccour: TakeMedicineOccur, howManyDays : String, dateStart : String, flaga : Boolean){
        val dbHelper = SQLConector(this)
        var date = dateStart
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
                date,
                "No"
            )
            if(flaga) addReminder(medicineToTake)
            dbHelper.addMedicineToTake(medicineToTake)
            var dateDate = LocalDate.parse(date)
            var period = Period.of(0, 0, 1)
            var modifiedDate = dateDate.plus(period)
            date  = modifiedDate.toString()
        }
    }

    private fun addReminder(takeMedToday : MedicineToTake){
      /*  val dbHelper = SQLConector(this)
        var time = textViewReminderTime.text
        //TODOO walidacja czy godzinę wybrał, walidacja pory dnia - czy godzina odpowiada i dopiero to
        var id= UUID.randomUUID().toString()
        var reminder= Reminder(
            id,
            takeMedToday.iD,
            takeMedToday.iDTakeMedOccur,
            takeMedToday.iD_MedicineType,
            takeMedToday.dateSMedToTake,
            time.toString()
        )

        var success = dbHelper.addReminder(reminder)
        if (success) {
            Toast.makeText(applicationContext, "Dodano lek razem z przypominajką", Toast.LENGTH_LONG)
                .show()
            val activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
            startActivity(activity)
        }
        */
    }


    private fun alertDialogLackOfTime(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nie wybrano godziny")
        builder.setMessage("Naciśnij przycisk ustaw godzinę, aby dodać przypominajkę.")
        builder.setNeutralButton("Wróć"){_,_ ->
        }
        builder.show()
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertNotificationBackTitle))
        builder.setMessage("Jeżeli cofniesz zostanie zapisany lek bez przypominajki")
        builder.setPositiveButton(getString(R.string.AlertDialogYes)) { dialog: DialogInterface, which: Int ->
            saveMedicineOcc(false)
            val activityGoToMenu = Intent(applicationContext, ActivityMedicinesMenu::class.java)
            startActivity(activityGoToMenu)
            Toast.makeText(
                applicationContext, "Lek został dodany, lecz bez przypominajki", Toast.LENGTH_LONG).show()

        }
        builder.setNegativeButton(getString(R.string.AlertDialogNo)) { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }
}
