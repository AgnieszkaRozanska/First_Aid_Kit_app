package com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_update__count_of__medicines.*

class ActivityUpdateCountofMedicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update__count_of__medicines)

        val id=setData()

        Update_Count_of_Med_Update.setOnClickListener {
            uprageMed(id)
        }
    }

    override fun onBackPressed() {
        var activity = Intent(applicationContext, ActivityUpdateRemoveMedicine::class.java)
        startActivity(activity)
    }

    private fun setData():String{
        if (intent.hasExtra("name")) Update_Count_of_Med_Name.text = intent.getStringExtra("name")
        if (intent.hasExtra("count")) Update_Count_of_Med_Count.setText(intent.getStringExtra("count"))
        var id=""
        if (intent.hasExtra("IDMedicine"))  id= intent.getStringExtra("IDMedicine")
        return  id
    }



    private fun uprageMed(id:String) {

        val dbHelper = SQLConector(this)
        val count = Update_Count_of_Med_Count.text.toString()


        val success= dbHelper.updateMedicineTypeDoses(id, count.toInt())

        if(success)
        {
            Toast.makeText(applicationContext, getString(R.string.attentionToUpdateMedicine), Toast.LENGTH_SHORT).show()

            var activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
            startActivity(activity)
        }
    }
}
