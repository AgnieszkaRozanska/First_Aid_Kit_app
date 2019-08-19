package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import kotlinx.android.synthetic.main.activity_add__reminder.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ActivityAddReminder : AppCompatActivity() {

    var timeformat = SimpleDateFormat("HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__reminder)

        buttonReminderChooseTime.setOnClickListener {
            timePiker()
        }

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
}
