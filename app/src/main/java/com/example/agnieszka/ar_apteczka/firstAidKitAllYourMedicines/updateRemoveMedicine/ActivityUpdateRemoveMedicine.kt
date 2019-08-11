package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.updateRemoveMedicine

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.notlification.AddUpdateNotification
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.showAllMedicines.ActivityShowAllMedicines
import kotlinx.android.synthetic.main.activity_update_remove_medicine.*

class ActivityUpdateRemoveMedicine : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_remove_medicine)
        setVaues()
        button_UpdateDoseTakeMedicineOccur.setOnClickListener {
            downloadData()
        }
        Button_RemoveMedicine.setOnClickListener {
            alertDialogRemoveMedicine()
        }
        Button_RemoveNotification.setOnClickListener {
            if(updateRemoveMedicieNotification.text.isEmpty()){
                alertDialogLackNotification()
            } else{
                alertDialogRemoveNotification()
            }
        }
        Button_addUNotificatio.setOnClickListener {
            addUpdateNotifcation()
        }

    }

    override fun onBackPressed() {
        val activityGoToShowAllMedicines = Intent(applicationContext, ActivityShowAllMedicines::class.java)
        startActivity(activityGoToShowAllMedicines)
    }

    private fun downloadData(){
        val intentEdit = Intent(applicationContext, ActivityUpdateCountofMedicines::class.java)
        val medNameCountEdit=UpdateRemoveMedicine_MedicineName.text
        val medCountCountEdit= updateRemoveTakeMedicineOccur_MedicineTimeofDay.text

        var id=""
        if (intent.hasExtra("IDMedicine"))  id= intent.getStringExtra("IDMedicine")


        intentEdit.putExtra("name", medNameCountEdit)
        intentEdit.putExtra("count", medCountCountEdit)
        intentEdit.putExtra("IDMedicine", id)
        startActivity(intentEdit)

    }

    private fun setVaues(){
        if (intent.hasExtra("name")) UpdateRemoveMedicine_MedicineName.text = intent.getStringExtra("name")
        if (intent.hasExtra("count")) updateRemoveTakeMedicineOccur_MedicineTimeofDay.text = intent.getStringExtra("count")
        if (intent.hasExtra("kind")) updateRemoveTakeMedicineOccur_Dose.text = intent.getStringExtra("kind")
        if (intent.hasExtra("description")) updateRemoveTakeMedicineOccur_MedicineAfterBeforeMeal.text = intent.getStringExtra("description")

        var id=""
        if (intent.hasExtra("IDMedicine"))  id= intent.getStringExtra("IDMedicine")
        val dbHelper = SQLConector(applicationContext)
        var amount :String= dbHelper.takeNotificatioMedCount(id)
        if(!amount.isEmpty()) updateRemoveMedicieNotification.text=amount + " tabletek"
        else updateRemoveMedicieNotification.text=amount

    }

    private fun alertDialogRemoveMedicine(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.AlertDialogAttentionAreYouSure))
        builder.setMessage(getString(R.string.AlertDialogQuestion))
        builder.setPositiveButton(getString(R.string.AlertDialogYes)) { dialog: DialogInterface, which: Int ->
            removeMedicine()
        }
        builder.setNegativeButton(getString(R.string.AlertDialogNo)) { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }

    private fun removeMedicine(){
        val intentRemove = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
        val dbHelper = SQLConector(applicationContext)
        var idToRemoveMed=""
        if (intent.hasExtra("IDMedicine"))  idToRemoveMed= intent.getStringExtra("IDMedicine")
        var ifCanRemoveMed = dbHelper.canRemoveMedicine(idToRemoveMed)
        if(ifCanRemoveMed){
            val success = dbHelper.removeMedicineType(idToRemoveMed)
            dbHelper.removeNotificationAboutAmountMedicine(idToRemoveMed)
            if(success)
            {
                Toast.makeText(applicationContext, getString(R.string.AttentionToRemoveMedicine), Toast.LENGTH_SHORT).show()
            }
            startActivity(intentRemove)
        } else alertDialogCannotRemoveMedicie()


    }

    private fun alertDialogRemoveNotification(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.AlertDialogAttentionAreYouSure))
        builder.setMessage("Czy chcesz usunąć przypomnienie")
        builder.setPositiveButton(getString(R.string.AlertDialogYes)) { dialog: DialogInterface, which: Int ->
            removeNotification()
        }
        builder.setNegativeButton(getString(R.string.AlertDialogNo)) { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }

    private fun removeNotification(){
       val intentRemove = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
       val dbHelper = SQLConector(applicationContext)
       var idToRemoveMed=""
       if (intent.hasExtra("IDMedicine"))  idToRemoveMed= intent.getStringExtra("IDMedicine")

        val success = dbHelper.removeNotificationAboutAmountMedicine(idToRemoveMed)
        if(success)
        {
            Toast.makeText(applicationContext, "Usunięto przypomnienie", Toast.LENGTH_SHORT).show()
        }
        startActivity(intentRemove)
    }


    private fun alertDialogLackNotification(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nie można usunąć przypomnienia")
        builder.setMessage("Ten lek nie posiada przypomnienia")
        builder.setNeutralButton("Wróć"){_,_ ->
        }
        builder.show()
    }

    private fun addUpdateNotifcation(){
        var id=""
        var whichAction = ""
        val intentEdit = Intent(applicationContext, AddUpdateNotification::class.java)
        val medNameNotification=UpdateRemoveMedicine_MedicineName.text
        if (intent.hasExtra("IDMedicine"))  id= intent.getStringExtra("IDMedicine")
        if(updateRemoveMedicieNotification.text.isEmpty()) whichAction ="ADD"
         else whichAction = "UPDATE"

        intentEdit.putExtra("IDMedicine", id)
        intentEdit.putExtra("name", medNameNotification)
        intentEdit.putExtra("whichAction", whichAction)

        startActivity(intentEdit)
    }

    private fun alertDialogCannotRemoveMedicie(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nie można usunąć leku")
        builder.setMessage("Ten lek aktualnie zażywasz. Nie możesz go usunąć.")
        builder.setNeutralButton("Wróć"){_,_ ->
        }
        builder.show()
    }

}
