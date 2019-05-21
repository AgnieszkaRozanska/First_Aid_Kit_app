package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.SQLConector
import kotlinx.android.synthetic.main.activity_update_remove_menu_take_medicin_occur.*

class UpdateRemoveMenuTakeMedicinOccur : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_remove_menu_take_medicin_occur)


        if (intent.hasExtra("nameTakeOccur")) UpdateRemoveTakeMedicineOccur_MedicineName.setText(intent.getStringExtra("nameTakeOccur"))
        if (intent.hasExtra("dose")) updateRemoveTakeMedicineOccur_Dose.setText(intent.getStringExtra("dose"))
        if (intent.hasExtra("timeOfDay")) updateRemoveTakeMedicineOccur_MedicineTimeofDay.setText(intent.getStringExtra("timeOfDay"))
        if (intent.hasExtra("afterBeforeMeal")) updateRemoveTakeMedicineOccur_MedicineAfterBeforeMeal.setText(intent.getStringExtra("afterBeforeMeal"))


        Button_RemoveTakeMedicineOccur.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Czy jesteś pewien!")
            builder.setMessage("Czy chcesz usunąć ten lek?")
            builder.setPositiveButton("Tak", { dialog: DialogInterface, which: Int ->
                Remove_takemedicineOccur()
            })
            builder.setNegativeButton("Nie", { dialogInterface: DialogInterface, i: Int -> })
            builder.show()

        }



    }

    override fun onBackPressed() {
        var activity: Intent = Intent(applicationContext, AllTakeMedicineOccurRecyclerView::class.java)
        startActivity(activity)
    }


    fun Remove_takemedicineOccur(){
        val intent_remove = Intent(applicationContext, AllTakeMedicineOccurRecyclerView::class.java)
        val dbHelper = SQLConector(applicationContext)
        var id_to_remove_takemedoccu:String=""
        if (intent.hasExtra("IDMedicine_TakeOccur"))  id_to_remove_takemedoccu= intent.getStringExtra("IDMedicine_TakeOccur")

        val ifsuccess = dbHelper.removeTakeMedicineOccur(id_to_remove_takemedoccu)


        if(ifsuccess)
        {
            Toast.makeText(applicationContext, "Lek został usunięty", Toast.LENGTH_SHORT).show()
        }
        startActivity(intent_remove)
    }


}
