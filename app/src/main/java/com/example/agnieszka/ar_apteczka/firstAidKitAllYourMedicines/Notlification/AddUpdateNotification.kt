package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.Notlification

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.ActivityFirstAidKitMenu
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.updateRemoveMedicine.ActivityUpdateRemoveMedicine
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import kotlinx.android.synthetic.main.activity_add_update_notification.*
import java.util.*

class AddUpdateNotification : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_notification)

        val whichAction=downloadDataAndSet()
        Button_NotificationSave.setOnClickListener {
            if(whichAction=="ADD") addNotification()
            if(whichAction=="UPDATE") updateNotification()
        }
    }


    private fun downloadDataAndSet(): String{
        var whichAction = ""
        if (intent.hasExtra("whichAction"))  whichAction= intent.getStringExtra("whichAction")
        if (intent.hasExtra("name")) textView_NotificationMedName.text = intent.getStringExtra("name")

        return whichAction
    }

    private fun addNotification(){
        var idMed=""
        if (intent.hasExtra("IDMedicine"))  idMed= intent.getStringExtra("IDMedicine")
        val idNotification= UUID.randomUUID().toString()
        var alarmUnitInStock= EditText_NotificationAmount.text.toString().toInt()


        val notification =
            NotificationAmountMed(
                idNotification,
                idMed,
                alarmUnitInStock
            )
        val dbHelper = SQLConector(this)
        val success = dbHelper.addNotification(notification)

        if (success) {
            Toast.makeText(applicationContext, "Dodano powiadomienie", Toast.LENGTH_LONG)
                .show()
            val activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
            startActivity(activity)
        }
    }

    private fun updateNotification(){
        var idMedicine=""
        if (intent.hasExtra("IDMedicine"))  idMedicine= intent.getStringExtra("IDMedicine")
        var updateAmount=  EditText_NotificationAmount.text.toString().toInt()

        val dbHelper = SQLConector(this)
        val success = dbHelper.updateNotificationAmount(idMedicine,updateAmount)
        if (success) {
            Toast.makeText(applicationContext, "Edytowano powiadomienie", Toast.LENGTH_LONG)
                .show()
            val activity = Intent(applicationContext, ActivityFirstAidKitMenu::class.java)
            startActivity(activity)
        }

    }

}
