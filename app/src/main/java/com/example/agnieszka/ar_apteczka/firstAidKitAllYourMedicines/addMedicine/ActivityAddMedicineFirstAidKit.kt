package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.addMedicine

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.notlification.NotificationOfSmallAmountOfTheDrug
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.MedicineType
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
        builder.setTitle("Nie można dodać leku")
        builder.setMessage("Lek o podanej nazwie i dawce aktywnej już istnieje")
        builder.setNeutralButton("Wróć"){_,_ ->
        }
        builder.show()
    }
}


