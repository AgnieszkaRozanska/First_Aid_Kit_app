package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_all__medicines.*
import kotlinx.android.synthetic.main.activity_first_and_kit.*

class All_Medicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all__medicines)



    }


    override fun onResume() {
        super.onResume()

        //inicializowanie bazy danyh
        val sqlConector = SQLConector(this)
        val db = sqlConector.writableDatabase

        val  medicines_list=sqlConector.getAllMedicineTypes()



/*
        val cursor = db.query(
            MEDICINE_TABLE_NAME,
            null, null, null,
            null, null, null
        )

        val medicines_list = ArrayList<MedicineType>()
       if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val name=cursor.getString(1)
                val  kindMedicineType = cursor.getString(2)
                val unitInStock = cursor.getInt(3)
                val description = cursor.getString(4)
                val iDMedicine = cursor.getString(0)

                val medicine = MedicineType(name, kindMedicineType, description, unitInStock)

                medicines_list.add(medicine)
                cursor.moveToNext()

            }
        }
        cursor.close()
*/
        recyler_view_med.layoutManager = GridLayoutManager(applicationContext, 2)
        recyler_view_med.adapter = card_view_All_Medicines(applicationContext, db, medicines_list)




    }



}
