package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.Menu
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.showAllMedicines.ActivityShowAllMedicines
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import kotlinx.android.synthetic.main.activity_notification_of_small_amount_of_the_drug.*
import java.util.*

class NotificationOfSmallAmountOfTheDrug : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_of_small_amount_of_the_drug)

        NotificationCount_SaveButton.setOnClickListener {

            var alarmUnitInStock= Notification_UnitInStock.text.toString()
            if(alarmUnitInStock.isEmpty() || alarmUnitInStock.toInt()<=0 ) alertDialogNullAmout()
            else {
                saveMed()
                saveNotification()
            }
        }

    }

    override fun onBackPressed() {
        alertDialog()
    }

    private fun alertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertNotificationBackTitle))
        builder.setMessage(getString(R.string.alertNotificationBackMessage))
        builder.setPositiveButton(getString(R.string.AlertDialogYes)) { dialog: DialogInterface, which: Int ->
            saveMed()
            val activityGoToMenu = Intent(applicationContext, Menu::class.java)
            startActivity(activityGoToMenu)
            Toast.makeText(applicationContext, getString(R.string.ToastMedicineAddedWithoutNotification), Toast.LENGTH_LONG).show()

        }
        builder.setNegativeButton(getString(R.string.AlertDialogNo)) { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }


    private fun saveMed(){

        var idMed=""
        if (intent.hasExtra("idMEd"))  idMed= intent.getStringExtra("idMEd")
        var nameMed=""
        if (intent.hasExtra("name"))  nameMed= intent.getStringExtra("name")
        var kindMed=""
        if (intent.hasExtra("kind"))  kindMed= intent.getStringExtra("kind")
        var description=""
        if (intent.hasExtra("description"))  description= intent.getStringExtra("description")
        var count=""
        if (intent.hasExtra("count"))  count= intent.getStringExtra("count")
        var activedose=""
        if (intent.hasExtra("activedosestext"))  activedose= intent.getStringExtra("activedosestext")


        val dbHelper = SQLConector(this)
        val medicine = MedicineType(
            idMed,
            nameMed,
            kindMed,
            description,
            count.toInt(),
            activedose
        )
        dbHelper.addMedicine(medicine)

   }

    private fun saveNotification(){
        var idMed=""
        if (intent.hasExtra("idMEd"))  idMed= intent.getStringExtra("idMEd")
        val idNotification= UUID.randomUUID().toString()
        var alarmUnitInStock= Notification_UnitInStock.text.toString().toInt()


            val notification = NotificationAmountMed(
                idNotification,
                idMed,
                alarmUnitInStock
            )
            val dbHelper = SQLConector(this)
            val success = dbHelper.addNotification(notification)

            if (success) {
                Toast.makeText(applicationContext, getString(R.string.NotificationSuccessToast), Toast.LENGTH_LONG)
                    .show()
                val activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
                startActivity(activity)
            }

       }

    private fun alertDialogNullAmout(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Uzupełnij brakujące dane")
        builder.setMessage("Podaj ilość tabetkek poniżej których ma nastąpić powiadomienie")
        builder.setNeutralButton("Wróć"){_,_ ->

        }
        builder.show()
    }



}
