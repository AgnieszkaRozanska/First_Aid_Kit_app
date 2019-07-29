package com.example.agnieszka.ar_apteczka.takeMedicineOccur.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.TakeMedicineOccur
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.updateRmoveTakeMedicineOccur.ActivityUpdateRemoveMenuTakeMedicinOccur
import kotlinx.android.synthetic.main.card_view_all_tak_medicines_occur.view.*


class ShowAllTakeMedicinesOccurAdapter(context: Context, var takeMedicineOccurList: ArrayList<TakeMedicineOccur>): RecyclerView.Adapter<MyViewHolderTakeMedOccur>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolderTakeMedOccur {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val cardViewTakeMedicineOccur = layoutInflater.inflate(R.layout.card_view_all_tak_medicines_occur, viewGroup, false)
        return MyViewHolderTakeMedOccur(
            cardViewTakeMedicineOccur
        )

    }

    override fun getItemCount(): Int {
        return takeMedicineOccurList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolderTakeMedOccur, position: Int) {
        holder.medName.text = takeMedicineOccurList.elementAt(position).medicineType_Name
        holder.medDose.text = "Dawka:       "+takeMedicineOccurList.elementAt(position).dose.toString()
        holder.medAfterBeforeMeal.text = takeMedicineOccurList.elementAt(position).beforeAfterMeal

        val context:Context = holder.view.context

        val cardViewMedicine = holder.view.Take_Medicine_Occur_cardView

        if (takeMedicineOccurList.elementAt(position).timeOfDay=="Rano") {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#F3F1B0"))
        }
        if (takeMedicineOccurList.elementAt(position).timeOfDay=="Wieczór") {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"))
        }
        if (takeMedicineOccurList.elementAt(position).timeOfDay=="Popołudnie") {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#F366B1"))
        }


        

        cardViewMedicine.setOnClickListener {

            val intentEdit = Intent(context, ActivityUpdateRemoveMenuTakeMedicinOccur::class.java)
            val medTakOccurNameEdit=takeMedicineOccurList[holder.adapterPosition].medicineType_Name
            val medTakeOccurDoseEdit= takeMedicineOccurList[holder.adapterPosition].dose.toString()
            val medTakeOccurTimeofDayEdit= takeMedicineOccurList[holder.adapterPosition].timeOfDay
            val medTakeOccurBeforAfterMealEdit= takeMedicineOccurList[holder.adapterPosition].beforeAfterMeal
            val idTakeOccurEdit= takeMedicineOccurList[holder.adapterPosition].iD

            intentEdit.putExtra("nameTakeOccur", medTakOccurNameEdit)
            intentEdit.putExtra("dose", medTakeOccurDoseEdit)
            intentEdit.putExtra("IDMedicine_TakeOccur", idTakeOccurEdit)
            intentEdit.putExtra("timeOfDay", medTakeOccurTimeofDayEdit)
            intentEdit.putExtra("afterBeforeMeal", medTakeOccurBeforAfterMealEdit)
            context.startActivity(intentEdit)
        }
    }

}


class MyViewHolderTakeMedOccur(val view: View) : RecyclerView.ViewHolder(view) {
    var medName : TextView = view.findViewById(R.id.MedicineName_cardView)
    var medDose: TextView = view.findViewById(R.id.MedicineDose_cardView)
    var medAfterBeforeMeal: TextView = view.findViewById(R.id.MedicineMeal_cardView)


}