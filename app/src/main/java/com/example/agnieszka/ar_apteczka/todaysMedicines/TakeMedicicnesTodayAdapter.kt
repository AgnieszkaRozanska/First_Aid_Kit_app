package com.example.agnieszka.ar_apteczka.todaysMedicines

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


class TakeMedicicnesTodayAdapter(context: Context, var medicinesToTakeTodayList: ArrayList<MedicineToTake>): RecyclerView.Adapter<MyViewHolderTakeMedToday>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolderTakeMedToday {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val cardViewMedidicnesToTakeToday = layoutInflater.inflate(R.layout.card_view_all_tak_medicines_occur, viewGroup, false)
        return MyViewHolderTakeMedToday(
            cardViewMedidicnesToTakeToday
        )

    }

    override fun getItemCount(): Int {
        return medicinesToTakeTodayList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolderTakeMedToday, position: Int) {
        holder.medName.text = medicinesToTakeTodayList.elementAt(position).nameMedToTake
        holder.medDose.text = "Dawka:       "+medicinesToTakeTodayList.elementAt(position).doseMedToTake.toString()
        holder.medAfterBeforeMeal.text = medicinesToTakeTodayList.elementAt(position).WhenMedToTake

        val context:Context = holder.view.context

        val cardViewMedicine = holder.view.Take_Medicine_Occur_cardView

        if (medicinesToTakeTodayList.elementAt(position).timeOfDayMedToTake =="Rano") {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#F3F1B0"))
        }
        if (medicinesToTakeTodayList.elementAt(position).timeOfDayMedToTake=="Wieczór") {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"))
        }
        if (medicinesToTakeTodayList.elementAt(position).timeOfDayMedToTake=="Popołudnie") {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#F366B1"))
        }




      /*  cardViewMedicine.setOnClickListener {

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
        */
    }

}


class MyViewHolderTakeMedToday(val view: View) : RecyclerView.ViewHolder(view) {
    var medName : TextView = view.findViewById(R.id.MedicineName_cardView)
    var medDose: TextView = view.findViewById(R.id.MedicineDose_cardView)
    var medAfterBeforeMeal: TextView = view.findViewById(R.id.MedicineMeal_cardView)


}