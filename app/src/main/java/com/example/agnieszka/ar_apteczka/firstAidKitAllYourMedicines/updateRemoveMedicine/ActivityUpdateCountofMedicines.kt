package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.updateRemoveMedicine

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import kotlinx.android.synthetic.main.activity_update__count_of__medicines.*

class ActivityUpdateCountofMedicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update__count_of__medicines)

        val id =setData()

        Update_Count_of_Med_Update.setOnClickListener {
            upgradeMed(id)
        }
    }


    private fun upgradeMed(id:String) {

        val dbHelper = SQLConector(this)
        val count = Update_Count_of_Med_Count.text.toString()

        if( count.isEmpty() || count.toInt()==0)
        {

            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.UpdateCountMedicinesTitleAlert))
            builder.setMessage(getString(R.string.UpdateCountMedicinesMessageAlert))
            builder.setPositiveButton(getString(R.string.back)) { dialog: DialogInterface, which: Int -> }
            builder.show()
        }
        else {
            val success = dbHelper.updateMedicineTypeDoses(id, count.toInt())
            if (success) {
                Toast.makeText(applicationContext, getString(R.string.attentionToUpdateMedicine), Toast.LENGTH_SHORT)
                    .show()

                var activityGoToMenu = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
                startActivity(activityGoToMenu)
            }
        }
    }

    private fun setData():String{
        if (intent.hasExtra("name")) Update_Count_of_Med_Name.text = intent.getStringExtra("name")
        if (intent.hasExtra("count")) Update_Count_of_Med_Count.setText(intent.getStringExtra("count"))
        var id=""
        if (intent.hasExtra("IDMedicine"))  id= intent.getStringExtra("IDMedicine")
        return id
    }


}
