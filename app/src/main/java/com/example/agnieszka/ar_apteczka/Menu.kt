package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        Button_FirstAidKit.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do drugiego okna

            var Activity2: Intent = Intent(applicationContext, firstAndKit::class.java)
            startActivity(Activity2)
        }


        Button_YourAllMedicines.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do drugiego okna

            var Activity3: Intent = Intent(applicationContext, Medicines::class.java)
            startActivity(Activity3)
        }

    }
}
