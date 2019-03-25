package com.example.agnieszka.ar_apteczka

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_card_view__all__medicines.view.*

// to jest mój Adapter tak naprawde, tylko nazywa sie tak
class card_view_All_Medicines(context: Context, val db: SQLiteDatabase, var medicineType: ArrayList<MedicineType>): RecyclerView.Adapter<MyViewHolder>()
{
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val cardView_Medicine = layoutInflater.inflate(R.layout.activity_card_view__all__medicines, viewGroup, false)
        return MyViewHolder(cardView_Medicine)

    }

    override fun getItemCount(): Int {
        val cursor= db.query(
            MEDICINE_TABLE_NAME, null,
            null, null,
            null, null, null)

        val countofrows= cursor.count

        cursor.close()
        return countofrows
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

// ----------- Elementy pojedynczego leku--------------

        val cardView_note = holder.view.Medicine_cardView
        val medName= holder.view.MedicineName_cardView
        val medKind= holder.view.Kind_cardView
        val medCount= holder.view.Count_cardView
        val medDescription= holder.view.Description_cardView
        val context:Context = holder.view.context


        //------------------------------------------------------


        medName.setText(medicineType[holder.adapterPosition].name)
        medKind.setText(medicineType[holder.adapterPosition].kindMedicineType)
        medCount.setText(medicineType[holder.adapterPosition].unitInStock)
        medDescription.setText(medicineType[holder.adapterPosition].description)

        //----------------- Wczytanie treści -----------------------------------
        val cursor= db.query(MEDICINE_TABLE_NAME, null,
            ID_MEDICINE + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
        null, null, null)

        if(cursor.moveToNext())
        {

            medName.setText(cursor.getString(1))
            medKind.setText(cursor.getString(2))
            medCount.setText(cursor.getString(3))
            medDescription.setText(cursor.getString(4))

        }
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

}

// ---------------- Edycja Leku po kliknięciu na Lek ---------------
//-----------------    Update ilości sztuk leków     -----------------


// ---------------- Gdy przytrzymamy Lek to usuwamy go  ---------------


class MyViewHolder(val view: View):RecyclerView.ViewHolder(view)