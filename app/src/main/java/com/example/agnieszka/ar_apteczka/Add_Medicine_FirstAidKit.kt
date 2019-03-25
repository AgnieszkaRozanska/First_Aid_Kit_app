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

        /*
        val dbhelper = DataBaseHelper(applicationContext)
        val db = dbhelper.writableDatabase
        val save_info_Toast = Toast.makeText(applicationContext, "Notatka zapisana możesz wyjść", Toast.LENGTH_SHORT)
        val update_info_Toast = Toast.makeText(applicationContext, "Notatka została zedytowana", Toast.LENGTH_LONG)

        if (intent.hasExtra("title")) title_details.setText(intent.getStringExtra("title"))
        if (intent.hasExtra("message")) message_details.setText(intent.getStringExtra("message"))

        */

    }

        fun AddMed(view: View){
            val dbHelper = SQLConector(this)
            val name: String = Med_Name_editText.text.toString()
            val kind: String = Med_Kind_editText.text.toString()
            val count: String = Med_Count_editText.text.toString()
            val description: String = Med_Description_editText.text.toString()

            if (intent.hasExtra("medname")) Med_Name_editText.setText(intent.getStringExtra("medname"))
            if (intent.hasExtra("medkind")) Med_Kind_editText.setText(intent.getStringExtra("medkind"))
            if (intent.hasExtra("medcount")) Med_Count_editText.setText(intent.getStringExtra("medcount"))
            if (intent.hasExtra("meddescription")) Med_Description_editText.setText(intent.getStringExtra("meddescription"))


            val ifsuccess= dbHelper.addMedicine(name, kind, count.toInt(), description)

            if(ifsuccess)
            {
                Toast.makeText(applicationContext, "Lek został dodany", Toast.LENGTH_SHORT).show()

                var Activity: Intent = Intent(applicationContext, firstAndKit::class.java)
                startActivity(Activity)
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


