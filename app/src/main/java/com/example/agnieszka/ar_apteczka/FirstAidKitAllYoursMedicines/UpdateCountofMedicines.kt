package com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_update__count_of__medicines.*

class UpdateCountofMedicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update__count_of__medicines)

        if (intent.hasExtra("name")) Update_Count_of_Med_Name.setText(intent.getStringExtra("name"))
        if (intent.hasExtra("count")) Update_Count_of_Med_Count.setText(intent.getStringExtra("count"))
        var id:String=""
        if (intent.hasExtra("IDMedicine"))  id= intent.getStringExtra("IDMedicine")

        Update_Count_of_Med_Update.setOnClickListener {
            UprageMed(id)
        }
    }

    fun UprageMed(id:String) {

        val dbHelper = SQLConector(this)
        val count = Update_Count_of_Med_Count.text.toString()


        val ifsuccess= dbHelper.updateMedicineTypeDoses(id, count.toInt())

        if(ifsuccess)
        {
            Toast.makeText(applicationContext, "Lek został zaktualizowany", Toast.LENGTH_SHORT).show()

            var Activity: Intent = Intent(applicationContext, FirstAidKitMenu::class.java)
            startActivity(Activity)
        }
    }
}
