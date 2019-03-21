package com.example.agnieszka.ar_apteczka

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class card_view_All_Medicines(context: Context, val db: SQLiteDatabase, var medicineType: ArrayList<MedicineType>): RecyclerView.Adapter<MyViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

// ---------------- Edycja Leku po kliknięciu na Lek ---------------
//-----------------    Update ilości sztuk leków     -----------------


// ---------------- Gdy przytrzymamy Lek to usuwamy go  ---------------


class MyViewHolder(val view: View):RecyclerView.ViewHolder(view)