package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_all__medicines.*
import kotlinx.android.synthetic.main.activity_first_and_kit.*

class All_Medicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all__medicines)



    }


    override fun onResume() {
        super.onResume()

        //inicializowanie bazy danyh
        val sqlConector = SQLConector(this)
        val db = sqlConector.writableDatabase

        val  medicines_list=sqlConector.getAllMedicineTypes()



        recyler_view_med.layoutManager = LinearLayoutManager(this)
        recyler_view_med.adapter = card_view_All_Medicines(this, medicines_list)




    }



}
