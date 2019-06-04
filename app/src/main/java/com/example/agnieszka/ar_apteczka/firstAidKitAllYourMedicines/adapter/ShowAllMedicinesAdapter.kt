package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.updateRemoveMedicine.ActivityUpdateRemoveMedicine
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.MedicineType
import kotlinx.android.synthetic.main.activity_card_view__all__medicines.view.*

class card_view_All_Medicines(context: Context, var medicineTypeList: ArrayList<MedicineType>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val cardViewMedicine = layoutInflater.inflate(R.layout.activity_card_view__all__medicines, viewGroup, false)
        return MyViewHolder(cardViewMedicine)

    }

    override fun getItemCount(): Int {
        return medicineTypeList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val medNameEdit=medicineTypeList[holder.adapterPosition].name
        val activeDose = medicineTypeList[holder.adapterPosition].activedoses
        val fullname= "$medNameEdit $activeDose"
        holder.medName.text = fullname
        holder.medKind.text = medicineTypeList.elementAt(position).kindMedicineType


        val cardViewmedicine = holder.view.Medicine_cardView
           val context:Context = holder.view.context

        cardViewmedicine.setOnClickListener {

            val intentEdit = Intent(context, ActivityUpdateRemoveMedicine::class.java)
            val medNameEdit=medicineTypeList[holder.adapterPosition].name
            val activeDose = medicineTypeList[holder.adapterPosition].activedoses
            val medCountEdit= medicineTypeList[holder.adapterPosition].unitInStock.toString()
            val medKindEdit= medicineTypeList[holder.adapterPosition].kindMedicineType
            val medDescriptionEdit= medicineTypeList[holder.adapterPosition].description
            val idEdit= medicineTypeList[holder.adapterPosition].iDMedicine
            val fullname= "$medNameEdit $activeDose"

            intentEdit.putExtra("name", fullname)
            intentEdit.putExtra("count", medCountEdit)
            intentEdit.putExtra("IDMedicine", idEdit)
            intentEdit.putExtra("kind", medKindEdit)
            intentEdit.putExtra("description", medDescriptionEdit)
            context.startActivity(intentEdit)
        }
    }
}

class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var medName : TextView= view.findViewById(R.id.MedicineName_cardView)
    var medKind : TextView = view.findViewById(R.id.Kind_cardView)
}