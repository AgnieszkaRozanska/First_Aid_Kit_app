package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.addMedicine

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.notlification.NotificationOfSmallAmountOfTheDrug
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.drugsDataBase.Drug
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.MedicineType
import com.example.agnieszka.ar_apteczka.validationDataSoThatTheAreNotZero
import kotlinx.android.synthetic.main.activity_add__medicine__first_aid_kit.*
import kotlinx.android.synthetic.main.custom_alert_other_informations.view.*
import java.util.*
import kotlin.collections.ArrayList

class ActivityAddMedicineFirstAidKit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__medicine__first_aid_kit)

        validationDataSoThatTheAreNotZero(Med_Name_editText, warm_informations_MedName)
        validationDataSoThatTheAreNotZero(Med_Kind_editText, warm_informations_MedKind)
        validationDataSoThatTheAreNotZero(Med_Count_editText, warm_informations_MedCount)

        var dbHelper = SQLConector(this)
        var listOfDrugs =  dbHelper.getAllDrugs()

        //
        var list = onlyNamesOfDrugs(listOfDrugs)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list)
        Med_Name_editText.setAdapter(adapter)
        Med_Name_editText.threshold = 1


        Med_Name_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var drug = ifExists(Med_Name_editText.text.toString(), listOfDrugs)
                if(drug.nameDrug == Med_Name_editText.text.toString()){
                    Med_Kind_editText.setText(drug.kind)
                    Med_Active_Dose_editText.setText(drug.power)
                    Med_Description_editText.setText("Substancje czynne: " + drug.activeDose)
                }
            }

        })

        buttonAddOtherInformations.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_other_informations, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.buttonSave.setOnClickListener {
                mAlertDialog.dismiss()
                var msg = ""


                    if (mDialogView.checkBoxCarPermission.isChecked) msg += "nie można prowadzić samochodu, "
                    if (mDialogView.checkBoxAlcoholPermission.isChecked) msg += "nie można spożywać alkoholu, "
                    if (mDialogView.checkBoxPregnantPermission.isChecked) msg += "nie zażywać w ciąży, "
                    if (mDialogView.checkBoxFeedPermission.isChecked) msg += "nie karmic piersią, "
                    if (mDialogView.checkBoxLocateColdPermission.isChecked) msg += "przechowywać w chłodnym miejscu, "
                    if (mDialogView.checkBoxLocatedPermission.isChecked) msg += "przechowywać w temp. pokojowej, "
                    if (mDialogView.checkBoxOtherMedicinesrPermission.isChecked) {
                        msg += "nie łączyć z: " + mDialogView.EditTextOtherMedicines.text + ", "
                    }
                    if (mDialogView.checkBoxOtherPermission.isChecked) msg += mDialogView.editTextOther.text
                var tempText = Med_Description_editText.text.toString()
                Med_Description_editText.setText(tempText + msg)

            }
            //cancel button click of custom layout
            mDialogView.dialogCancelBtn.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }



    }

    override fun onBackPressed() {
        val activityGoToMenu = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
        startActivity(activityGoToMenu)
    }


        fun addMed(view: View){
            val dbHelper = SQLConector(this)
            val id= UUID.randomUUID().toString()
            val name: String = Med_Name_editText.text.toString()
            val kind: String = Med_Kind_editText.text.toString()
            val count: String = Med_Count_editText.text.toString()
            val description: String = Med_Description_editText.text.toString()
            val activedosestext:String = Med_Active_Dose_editText.text.toString()
            val ifExists =dbHelper.checkIfDrugAlreadyExists(name, activedosestext)

            if(name.isEmpty() || kind.isEmpty() || count.isEmpty())
            {
                alertDialogLackOfData()
            }
            else{
                if(ifExists == true){
                    alertDialogMedExists()
                }else {

                    val medicine = MedicineType(
                        id,
                        name,
                        kind,
                        description,
                        count.toInt(),
                        activedosestext
                    )
                    val success = dbHelper.addMedicine(medicine)

                    if (success) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.infortionMedicinWasAdded),
                            Toast.LENGTH_SHORT
                        ).show()

                        val activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
                        startActivity(activity)
                    }
                }
            }
        }

    fun downloadData(view: View) {
        val dbHelper = SQLConector(this)
        val id = UUID.randomUUID().toString()
        val name: String = Med_Name_editText.text.toString()
        val kind: String = Med_Kind_editText.text.toString()
        val count: String = Med_Count_editText.text.toString()
        val description: String = Med_Description_editText.text.toString()
        val activedosestext: String = Med_Active_Dose_editText.text.toString()
        val ifExists =dbHelper.checkIfDrugAlreadyExists(name, activedosestext)
        if(name.isEmpty() || kind.isEmpty() || count.isEmpty())
        {
            alertDialogLackOfData()
        } else{
            if(ifExists == true) {
                alertDialogMedExists()
            }else {
                val activityToAddNotificatioCount =
                    Intent(applicationContext, NotificationOfSmallAmountOfTheDrug::class.java)


                activityToAddNotificatioCount.putExtra("idMEd", id)
                activityToAddNotificatioCount.putExtra("name", name)
                activityToAddNotificatioCount.putExtra("kind", kind)
                activityToAddNotificatioCount.putExtra("count", count)
                activityToAddNotificatioCount.putExtra("description", description)
                activityToAddNotificatioCount.putExtra("activeDoses", activedosestext)

                startActivity(activityToAddNotificatioCount)
            }
        }
    }

    private fun alertDialogLackOfData(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.attentiontoAddMedicine))
        builder.setMessage(getString(R.string.fillTheData))
        builder.setPositiveButton(getString(R.string.back)) { dialog: DialogInterface, which: Int -> }
        builder.show()
    }


    private fun alertDialogMedExists(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertDialogTileMedExists))
        builder.setMessage(getString(R.string.alertDialogMessageMedExists))
        builder.setNeutralButton(getString(R.string.alertDialogBack)){ _, _ ->
        }
        builder.show()
    }

    private  fun onlyNamesOfDrugs(listOfDrugs : ArrayList<Drug>) : ArrayList<String>{
        val arrayListOfNamesDrugs = ArrayList<String>()

        for(i: Drug in listOfDrugs){
            arrayListOfNamesDrugs.add(i.nameDrug)
        }
        return arrayListOfNamesDrugs
    }

    private fun ifExists(nameDrug : String, listOfDrugs : ArrayList<Drug>) : Drug{
        var drug = Drug("", "","","","")
        for(i: Drug in listOfDrugs){
            if(i.nameDrug == nameDrug){
                var drugExists = Drug(i.idDrug, i.nameDrug, i.power, i.kind, i.activeDose)
                return drugExists
            }
        }
        return drug
    }

}


