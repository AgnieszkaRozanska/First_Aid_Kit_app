package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.MedicineType
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.NotificationAmountMed
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import kotlinx.android.synthetic.main.activity_notification_of_small_amount_of_the_drug.*
import java.util.*

class NotificationOfSmallAmountOfTheDrug : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_of_small_amount_of_the_drug)

        NotificationCount_SaveButton.setOnClickListener {
            saveMed()
            saveNotification()
        }

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
        val success= dbHelper.addNotification(notification)

        if(success)
        {
            Toast.makeText(applicationContext, getString(R.string.NotificationSuccessToast), Toast.LENGTH_LONG).show()
            val activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
            startActivity(activity)
        }

       }

}
