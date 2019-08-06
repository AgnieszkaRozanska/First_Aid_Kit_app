package com.example.agnieszka.ar_apteczka.takeMedicineOccur.updateRmoveTakeMedicineOccur

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.showAllTakeMedicineOccur.ActivityShowAllTakeMedicineOccur
import kotlinx.android.synthetic.main.activity_update_remove_menu_take_medicin_occur.*

class ActivityUpdateRemoveMenuTakeMedicinOccur : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_remove_menu_take_medicin_occur)

        setData()

        Button_RemoveTakeMedicineOccur.setOnClickListener {
            alertDialogRemoveTakeMedOccur()
        }

        button_UpdateDoseTakeMedicineOccur.setOnClickListener {
            downloadDataUpdateDose()
        }

    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        startActivity(activity)
    }

    private fun setData(){
        if (intent.hasExtra("nameTakeOccur")) UpdateRemoveTakeMedicineOccur_MedicineName.text = intent.getStringExtra("nameTakeOccur")
        if (intent.hasExtra("dose")) updateRemoveTakeMedicineOccur_Dose.text = intent.getStringExtra("dose")
        if (intent.hasExtra("timeOfDay")) updateRemoveTakeMedicineOccur_MedicineTimeofDay.text = intent.getStringExtra("timeOfDay")
        if (intent.hasExtra("afterBeforeMeal")) updateRemoveTakeMedicineOccur_MedicineAfterBeforeMeal.text = intent.getStringExtra("afterBeforeMeal")
    }

    private fun alertDialogRemoveTakeMedOccur(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.RemoveTakeMedOccurAlertTitle))
        builder.setMessage(getString(R.string.AlertDialogMessage))
        builder.setPositiveButton(getString(R.string.AlertDialogRemoveTakeMedOccurYes)) { dialog: DialogInterface, which: Int ->
            removeTakemedicineOccur()
        }
        builder.setNegativeButton(getString(R.string.AlertDialogRemoveTakeMedOccurNo)) { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }

    private fun downloadDataUpdateDose(){
        val intentEdit = Intent(applicationContext, ActivityUpdateDoseTakeMedicineOccur::class.java)
        val medNameDoseEdit=UpdateRemoveTakeMedicineOccur_MedicineName.text
        val medDoseDoseEdit= updateRemoveTakeMedicineOccur_Dose.text
        var idToUpdateDose=""
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idToUpdateDose= intent.getStringExtra("IDMedicine_TakeOccur")
        intentEdit.putExtra("nameMedTakeOcur", medNameDoseEdit)
        intentEdit.putExtra("Dose", medDoseDoseEdit)
        intentEdit.putExtra("IDMedicine_TakeOccur", idToUpdateDose)
        startActivity(intentEdit)

    }

    private fun removeTakemedicineOccur(){
        val intentRemove = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        val dbHelper = SQLConector(applicationContext)
        var idToRemoveTakemedoccu=""
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idToRemoveTakemedoccu= intent.getStringExtra("IDMedicine_TakeOccur")
        val successRemoveMedOccur = dbHelper.removeTakeMedicineOccur(idToRemoveTakemedoccu)
        val successRemoveTakeMed = dbHelper.removeTakeTodayMedicie(idToRemoveTakemedoccu)
        if(successRemoveMedOccur && successRemoveTakeMed)
        {
            Toast.makeText(applicationContext, getString(R.string.UpdateRemoveMenuRemoveAttention), Toast.LENGTH_SHORT).show()
        }
        startActivity(intentRemove)
    }

}
