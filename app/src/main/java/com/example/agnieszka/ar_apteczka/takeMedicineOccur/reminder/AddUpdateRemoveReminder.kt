package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
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

}
