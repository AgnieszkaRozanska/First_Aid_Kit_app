package com.example.agnieszka.ar_apteczka

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.agnieszka.ar_apteczka.R.id.Medicine_cardView

class card_view_All_Medicines(context: Context, var medicineTypeList: ArrayList<MedicineType>): RecyclerView.Adapter<MyViewHolder>()
{
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
        holder.medCount.setText(medicineTypeList.elementAt(position).unitInStock.toString())
        holder.medDescription.setText(medicineTypeList.elementAt(position).description)


        // ---------------- Edycja leku po kliknięciu w nią ---------------
        //val cardView_medicine = holder.view.Medicine_cardView

        //cardView_medicine.setOnClickListener {
            //val intent_edit = Intent(context, Add_Medicine_FirstAidKit::class.java)
        //}
            /*      val Med_Name_edit=medicineTypeList[holder.adapterPosition].name
         val Med_Kind_edit= medicineTypeList[holder.adapterPosition].kindMedicineType
         val Med_Count_edit= medicineTypeList[holder.adapterPosition].unitInStock.toString()
         val Med_Description_edit= medicineTypeList[holder.adapterPosition].description

         val id_edit= notes[holder.adapterPosition].id.toString()

         intent_edit.putExtra("name", Med_Name_edit)
         intent_edit.putExtra("kind", Med_Kind_edit)
         intent_edit.putExtra("count", Med_Count_edit)
         intent_edit.putExtra("description", Med_Description_edit)

         //intent_edit.putExtra("ID", id_edit)

         context.startActivity(intent_edit)
         */
      //  }
    }
        /*
        // ---------------- Gdy przytrzymamy lek to ja usuwamy ---------------------------
        cardView_note.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                db.delete(TableInfo.TABLE_NAME, BaseColumns._ID + "=?",
                    arrayOf(notes[holder.adapterPosition].id.toString()))

                // narzedzie do zarzadzania wyswetlanymi elementami
                notes.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)


                // ---------------- Gdy przytrzymamy notake skopiujemy tyuł i treść ---------------------------
             /* val copy_info= Toast.makeText(context, "Skopiowano", Toast.LENGTH_SHORT)
                val cm= context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                val clipdata = ClipData.newPlainText("CopyText",
                "Title: " + title.text + "\n" + "Message: " + message.text)

                cm.primaryClip = clipdata
                        copy_info.show()

            */

                return  true
            }

        })


 */

}

// ---------------- Edycja Leku po kliknięciu na Lek ---------------
//-----------------    Update ilości sztuk leków     -----------------


// ---------------- Gdy przytrzymamy Lek to usuwamy go  ---------------


class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var medName : TextView= view.findViewById(R.id.MedicineName_cardView)
    var medKind : TextView = view.findViewById(R.id.Kind_cardView)
    var medCount : TextView= view.findViewById(R.id.Count_cardView)
    var medDescription : TextView= view.findViewById(R.id.Description_cardView)


}