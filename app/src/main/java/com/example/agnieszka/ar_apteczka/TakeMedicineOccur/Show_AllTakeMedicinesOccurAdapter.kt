package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import android.content.Context
import android.content.Intent
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


        val context:Context = holder.view.context

        cardView_medicine.setOnClickListener {

            val intent_edit = Intent(context, UpdateRemoveMenuTakeMedicinOccur::class.java)
            val Med_TakOccur_Name_edit=takeMedicineOccurList[holder.adapterPosition].medicineType
            val Med_TakeOccur_Dose_edit= takeMedicineOccurList[holder.adapterPosition].dose.toString()
            val Med_TakeOccur_TimeofDay_edit= takeMedicineOccurList[holder.adapterPosition].timeOfDay
            val Med_TakeOccur_BeforAfterMeal_edit= takeMedicineOccurList[holder.adapterPosition].beforeAfterMeal
            val id_TakeOccur_edit= takeMedicineOccurList[holder.adapterPosition].iD

            intent_edit.putExtra("nameTakeOccur", Med_TakOccur_Name_edit)
            intent_edit.putExtra("dose", Med_TakeOccur_Dose_edit)
            intent_edit.putExtra("IDMedicine_TakeOccur", id_TakeOccur_edit)
            intent_edit.putExtra("timeOfDay", Med_TakeOccur_TimeofDay_edit)
            intent_edit.putExtra("afterBeforeMeal", Med_TakeOccur_BeforAfterMeal_edit)
            context.startActivity(intent_edit)
        }


    }
}


class MyViewHolderTakeMedOccur(val view: View) : RecyclerView.ViewHolder(view) {
    var medName : TextView = view.findViewById(R.id.MedicineName_cardView)
    var medDose: TextView = view.findViewById(R.id.MedicineDose_cardView)
    var medAfterBeforeMeal: TextView = view.findViewById(R.id.MedicineMeal_cardView)


}