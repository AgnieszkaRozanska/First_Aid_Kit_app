package com.example.agnieszka.ar_apteczka.takeMedicineOccur.updateRmoveTakeMedicineOccur

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder.AddUpdateRemoveReminder
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.showAllTakeMedicineOccur.ActivityShowAllTakeMedicineOccur
import kotlinx.android.synthetic.main.activity_add_update_remove_reminder.*
import kotlinx.android.synthetic.main.activity_update_remove_menu_take_medicin_occur.*

class ActivityUpdateRemoveMenuTakeMedicinOccur : AppCompatActivity() {

    private var dateStartOfPeriodTaken = ""
    private var dateEndOfPeriodTaken = ""
    private var idTakeMedOccur = ""
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
        button_ChangeTakePeriod.setOnClickListener {
            downloadDataToChangeTimePeriod(dateStartOfPeriodTaken, dateEndOfPeriodTaken)
        }

        buttonUpdateRemoveReminder.setOnClickListener {
            val activity = Intent(applicationContext, AddUpdateRemoveReminder::class.java)
            startActivity(activity)
        }

    }

    override fun onBackPressed() {
        val activity = Intent(applicationContext, ActivityShowAllTakeMedicineOccur::class.java)
        startActivity(activity)
    }

    @SuppressLint("SetTextI18n")
    private fun setData(){
        val dbHelper = SQLConector(this)
        if (intent.hasExtra("nameTakeOccur")) UpdateRemoveTakeMedicineOccur_MedicineName.text = intent.getStringExtra("nameTakeOccur")
        if (intent.hasExtra("dose")) updateRemoveTakeMedicineOccur_Dose.text = intent.getStringExtra("dose")
        if (intent.hasExtra("timeOfDay")) updateRemoveTakeMedicineOccur_MedicineTimeofDay.text = intent.getStringExtra("timeOfDay")
        if (intent.hasExtra("afterBeforeMeal")) updateRemoveTakeMedicineOccur_MedicineAfterBeforeMeal.text = intent.getStringExtra("afterBeforeMeal")
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idTakeMedOccur= intent.getStringExtra("IDMedicine_TakeOccur")
        var ifHaveReminder = dbHelper.checkIfMedHaveReminder(idTakeMedOccur)
        if(ifHaveReminder) {
            var time = dbHelper.takeTimeOfReminder(idTakeMedOccur)
            textView_ReminderInfo.setText("Lek posiada przypominajkę na godzinę: " + time)
        }else textView_ReminderInfo.setText("Lek nie posiada przypominajki")

        if (intent.hasExtra("dateStart")) {
            var dateStart = intent.getStringExtra("dateStart")
             dateStartOfPeriodTaken= dateStart.replace("-", ".")
        }
        if (intent.hasExtra("dateEnd")) {
           var dateEnd = intent.getStringExtra("dateEnd")
            dateEndOfPeriodTaken = dateEnd.replace("-", ".")
        }

        updateRemoveTakeMedicineOccur_DateStart.text = "$dateStartOfPeriodTaken  -  $dateEndOfPeriodTaken"
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
        val successRemoveReminders = dbHelper.removeReminder(idToRemoveTakemedoccu)
        if(successRemoveMedOccur && successRemoveTakeMed && successRemoveReminders)
        {
            Toast.makeText(applicationContext, getString(R.string.UpdateRemoveMenuRemoveAttention), Toast.LENGTH_SHORT).show()
        }
        startActivity(intentRemove)
    }

    private fun downloadDataToChangeTimePeriod(dateStart : String, dateEnd : String){
        val intentEdit = Intent(applicationContext, ChangeTimePeriodOfTakenMedicine::class.java)
        val medicneName=UpdateRemoveTakeMedicineOccur_MedicineName.text
        var whenDiuringDay = updateRemoveTakeMedicineOccur_MedicineAfterBeforeMeal.text
        var timeOfDay = ""
        var dateStart = dateStart
        var dateEnd = dateEnd
        var idTakeMedOccur=""
        if (intent.hasExtra("IDMedicine_TakeOccur"))  idTakeMedOccur= intent.getStringExtra("IDMedicine_TakeOccur")
        if (intent.hasExtra("timeOfDay")) timeOfDay = intent.getStringExtra("timeOfDay")
        intentEdit.putExtra("afterBeforeMeal", whenDiuringDay)
        intentEdit.putExtra("nameMedTakeOcur", medicneName)
        intentEdit.putExtra("IDMedicine_TakeOccur", idTakeMedOccur)
        intentEdit.putExtra("dateStart", dateStart)
        intentEdit.putExtra("dateEnd", dateEnd)
        intentEdit.putExtra("timeOfDay", timeOfDay)
        startActivity(intentEdit)

    }



}
