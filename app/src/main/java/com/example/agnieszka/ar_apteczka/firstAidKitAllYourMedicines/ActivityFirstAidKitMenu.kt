package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.addMedicine.ActivityAddMedicineFirstAidKit
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.showAllMedicines.ActivityShowAllMedicines
import kotlinx.android.synthetic.main.activity_first_and_kit.*

class ActivityFirstAidKitMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_and_kit)

        Button_Add_Med_toFAidK.setOnClickListener {
            val acivityGoToAddMedicine = Intent(applicationContext, ActivityAddMedicineFirstAidKit::class.java)
            startActivity(acivityGoToAddMedicine)
        }

        Button_All_Med_INFaidK.setOnClickListener {
            val activityGoToShowAllMedicines = Intent(applicationContext, ActivityShowAllMedicines::class.java)
            startActivity(activityGoToShowAllMedicines)
        }

    }

    override fun onBackPressed() {
        val activityGoToMenu = Intent(applicationContext, Menu::class.java)
        startActivity(activityGoToMenu)
    }

}
