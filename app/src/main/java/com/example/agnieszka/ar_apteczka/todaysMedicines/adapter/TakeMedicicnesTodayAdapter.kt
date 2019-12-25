package com.example.agnieszka.ar_apteczka.todaysMedicines.adapter

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import com.example.agnieszka.ar_apteczka.todaysMedicines.objectMedicinesToTake.MedicineToTake
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

        if (medicinesToTakeTodayList.elementAt(position).timeOfDayMedToTake ==context.getString(R.string.Morning)) {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#F3F1B0"))
        }
        if (medicinesToTakeTodayList.elementAt(position).timeOfDayMedToTake==context.getString(R.string.Evening)) {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"))
        }
        if (medicinesToTakeTodayList.elementAt(position).timeOfDayMedToTake==context.getString(R.string.Midday)) {
            cardViewMedicine.setCardBackgroundColor(Color.parseColor("#F366B1"))
        }


        cardViewMedicine.setOnClickListener {
            val dbHelper = SQLConector(context)
            var idTakeMedToday= medicinesToTakeTodayList[holder.adapterPosition].iD
            var idMedicineType= medicinesToTakeTodayList[holder.adapterPosition].iD_MedicineType
            var doseToTake= medicinesToTakeTodayList[holder.adapterPosition].doseMedToTake
            var currentAmountOfMedicine= dbHelper.getUnitInStock(idMedicineType)
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.alertDialogTitleTakeMedicine))
            builder.setMessage(context.getString(R.string.alertDialogMessageTakeMedicine))
            builder.setPositiveButton(context.getString(R.string.AlertDialogYes)) { dialog: DialogInterface, which: Int ->
                val context:Context = holder.view.context

                if(currentAmountOfMedicine<doseToTake){
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle(context.getString(R.string.alertDailogTitleNotEnoughMedicine))
                    builder.setMessage(context.getString(R.string.alertDialogMessageNotEnoughMedicine))
                    builder.setNeutralButton(context.getString(R.string.back)){_,_ ->

                    }
                    builder.show()

                }else{
                val success = dbHelper.takingTheTodayMedicine(idTakeMedToday)
                val successToReduceMedType =dbHelper.reduceAmountOfDrugMedicineTypeByTheTakenDose(idMedicineType,doseToTake)
                val successRemoveMed = dbHelper.removeReminderMedWasTaken(idTakeMedToday)
                if(success && successToReduceMedType)
                {
                    Toast.makeText(context,context.getString(R.string.ToastTakeMedicine), Toast.LENGTH_SHORT).show()
                    medicinesToTakeTodayList.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                }
                }
            }
            builder.setNegativeButton(context.getString(R.string.AlertDialogNo)) { dialogInterface: DialogInterface, i: Int -> }
            builder.show()
        }
    }
}

class MyViewHolderTakeMedToday(val view: View) : RecyclerView.ViewHolder(view) {
    var medName : TextView = view.findViewById(R.id.MedicineName_cardView)
    var medDose: TextView = view.findViewById(R.id.MedicineDose_cardView)
    var medAfterBeforeMeal: TextView = view.findViewById(R.id.MedicineMeal_cardView)
}