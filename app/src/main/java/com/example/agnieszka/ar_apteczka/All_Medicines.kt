package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first_and_kit.*

class All_Medicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all__medicines)



        Button_Powrot.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do MENU

            var Activity3: Intent = Intent(applicationContext, firstAndKit::class.java)
            startActivity(Activity3)
        }
    }
}
