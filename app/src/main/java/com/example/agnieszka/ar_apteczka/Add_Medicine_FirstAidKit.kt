package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add__medicine__first_aid_kit.*

class Add_Medicine_FirstAidKit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__medicine__first_aid_kit)

        //var list_of_items = arrayOf("Item 1", "Item 2", "Item 3")

        if (intent.hasExtra("name")) Med_Name_editText.setText(intent.getStringExtra("name"))
        if (intent.hasExtra("kind")) Med_Kind_editText.setText(intent.getStringExtra("kind"))
        if (intent.hasExtra("count")) Med_Count_editText.setText(intent.getStringExtra("count"))
        if (intent.hasExtra("description")) Med_Description_editText.setText(intent.getStringExtra("description"))

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

                var Activity: Intent = Intent(applicationContext, firstAndKit::class.java)
                startActivity(Activity)
            }


        }
    }


