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



        /*
               val dbhelper = SQLConector(applicationContext)
               val db = dbhelper.writableDatabase
               val save_info_Toast = Toast.makeText(applicationContext, "Lek został dodany", Toast.LENGTH_SHORT)


               if (intent.hasExtra("medname")) MedicineName_cardView.setText(intent.getStringExtra("medname"))
               if (intent.hasExtra("medkind")) Kind_cardView.setText(intent.getStringExtra("medkind"))
               if (intent.hasExtra("medcount")) Count_cardView.setText(intent.getStringExtra("medcount"))
               if (intent.hasExtra("meddescription")) Description_cardView.setText(intent.getStringExtra("meddescription"))

               */




    }

}
