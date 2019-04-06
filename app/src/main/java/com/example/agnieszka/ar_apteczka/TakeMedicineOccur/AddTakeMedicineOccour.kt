package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.R
import kotlinx.android.synthetic.main.activity_add__take_medicine_occour.*

class AddTakeMedicineOccour : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__take_medicine_occour)

        Add_Med_Occour_Next_Button.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do MENU

            var Activity: Intent = Intent(applicationContext, AddTakeMedicineOccur2::class.java)
            startActivity(Activity)
        }
    }
}
