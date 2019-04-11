package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_add__take_medicine_occour.*
import java.util.*

class AddTakeMedicineOccour : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__take_medicine_occour)

        Add_Med_Occour_Next_Button.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do MENU

            Next()
            var Activity: Intent = Intent(applicationContext, AddTakeMedicineOccur2::class.java)
            startActivity(Activity)
        }

    }

    fun Next(){

        val id= UUID.randomUUID().toString()
        val dose: String = Add_Med_Occour_Dose_EditText.text.toString()
        val radiobuttonId: Int = Add_Med_Occour_TimeOfDay_radioGroup.checkedRadioButtonId
        val timeOfTheDay : TakeMedicineOccur.TimeOfDay



        when (radiobuttonId) {
            findViewById<RadioButton>(R.id.RadioButton_Morning).id -> timeOfTheDay = TakeMedicineOccur.TimeOfDay.rano
            findViewById<RadioButton>(R.id.RadioButton_Afternoon).id -> timeOfTheDay = TakeMedicineOccur.TimeOfDay.poludnie
            findViewById<RadioButton>(R.id.RadioButton_Evening).id -> timeOfTheDay = TakeMedicineOccur.TimeOfDay.wieczor
            else -> timeOfTheDay = TakeMedicineOccur.TimeOfDay.rano
        }

        Toast.makeText(applicationContext, timeOfTheDay.toString(), Toast.LENGTH_LONG).show()


    }
}
