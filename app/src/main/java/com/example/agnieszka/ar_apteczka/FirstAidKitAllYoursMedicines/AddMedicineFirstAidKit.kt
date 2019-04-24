package com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines

import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import com.example.agnieszka.ar_apteczka.ValidationDataSoThatTheAreNotZero
import kotlinx.android.synthetic.main.activity_add__medicine__first_aid_kit.*
import java.util.*

class AddMedicineFirstAidKit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__medicine__first_aid_kit)

        //------------walidacja danych --------------------
        // nazwa leku, rodzaj nie moze byc puste
        ValidationDataSoThatTheAreNotZero(Med_Name_editText, warm_informations_MedName)
        ValidationDataSoThatTheAreNotZero(Med_Kind_editText, warm_informations_MedKind)
        ValidationDataSoThatTheAreNotZero(Med_Count_editText, warm_informations_MedCount)


    }

        fun AddMed(view: View){
            val dbHelper = SQLConector(this)
            val id= UUID.randomUUID().toString()
            val name: String = Med_Name_editText.text.toString()
            val kind: String = Med_Kind_editText.text.toString()
            val count: String = Med_Count_editText.text.toString()
            val description: String = Med_Description_editText.text.toString()

            if(name.length == 0 || kind.length == 0 || count.length == 0)
            {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Uwaga!")
                builder.setMessage("Uzupełnij brakujące dane!")
                builder.setPositiveButton("Wróć", { dialog: DialogInterface, which: Int -> })

                builder.show()
            }
            else{
                val ifsuccess= dbHelper.addMedicine(id, name, kind, count.toInt(), description)

                if(ifsuccess)
                {
                    Toast.makeText(applicationContext, "Lek został dodany", Toast.LENGTH_SHORT).show()

                    var Activity: Intent = Intent(applicationContext, FirstAidKitMenu::class.java)
                    startActivity(Activity)
                }
            }




        }

    }


