package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import kotlinx.android.synthetic.main.activity_first_and_kit.*

class ActivityFirstAidKitMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_and_kit)




        Button_Add_Med_toFAidK.setOnClickListener {
            var acivity = Intent(applicationContext, ActivityAddMedicineFirstAidKit::class.java)
            startActivity(acivity)
        }

        Button_All_Med_INFaidK.setOnClickListener {
            var activity2 = Intent(applicationContext, ActivityShowAllMedicines::class.java)
            startActivity(activity2)
        }

    }

    override fun onBackPressed() {
        var activity = Intent(applicationContext, Menu::class.java)
        startActivity(activity)
    }

}
