package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.showAllTakeMedicineOccur.ActivityShowAllTakeMedicineOccur
import kotlinx.android.synthetic.main.activity_add__reminder.*
import kotlinx.android.synthetic.main.activity_add_update_remove_reminder.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddUpdateRemoveReminder : AppCompatActivity() {

    var timeformat = SimpleDateFormat("HH:mm", Locale.US)
    var idTakeMedOccur = ""
    var time = ""
    var ifHaveReminder = false
    var timeOfDay = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_remove_reminder)

        setData()
        buttonChooseTime.setOnClickListener {
            timePiker()
        }
        textViewChoosenNewTime.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkTimeWithTimeOfDay()
            }

        })
        buttonRemoveReminder.setOnClickListener {
            alertDialogRemoveReminder()
        }
        ButtonSaveAddUpdateReminder.setOnClickListener {
            if(textViewChoosenNewTime.text == "Nowy czas"){
                alertDialogLackOfTime()
            }else{
                if(ifHaveReminder){
                    updateReminder(idTakeMedOccur)
                }else{
                    addReminder()
                }
            }
        }
    }

    private fun setData(){
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idTakeMedOccur= intent.getStringExtra("IDMedicine_TakeOccur")
        if (intent.hasExtra("nameMedTakeOcur"))  UpdateReminder_MedicineName.text = intent.getStringExtra("nameMedTakeOcur")
        if (intent.hasExtra("ifHaveReminder"))  ifHaveReminder = intent.getBooleanExtra("ifHaveReminder", false)
        if (intent.hasExtra("time"))  time= intent.getStringExtra("time")
        if (intent.hasExtra("timeOfDay")) timeOfDay = intent.getStringExtra("timeOfDay")

        if(ifHaveReminder){
            textViewconstinformation.setText("Lek posiada przypominajkę na godzinę: " + time)
        }else{
            textViewconstinformation.setText("Lek nie posiada przypominajki")
        }
    }

    private fun timePiker(){
        val now = Calendar.getInstance()
        try {
            if(textViewChoosenNewTime.text != "Nowy czas")
            {val date=timeformat.parse(textViewChoosenNewTime.text.toString())
                now.time =date
            }
        } catch (e: Exception){ e.printStackTrace()}
        val timePiker = TimePickerDialog( this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
            val selectedTime= Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedTime.set(Calendar.MINUTE, minute)
            Toast.makeText(this, "Ustawiono: " + timeformat.format(selectedTime.time), Toast.LENGTH_LONG).show()
            textViewChoosenNewTime.text=timeformat.format(selectedTime.time)
        },
            now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
        timePiker.show()
    }

    private fun checkTimeWithTimeOfDay(){
        if(textViewChoosenNewTime.text!="Nowy czas") {
            if (intent.hasExtra("timeOfDay")) timeOfDay = intent.getStringExtra("timeOfDay")
            var timeTab = textViewChoosenNewTime.text.split(':')
            var choosenNewTime = textViewChoosenNewTime.text.toString()
            if(timeOfDay==getString(R.string.Morning) && timeTab[0].toInt()>12) alertDialogTime(timeOfDay, choosenNewTime)
            if(timeOfDay==getString(R.string.Midday) && (timeTab[0].toInt()<12 || timeTab[0].toInt()>18)) alertDialogTime(timeOfDay, choosenNewTime)
            if(timeOfDay==getString(R.string.Evening) && timeTab[0].toInt()<16) alertDialogTime(timeOfDay, choosenNewTime)
        }
    }

    private fun alertDialogTime(timeOfDay :String, time : String){
        val builder = AlertDialog.Builder(this)
        var correctFormTimeOfDay = correctFormTimeOfDay(timeOfDay)
        builder.setTitle(getString(R.string.alertDialogTitleTime))
        builder.setMessage("Lek ma zostać zażyty $correctFormTimeOfDay, a wybrana godzina to $time")
        builder.setNeutralButton(getString(R.string.OK)){ _, _ ->
        }
        builder.show()
    }

    private fun correctFormTimeOfDay(timeOfDay : String) : String{
        var result = ""
        if(timeOfDay == getString(R.string.Morning)) result = getString(R.string.correctFormMorning)
        if(timeOfDay == getString(R.string.Midday)) result = getString(R.string.correctFormMidday)
        if(timeOfDay == getString(R.string.Evening)) result = getString(R.string.correctFormEvening)
        return result
    }

    private fun alertDialogRemoveReminder(){
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idTakeMedOccur= intent.getStringExtra("IDMedicine_TakeOccur")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.RemoveTakeMedOccurAlertTitle))
        builder.setMessage("Czy chcesz usunąć przypominajkę dla tego leku?")
        builder.setPositiveButton(getString(R.string.AlertDialogRemoveTakeMedOccurYes)) { dialog: DialogInterface, which: Int ->
            removeReminder(idTakeMedOccur)
        }
        builder.setNegativeButton(getString(R.string.AlertDialogRemoveTakeMedOccurNo)) { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }

    private fun removeReminder(idTakeMedOccur : String){
        val dbHelper = SQLConector(applicationContext)
        val intentRemove = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        val successRemoveReminders = dbHelper.removeReminder(idTakeMedOccur)

        if(textViewconstinformation.text != "Lek nie posiada przypominajki"){
            if(successRemoveReminders)
            {
                Toast.makeText(this, "Usunięto wszystkie przypominajki dla tego leku", Toast.LENGTH_LONG).show()
            }
            startActivity(intentRemove)
        }else{
            alertDialogLackNotification()
        }
    }

    private fun updateReminder(idTakeMedOccur : String){
        val intentUpdate = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        var newTimeOfReminder = textViewChoosenNewTime.text.toString()
        if(newTimeOfReminder == "Nowy czas"){
            alertDialogLackOfTime()
        }else{
            if (intent.hasExtra("time"))  time= intent.getStringExtra("time")
            if(newTimeOfReminder == time){
                alertDialogTheSameTime()
            }else{
                val dbHelper = SQLConector(applicationContext)
                var successUpdateReminder = dbHelper.updateReminder(idTakeMedOccur, newTimeOfReminder)
                if(successUpdateReminder)
                {
                    Toast.makeText(this, "Godzina prypominajki została zaktualizowana", Toast.LENGTH_LONG).show()
                }
                startActivity(intentUpdate)
            }
        }
    }

    private fun alertDialogLackOfTime(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Brak danych!")
        builder.setMessage("Musisz wybrać nową godzinę powiadomienia. Kliknij na przycisk Ustaw czas i wybierz godzinę")
        builder.setNeutralButton(getString(R.string.OK)){ _, _ ->
        }
        builder.show()
    }

    private fun alertDialogTheSameTime(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ta sama godzina")
        builder.setMessage("Została wybrana ta sama godzina. Musisz wybrać nową godzinę powiadomienia. Kliknij na przycisk Ustaw czas i wybierz godzinę")
        builder.setNeutralButton(getString(R.string.OK)){ _, _ ->
        }
        builder.show()
    }

    private fun alertDialogLackNotification(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertDialogTitleLackOfNotification))
        builder.setMessage(getString(R.string.alertDialogMessageLackOfNotification))
        builder.setNeutralButton(getString(R.string.back)){_,_ ->
        }
        builder.show()
    }

    private fun addReminder(){
        val dbHelper = SQLConector(applicationContext)
        val intentAdd = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        var newTime = textViewChoosenNewTime.text.toString()
        idTakeMedOccur
        var successAddReminderDiuringUpdate = dbHelper.addReminderDiuringUpdate(idTakeMedOccur, newTime)
        if(successAddReminderDiuringUpdate)
        {
            Toast.makeText(this, "Przypominajka została dodana", Toast.LENGTH_LONG).show()
        }
        startActivity(intentAdd)
    }
}
