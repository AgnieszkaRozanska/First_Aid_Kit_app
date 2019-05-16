package com.example.agnieszka.ar_apteczka

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.FirstAidKitMenu
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.MedicineType
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.UpdateCountofMedicines
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.UpdateRemoveMedicine
import kotlinx.android.synthetic.main.activity_card_view__all__medicines.view.*

class card_view_All_Medicines(context: Context, var medicineTypeList: ArrayList<MedicineType>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val cardView_Medicine = layoutInflater.inflate(R.layout.activity_card_view__all__medicines, viewGroup, false)
        return MyViewHolder(cardView_Medicine)

    }

    override fun getItemCount(): Int {
        return medicineTypeList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.medName.setText(medicineTypeList.elementAt(position).name)
        holder.medKind.setText(medicineTypeList.elementAt(position).kindMedicineType)
        //holder.medCount.setText(medicineTypeList.elementAt(position).unitInStock.toString())
       // holder.medDescription.setText(medicineTypeList.elementAt(position).description)


        // ---------------- Edycja leku po kliknięciu w nią ---------------
        val cardView_medicine = holder.view.Medicine_cardView
           val context:Context = holder.view.context

        cardView_medicine.setOnClickListener {
            //val dbHelper = SQLConector(context)

            val intent_edit = Intent(context, UpdateRemoveMedicine::class.java)
            val Med_Name_edit=medicineTypeList[holder.adapterPosition].name
            val Med_Count_edit= medicineTypeList[holder.adapterPosition].unitInStock.toString()
            val Med_Kind_edit= medicineTypeList[holder.adapterPosition].kindMedicineType
            val Med_Description_edit= medicineTypeList[holder.adapterPosition].description
            val id_edit= medicineTypeList[holder.adapterPosition].iDMedicine

            intent_edit.putExtra("name", Med_Name_edit)
            intent_edit.putExtra("count", Med_Count_edit)
            intent_edit.putExtra("IDMedicine", id_edit)
            intent_edit.putExtra("kind", Med_Kind_edit)
            intent_edit.putExtra("description", Med_Description_edit)
            context.startActivity(intent_edit)
        }


    }



}

class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var medName : TextView= view.findViewById(R.id.MedicineName_cardView)
    var medKind : TextView = view.findViewById(R.id.Kind_cardView)
    //var medCount : TextView= view.findViewById(R.id.Count_cardView)
    //var medDescription : TextView= view.findViewById(R.id.Description_cardView)


}