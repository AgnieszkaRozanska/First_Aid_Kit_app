package com.example.agnieszka.ar_apteczka.takeMedicineOccur.showAllTakeMedicineOccur

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.ActivityMedicinesMenu
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.adapter.ShowAllTakeMedicinesOccurAdapter
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.android.synthetic.main.activity_all_take_medicine_occur_recycler_view.*
import okhttp3.OkHttpClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ActivityShowAllTakeMedicineOccur : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_take_medicine_occur_recycler_view)
    }


    override fun onBackPressed() {
        val activity = Intent(applicationContext, ActivityMedicinesMenu::class.java)
        startActivity(activity)
    }


    override fun onResume() {
        super.onResume()
        val sqlConector = SQLConector(this)
        val  takeMedicinesOccurListMORNING=sqlConector.getAllTakeMedicineOccur(getString(R.string.Morning))
        val  takeMedicinesOccurListMIDDAY=sqlConector.getAllTakeMedicineOccur(getString(R.string.Midday))
        val  takeMedicinesOccurListEVENING=sqlConector.getAllTakeMedicineOccur(getString(R.string.Evening))
        recyler_view_All_take_MedOccurMorning.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_take_MedOccurMorning.adapter=
            ShowAllTakeMedicinesOccurAdapter(
                this,
                takeMedicinesOccurListMORNING
            )
        recyler_view_All_take_MedOccurMidday.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_take_MedOccurMidday.adapter=
            ShowAllTakeMedicinesOccurAdapter(
                this,
                takeMedicinesOccurListMIDDAY
            )
        recyler_view_All_take_MedOccurEvening.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_take_MedOccurEvening.adapter=
            ShowAllTakeMedicinesOccurAdapter(
                this,
                takeMedicinesOccurListEVENING
            )
    }


}


