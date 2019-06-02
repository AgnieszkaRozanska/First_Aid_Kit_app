package com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import com.example.agnieszka.ar_apteczka.validationDataSoThatTheAreNotZero
import kotlinx.android.synthetic.main.activity_add__medicine__first_aid_kit.*
import java.util.*

class ActivityAddMedicineFirstAidKit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__medicine__first_aid_kit)

        validationDataSoThatTheAreNotZero(Med_Name_editText, warm_informations_MedName)
        validationDataSoThatTheAreNotZero(Med_Kind_editText, warm_informations_MedKind)
        validationDataSoThatTheAreNotZero(Med_Count_editText, warm_informations_MedCount)

    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
        startActivity(activity)
    }


        fun addMed(view: View){
            val dbHelper = SQLConector(this)
            val id= UUID.randomUUID().toString()
            val name: String = Med_Name_editText.text.toString()
            val kind: String = Med_Kind_editText.text.toString()
            val count: String = Med_Count_editText.text.toString()
            val description: String = Med_Description_editText.text.toString()

            if(name.isEmpty() || kind.isEmpty() || count.isEmpty())
            {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.attentiontoAddMedicine))
                builder.setMessage(getString(R.string.fillTheData))
                builder.setPositiveButton(getString(R.string.back)) { dialog: DialogInterface, which: Int -> }

                builder.show()
            }
            else{
                val success= dbHelper.addMedicine(id, name, kind, count.toInt(), description)

                if(success)
                {
                    Toast.makeText(applicationContext, getString(R.string.infortionMedicinWasAdded), Toast.LENGTH_SHORT).show()

                    val activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
                    startActivity(activity)
                }
            }
        }

    }


