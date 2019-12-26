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
import android.widget.Spinner
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
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

 class ActivityAddMedicineFirstAidKit : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__medicine__first_aid_kit)

        validationDataSoThatTheAreNotZero(Med_Name_editText, warm_informations_MedName)
        validationDataSoThatTheAreNotZero(Med_Kind_editText, warm_informations_MedKind)
        validationDataSoThatTheAreNotZero(Med_Count_editText, warm_informations_MedCount)


       var listOfLines = readTxtFiles()
        var listOfDrugs  = splitAndCreateListofDrugs(listOfLines)

         var list = onlyNamesOfDrugs(listOfDrugs)
         var adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list)
         Med_Name_editText.setAdapter(adapter)
         Med_Name_editText.threshold = 1


        var listKind = arrayOf("tabletki", "kapsułki", "tabletki musujące", "tabletki podjęzykowe", "tabletki drażowane","kapsułki miękkie", "pastylki", "czopki", "tabletki powlekane", "kapsułki twarde", "tabletki do ssania", "tabletki o przedłużonym uwalnianiu")
        var adapterKind = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, listKind)
        Med_Kind_editText.setAdapter(adapterKind)
        Med_Kind_editText.threshold = 1

        Med_Name_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var drug = ifExists(Med_Name_editText.text.toString(), listOfDrugs)
                if(drug.nameDrug + "," + drug.power+ ";" + drug.kind== Med_Name_editText.text.toString()){
                    Med_Kind_editText.setText(drug.kind)
                    Med_Active_Dose_editText.setText(drug.power)
                    Med_Description_editText.setText("Substancje czynne: " + drug.activeDose)
                }else{
                    Med_Kind_editText.setText("")
                    Med_Active_Dose_editText.setText("")
                    Med_Description_editText.setText("")
                }
            }

        })

        buttonAddOtherInformations.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_other_informations, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val  mAlertDialog = mBuilder.show()
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
            mDialogView.dialogCancelBtn.setOnClickListener {
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
            var name = ""
            var nameDrug = Med_Name_editText.text.toString()
            if(nameDrug.contains(',') && nameDrug.contains(';')) {
                var tempTab = nameDrug.split(',')
                name = tempTab[0]
            }else
            {
                name = Med_Name_editText.text.toString()
            }

            val kind: String = Med_Kind_editText.text.toString()
            val count: String = Med_Count_editText.text.toString()
            val description: String = Med_Description_editText.text.toString()
            val activedosestext:String = Med_Active_Dose_editText.text.toString()
            val ifExists =dbHelper.checkIfDrugAlreadyExists(name, activedosestext)

            if(name.isEmpty() || kind.isEmpty() || count.isEmpty())
            {
                alertDialogLackOfData()
            }
            else if(count.toInt()<1)
            {
                alertDialogIncorrectOfCount()
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
        var name = ""
        var nameDrug = Med_Name_editText.text.toString()
        if(nameDrug.contains(',') && nameDrug.contains(';')) {
            var tempTab = nameDrug.split(',')
            name = tempTab[0]
        }else
        {
            name = Med_Name_editText.text.toString()
        }
        val kind: String = Med_Kind_editText.text.toString()
        val count: String = Med_Count_editText.text.toString()
        val description: String = Med_Description_editText.text.toString()
        val activedosestext: String = Med_Active_Dose_editText.text.toString()
        val ifExists =dbHelper.checkIfDrugAlreadyExists(name, activedosestext)
        if(name.isEmpty() || kind.isEmpty() || count.isEmpty())
        {
            alertDialogLackOfData()
        }
        else if(count.toInt()<1)
        {
            alertDialogIncorrectOfCount()
        }
        else{
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

     private fun alertDialogIncorrectOfCount(){
         val builder = AlertDialog.Builder(this)
         builder.setTitle(getString(R.string.attentiontoAddMedicine))
         builder.setMessage(getString(R.string.alertDialogMessageIncorectOfCount))
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

    private fun readTxtFiles(): ArrayList<String> {
        val outputArrayList = ArrayList<String>()
        val inputStream = applicationContext.resources.openRawResource(R.raw.writeparse)

        try {

            val input = DataInputStream(inputStream)
            val br = BufferedReader(InputStreamReader(input))

            var strLine: String?

            val listOfEntries = br.readLines()
            for(i in listOfEntries){
                outputArrayList.add(i)
            }

            br.close()
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return outputArrayList
    }

    //  0               1    2   3        4     5                  6                   7
    //Nalgesin Forte|ludzki|moc|550 mg|postac|tabletki powlekane|substancjaCzynna|Naproxenum natricum,

    private fun splitAndCreateListofDrugs(listOfLines : ArrayList<String>) : ArrayList<Drug>{
        val arrayListOfDrugs = ArrayList<Drug>()
        var id = 1
        for (i  in listOfLines){
            var drugName = ""
            var drugPower = ""
            var drugKind = ""
            var activeSubstance = ""
            var tempTab = i.split('|')
            drugName += tempTab[0]

            var sizeOfTempTab = tempTab.size
            for (j in 0..sizeOfTempTab - 1){

                if(tempTab[j] == "moc") {
                    if(tempTab[j + 1].contains(',')){
                        var powerReplace = tempTab[j + 1].replace(',', '.')
                        drugPower += powerReplace
                    }else{
                        drugPower += tempTab[j + 1]

                    }
                }
                if(tempTab[j] == "postac") drugKind += tempTab[j+1]
                if(tempTab[j] == "substancjaCzynna") activeSubstance += tempTab[j+1]
            }

            var drug =
                Drug(
                    id.toString(),
                    drugName,
                    drugPower,
                    drugKind,
                    activeSubstance
                )
            arrayListOfDrugs.add(drug)

            id +=1
        }
        return arrayListOfDrugs
    }

    private  fun onlyNamesOfDrugs(listOfDrugs : ArrayList<Drug>) : ArrayList<String>{
        val arrayListOfNamesDrugs = ArrayList<String>()

        for(i: Drug in listOfDrugs){
            arrayListOfNamesDrugs.add(i.nameDrug +","+ i.power + ";" + i.kind)
        }
        return arrayListOfNamesDrugs
    }

    private fun ifExists(nameDrug : String, listOfDrugs : ArrayList<Drug>) : Drug{
        var drug = Drug("", "","","","")
        if(nameDrug.contains(',') && nameDrug.contains(';')) {
            var tempTab = nameDrug.split(',')
            var tempTab2 = tempTab[1].split(';')
            for (i: Drug in listOfDrugs) {
                if (i.nameDrug == tempTab[0] && i.power == tempTab2[0]&& i.kind == tempTab2[1]) {
                    var drugExists = Drug(i.idDrug, i.nameDrug, i.power, i.kind, i.activeDose)
                    return drugExists
                }
            }
        }
        return drug
    }

}


