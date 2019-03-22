package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_first_and_kit.*

class All_Medicines : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all__medicines)


        Button_Powrot.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do MENU

            var Activity3: Intent = Intent(applicationContext, firstAndKit::class.java)
            startActivity(Activity3)
        }


    }
/*
    override fun onResume() {
        super.onResume()

        //inicializowanie bazy danyh
        val sqlConector = SQLConector(applicationContext)
        val db = sqlConector.writableDatabase


        val cursor= db.query(TABLE_NAME,
            null, null, null,
            null, null, null)

        val medicines_list = ArrayList<MedicineType>()
        if(cursor.count>0){
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val medicine= MedicineType("", "", "", 0)
                medicine.name=cursor.getInt(0)
                medicine.kindMedicineType= cursor.getInt(1)
                medicine.unitInStock=cursor.getInt(2)
                medicine.description=cursor.getInt(3)
                medicine.idMedicine=cursor.getInt(4)



                medicines_list.add(medicine)
                cursor.moveToNext()

            }
        }
        cursor.close()

        .layoutManager= GridLayoutManager(applicationContext, 2)
        recyler_view.adapter= CardViewAdapter(applicationContext, db, notes)




    }

    */

}
