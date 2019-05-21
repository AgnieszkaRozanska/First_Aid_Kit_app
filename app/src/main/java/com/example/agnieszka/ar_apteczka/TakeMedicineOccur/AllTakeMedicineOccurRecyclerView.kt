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

        val  take_medicines_occur_list=sqlConector.getAllTakeMedicineOccurs()

        recyler_view_All_take_MedOccur.layoutManager=LinearLayoutManager(this)
        recyler_view_All_take_MedOccur.adapter=card_view_All_TakeMedicinesOccur(this, take_medicines_occur_list)

    }



}


