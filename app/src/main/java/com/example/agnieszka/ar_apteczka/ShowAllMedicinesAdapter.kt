package com.example.agnieszka.ar_apteczka

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// to jest mój Adapter tak naprawde, tylko nazywa sie tak
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
    }
// ----------- Elementy pojedynczego leku--------------




        //------------------------------------------------------


        //----------------- Wczytanie treści -----------------------------------
//        val cursor= db.query(MEDICINE_TABLE_NAME, null,
//            ID_MEDICINE + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
//        null, null, null)
//
//        if(cursor.moveToNext())
//        {
//
//            medName.setText(cursor.getString(1))
//            medKind.setText(cursor.getString(2))
//            medCount.setText(cursor.getString(3))
//            medDescription.setText(cursor.getString(4))
//
//        }
        // ---------------- Edycja leku po kliknięciu w nią ---------------
    /*    cardView_note.setOnClickListener{
            val intent_edit = Intent(context, Details_activity::class.java)

            val title_edit=notes[holder.adapterPosition].tile
            val message_edit= notes[holder.adapterPosition].message

            val id_edit= notes[holder.adapterPosition].id.toString()

            intent_edit.putExtra("title", title_edit)
            intent_edit.putExtra("message", message_edit)
            intent_edit.putExtra("ID", id_edit)

            context.startActivity(intent_edit)
        }
*/
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


class MyViewHolder : RecyclerView.ViewHolder {
    var medName : TextView
    var medKind : TextView
    var medCount : TextView
    var medDescription : TextView

    constructor(view : View) : super(view) {
        //cardView_note = view.findViewById(R.id.car)
        medName = view.findViewById(R.id.MedicineName_cardView)
        medKind= view.findViewById(R.id.Kind_cardView)
        medCount= view.findViewById(R.id.Count_cardView)
        medDescription= view.findViewById(R.id.Description_cardView)
    }
}