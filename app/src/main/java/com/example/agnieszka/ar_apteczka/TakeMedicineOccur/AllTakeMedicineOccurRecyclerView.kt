package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_all_take_medicine_occur_recycler_view.*


class AllTakeMedicineOccurRecyclerView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_take_medicine_occur_recycler_view)
    }


    override fun onBackPressed() {
        var activity = Intent(applicationContext, MedicinesMenu::class.java)
        startActivity(activity)
    }


    override fun onResume() {
        super.onResume()


        val sqlConector = SQLConector(this)
        val  take_medicines_occur_listMORNING=sqlConector.getAllTakeMedicineOccursinMorning()
        val  take_medicines_occur_listMIDDAY=sqlConector.getAllTakeMedicineOccursinMidday()
        val  take_medicines_occur_listEVENING=sqlConector.getAllTakeMedicineOccursinEvening()

        recyler_view_All_take_MedOccurMorning.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_take_MedOccurMorning.adapter=card_view_All_TakeMedicinesOccur(this, take_medicines_occur_listMORNING)


        recyler_view_All_take_MedOccurMidday.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_take_MedOccurMidday.adapter=card_view_All_TakeMedicinesOccur(this, take_medicines_occur_listMIDDAY)


        recyler_view_All_take_MedOccurEvening.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_take_MedOccurEvening.adapter=card_view_All_TakeMedicinesOccur(this, take_medicines_occur_listEVENING)


    }



}


