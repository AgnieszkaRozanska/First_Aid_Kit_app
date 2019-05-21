package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.agnieszka.ar_apteczka.R
import kotlinx.android.synthetic.main.card_view_all_tak_medicines_occur.view.*


class card_view_All_TakeMedicinesOccur(context: Context, var takeMedicineOccurList: ArrayList<TakeMedicineOccur>): RecyclerView.Adapter<MyViewHolderTakeMedOccur>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolderTakeMedOccur {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val cardView_TakeMedicineOccur = layoutInflater.inflate(R.layout.card_view_all_tak_medicines_occur, viewGroup, false)
        return MyViewHolderTakeMedOccur(cardView_TakeMedicineOccur)

    }

    override fun getItemCount(): Int {
        return takeMedicineOccurList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolderTakeMedOccur, position: Int) {
        holder.medName.setText(takeMedicineOccurList.elementAt(position).medicineType)
        holder.medDose.setText("Dawka:       "+takeMedicineOccurList.elementAt(position).dose.toString())
        holder.medTimeOfDay.setText("Pora dnia zażycia:    "+takeMedicineOccurList.elementAt(position).timeOfDay)
        holder.medAfterBeforeMeal.setText(takeMedicineOccurList.elementAt(position).beforeAfterMeal)



        val cardView_medicine = holder.view.Take_Medicine_Occur_cardView

        if (takeMedicineOccurList.elementAt(position).timeOfDay=="Rano") {
            cardView_medicine.setCardBackgroundColor(Color.parseColor("#F3F1B0"))
        }
        if (takeMedicineOccurList.elementAt(position).timeOfDay=="Wieczór") {
            cardView_medicine.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"))
        }
        if (takeMedicineOccurList.elementAt(position).timeOfDay=="Popołudnie") {
            cardView_medicine.setCardBackgroundColor(Color.parseColor("#F366B1"))
        }
    }
}


class MyViewHolderTakeMedOccur(val view: View) : RecyclerView.ViewHolder(view) {
    var medName : TextView = view.findViewById(R.id.MedicineName_cardView)
    var medDose: TextView = view.findViewById(R.id.MedicineDose_cardView)
    var medTimeOfDay : TextView = view.findViewById(R.id.MedicineTimeofDay_cardView)
    var medAfterBeforeMeal: TextView = view.findViewById(R.id.MedicineMeal_cardView)


}