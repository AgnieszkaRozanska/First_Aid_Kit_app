package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_medicines.*

class Medicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicines)


        Button_Back.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do drugiego okna

            var Activity3: Intent = Intent(applicationContext, Menu::class.java)
            startActivity(Activity3)
        }

    }
}
