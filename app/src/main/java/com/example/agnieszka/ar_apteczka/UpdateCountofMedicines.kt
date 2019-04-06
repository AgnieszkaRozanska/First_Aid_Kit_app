package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.FirstAidKitMenu
import kotlinx.android.synthetic.main.activity_update__count_of__medicines.*

class UpdateCountofMedicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update__count_of__medicines)


        if (intent.hasExtra("name")) Update_Count_of_Med_Name.setText(intent.getStringExtra("name"))
       // if (intent.hasExtra("kind")) Med_Kind_editText.setText(intent.getStringExtra("kind"))
       if (intent.hasExtra("count")) Update_Count_of_Med_Count.setText(intent.getStringExtra("count"))
       // if (intent.hasExtra("description")) Med_Description_editText.setText(intent.getStringExtra("description"))

//
        Update_Count_of_Med_Update.setOnClickListener {
            UprageMed()
        }
    }

    fun UprageMed() {
        val dbHelper = SQLConector(this)
        val count = Update_Count_of_Med_Count.toString().toInt()
        val id= intent.getStringExtra("IDMedicine")

        val ifsuccess= dbHelper.updateMedicineTypeDoses(id, count)

        if(ifsuccess)
        {
            Toast.makeText(applicationContext, "Lek został zaktualizowany", Toast.LENGTH_SHORT).show()

            var Activity: Intent = Intent(applicationContext, FirstAidKitMenu::class.java)
            startActivity(Activity)
        }
    }
}
