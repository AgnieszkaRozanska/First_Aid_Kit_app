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
            alertDialog()
        }

    }



    override fun onBackPressed() {
        var activity = Intent(applicationContext, ActivityShowAllMedicines::class.java)
        startActivity(activity)
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

    }

    private fun alertDialog(){
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

        val success = dbHelper.removeMedicineType(idToRemoveMed)


        if(success)
        {
            Toast.makeText(applicationContext, getString(R.string.AttentionToRemoveMedicine), Toast.LENGTH_SHORT).show()
        }
        startActivity(intentRemove)
    }


}
