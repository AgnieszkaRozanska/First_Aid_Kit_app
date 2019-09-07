package com.example.agnieszka.ar_apteczka.todaysMedicines.showAllMedicinesToday

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.todaysMedicines.objectMedicinesToTake.MedicineToTake
import com.example.agnieszka.ar_apteczka.todaysMedicines.adapter.TakeMedicicnesTodayAdapter
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.android.synthetic.main.activity_show_all_todays_medicines.*
import okhttp3.OkHttpClient
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ShowAllTodaysMedicines : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_todays_medicines)
        setTodayDate()
        setStetho()
        val dbHelper = SQLConector(this)
        dbHelper.removeOldReminders(takeDataToday())
        button_GoToFirstAidKit.setOnClickListener {
            val goToMenu = Intent(applicationContext, Menu::class.java)
            startActivity(goToMenu)
        }


      val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                try {
                    setTextIfListIsEmpty()
                } catch (e: Exception) {}
                handler.postDelayed(this, 300)
            }
        }
        handler.postDelayed(runnable, 300)



    }
     override fun onResume() {
        super.onResume()
        val sqlConector = SQLConector(this)
        var dataToday = takeDataToday()
        var takeTodayMedicinesListMORNING=sqlConector.getAllTakeMedicinesToday(getString(R.string.Morning), dataToday)
        var takeTodayMedicinesListMIDDAY=sqlConector.getAllTakeMedicinesToday(getString(R.string.Midday), dataToday)
        var takeTodayMedicinesListEVENING=sqlConector.getAllTakeMedicinesToday(getString(R.string.Evening), dataToday)

        recyler_view_All_Medicines_To_Take_Today_Morning.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_Medicines_To_Take_Today_Morning.adapter=
            TakeMedicicnesTodayAdapter(
                this,
                takeTodayMedicinesListMORNING
            )
        recyler_view_All_take_Medicines_To_Take_Today_Midday.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_take_Medicines_To_Take_Today_Midday.adapter=
            TakeMedicicnesTodayAdapter(
                this,
                takeTodayMedicinesListMIDDAY
            )
        recyler_view_All_take_Medicine_To_Take_Today_Evening.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyler_view_All_take_Medicine_To_Take_Today_Evening.adapter=
            TakeMedicicnesTodayAdapter(
                this,
                takeTodayMedicinesListEVENING
            )
    }

    private fun setTodayDate(){
        val current = LocalDateTime.now()
        val formatDate = DateTimeFormatter.ofPattern("dd.MM.yyy")

        TextView_DataToday.text=current.format(formatDate)

     }

    private fun setStetho(){
        Stetho.initializeWithDefaults(this)
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    private fun takeDataToday() : String{
        val current = LocalDateTime.now()
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var dateTodayString =current.format(formatDate)
        return dateTodayString.toString()
    }

    private fun setTextIfListIsEmpty(){
        val sqlConector = SQLConector(this)
        var dataToday = takeDataToday()
        var takeTodayMedicinesListMORNING=sqlConector.getAllTakeMedicinesToday(getString(R.string.Morning), dataToday)
        var takeTodayMedicinesListMIDDAY=sqlConector.getAllTakeMedicinesToday(getString(R.string.Midday), dataToday)
        var takeTodayMedicinesListEVENING=sqlConector.getAllTakeMedicinesToday(getString(R.string.Evening), dataToday)
        if(takeTodayMedicinesListEVENING.isNullOrEmpty()) textViewEveningMedicines.visibility = TextView.VISIBLE
        else textViewEveningMedicines.visibility = TextView.INVISIBLE

        if(takeTodayMedicinesListMIDDAY.isNullOrEmpty()) textViewAfternoonMedicines.visibility = TextView.VISIBLE
        else textViewAfternoonMedicines.visibility = TextView.INVISIBLE

        if(takeTodayMedicinesListMORNING.isNullOrEmpty()) textViewMorningMedicines.visibility = TextView.VISIBLE
        else textViewMorningMedicines.visibility = TextView.INVISIBLE
    }


}