package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import com.example.agnieszka.ar_apteczka.card_view_All_Medicines
import kotlinx.android.synthetic.main.activity_all__medicines.*

class ActivityShowAllMedicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all__medicines)
    }


    override fun onBackPressed() {
        var activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
        startActivity(activity)
    }


    override fun onResume() {
        super.onResume()


        val sqlConector = SQLConector(this)
        sqlConector.writableDatabase

        val  medicinesList=sqlConector.getAllMedicineTypes()



        recyler_view_med.layoutManager = LinearLayoutManager(this)
        recyler_view_med.adapter = card_view_All_Medicines(this, medicinesList)


    }



}
