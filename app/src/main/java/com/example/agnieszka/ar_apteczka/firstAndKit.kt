package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_first_and_kit.*
import kotlinx.android.synthetic.main.activity_main.*

class firstAndKit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_and_kit)


        Button_Powrot.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do MENU

            var Activity3: Intent = Intent(applicationContext, Menu::class.java)
            startActivity(Activity3)
        }

        Button_Add_Med_toFAidK.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do drugiego okna

            var Activity1: Intent = Intent(applicationContext, Add_Medicine_FirstAidKit::class.java)
            startActivity(Activity1)
        }

        Button_All_Med_INFaidK.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do drugiego okna

            var Activity2: Intent = Intent(applicationContext, All_Medicines::class.java)
            startActivity(Activity2)
        }




    }
}
