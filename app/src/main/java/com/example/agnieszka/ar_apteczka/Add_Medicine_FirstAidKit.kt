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



        MedSaveButton.setOnClickListener {

            if(Med_Name_editText.length()>0) intent.putExtra("name", Med_Name_editText.text)
            else Toast.makeText(applicationContext,"Proszę podać nazwę leku", Toast.LENGTH_SHORT)

            if(Med_Kind_editText.length()>0)
                intent.putExtra("haslo", Med_Kind_editText.text)
            else Toast.makeText(applicationContext,"Proszę podać rodzaj leku", Toast.LENGTH_SHORT)

            if(Med_Count_editText.length()>0)
                intent.putExtra("email", Med_Count_editText.text)
            else Toast.makeText(applicationContext,"Proszę podać ilość", Toast.LENGTH_SHORT)

        }




    }

}
