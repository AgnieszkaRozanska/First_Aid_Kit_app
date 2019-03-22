package com.example.agnieszka.ar_apteczka

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add__medicine__first_aid_kit.*

class Add_Medicine_FirstAidKit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__medicine__first_aid_kit)

        //tworzenie bazy danyh

        // val db=dbHelper.writableDatabase
    }

        fun AddMed(view: View){
            val dbHelper = SQLConector(this)
            val name: String = Med_Name_editText.text.toString()
            val kind: String = Med_Kind_editText.text.toString()
            val count: String = Med_Count_editText.text.toString()
            val description: String = Med_Description_editText.text.toString()


            val ifsuccess= dbHelper.addMedicine(name, kind, count.toInt(), description)

            if(ifsuccess)
            {
                Toast.makeText(applicationContext, "Lek został dodany", Toast.LENGTH_SHORT).show()
            }
        }


        /*

               val save_info_Toast = Toast.makeText(applicationContext, "Lek został dodany", Toast.LENGTH_SHORT)


               if (intent.hasExtra("medname")) MedicineName_cardView.setText(intent.getStringExtra("medname"))
               if (intent.hasExtra("medkind")) Kind_cardView.setText(intent.getStringExtra("medkind"))
               if (intent.hasExtra("medcount")) Count_cardView.setText(intent.getStringExtra("medcount"))
               if (intent.hasExtra("meddescription")) Description_cardView.setText(intent.getStringExtra("meddescription"))

               */




    }


