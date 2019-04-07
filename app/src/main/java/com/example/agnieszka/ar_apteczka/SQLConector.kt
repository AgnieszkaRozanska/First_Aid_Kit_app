package com.example.agnieszka.ar_apteczka

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.MedicineType

//-----------------------Opis tabeli----------------

    //stałe opisjace nasza tabele, nazwy i kolumny
  const  val DATABASE_NAME = "FirstAidKit.db"

    const val MEDICINE_TABLE_NAME = "Medicine"
    const val NAME = "Name"
    const val MEDICINE_TYPE = "MedicineType"
    const val UNIT_IN_STOCK = "UnitInStock"
    const val DESCRIPTION = "Description"
    const val ID_MEDICINE = "IDMedicine"

    const val MEDICINE_ONCE_TABLE_NAME = "MedicineOnce"
    const val DAY = "Day"
    const val TIME_OF_DAY = "TimeOfDay"
    const val DOSE = "Dose"
    const val BEFORE_AFTER_MEAL = "BeforeAfterMeal"
    const val HOUR_REMINDERS = "HourReminders"
    const val DESCRIPTION_REMINDER = "DescriptionReminder"
    const val ID_MEDICINEONCE = "IDMedicine"
    const val ID = "ID"


//-----------------------Podstawowe komendy SQL-------

    const val SQL_CREATE_TABLE_MEDICINE = ("CREATE TABLE IF NOT EXISTS "  + MEDICINE_TABLE_NAME + " (" +
                 ID_MEDICINE + " TEXT PRIMARY KEY," +
             NAME + " TEXT NOT NULL,"+
             MEDICINE_TYPE + " TEXT NOT NULL," +
             UNIT_IN_STOCK + " INTEGER NOT NULL," +
             DESCRIPTION + " TEXT);")


const val SQL_CREATE_TABLE_MEDICINE_ONE = ("CREATE TABLE IF NOT EXISTS "  + MEDICINE_ONCE_TABLE_NAME + " (" +
        ID_MEDICINEONCE + " INTEGER PRIMARY KEY," +
        ID + " INTEGER NOT NULL,"+
        DAY + " TEXT NOT NULL," +
        UNIT_IN_STOCK + " INTEGER NOT NULL," +
        TIME_OF_DAY + " TEXT NOT NULL,"+
        DOSE +" INTEGER NOT NULL,"+
        BEFORE_AFTER_MEAL +" TEXT NOT NULL,"+
        HOUR_REMINDERS +" TEXT,"+
        DESCRIPTION_REMINDER +" TEXT);")

const val SQL_DELETE_TABLE_MEDICINE = "DROP TABLE IF EXISTS $MEDICINE_TABLE_NAME"

const val SQL_DELETE_TABLE_MEDICINE_ONCE = "DROP TABLE IF EXISTS $MEDICINE_ONCE_TABLE_NAME"




//Klasa ta będzie służyła do tworznia bazy danych i niezbędnych tabel
class SQLConector(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, 1)
{


    //-----------------------Podstawowe komendy SQL-------

    //metoda tworzy nam naszą baze danych z tabelą Medicine i MedicineOnce
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MEDICINE)
        db.execSQL(SQL_CREATE_TABLE_MEDICINE_ONE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE_MEDICINE)
        db.execSQL(SQL_DELETE_TABLE_MEDICINE_ONCE)
        onCreate(db)
    }

    //-------------------------------------------------------

    fun addMedicine(id:String, name: String, medicineType: String, unitInStock: Int, description: String ):Boolean
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(ID_MEDICINE, id)
        cv.put(NAME, name)
        cv.put(MEDICINE_TYPE, medicineType)
        cv.put(UNIT_IN_STOCK, unitInStock)
        cv.put(DESCRIPTION, description)

        val result= db.insert(MEDICINE_TABLE_NAME, null, cv)
        db.close()
        return !result.equals(-1)
    }

    fun getAllMedicineTypes(): ArrayList<MedicineType>
    {
        val medicine_All_List= ArrayList<MedicineType>()
        val db= readableDatabase

        val cursor=db.rawQuery("SELECT * FROM $MEDICINE_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do{
                    var id= cursor.getString(cursor.getColumnIndex(ID_MEDICINE))
                    var name=cursor.getString(cursor.getColumnIndex(NAME))
                    var medicineType=cursor.getString(cursor.getColumnIndex(MEDICINE_TYPE))
                    var unitInStock=cursor.getString(cursor.getColumnIndex(UNIT_IN_STOCK))
                    var description=cursor.getString(cursor.getColumnIndex(DESCRIPTION))


                val med= MedicineType(
                    id,
                    name,
                    medicineType,
                    description,
                    unitInStock.toInt()

                )
                medicine_All_List.add(med)
                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        db.close()
        return medicine_All_List
    }

    fun getMedicineOnce(id:String):Cursor
    {
        val db=this.writableDatabase

        return db.rawQuery("SELECT * FROM $MEDICINE_TABLE_NAME WHERE ID=?", arrayOf(id), null)

    }

    fun updateMedicineTypeDoses(id:String, unitInStock: Int):Boolean
    {
        try {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(UNIT_IN_STOCK, unitInStock)

            db.update(MEDICINE_TABLE_NAME, cv, "IDMedicine =?", arrayOf(id))
            db.close()
            return true
        }
        catch (e: Exception) {
           e.printStackTrace()
           return false
        }
    }

    fun removeMedicineType(id: String):Int?
    {
        val db=this.writableDatabase
        return db.delete(MEDICINE_TABLE_NAME, "ID=?", arrayOf(id))
    }



    fun addTakeMedicineOccour(day: String, timeOfDay: String, dose: Int, beforeAfterMeal: String, hourReminders : String, iDMedicine: String, iD: String ):Boolean?
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(DAY, day)
        cv.put(TIME_OF_DAY, timeOfDay)
        cv.put(DOSE, dose)
        cv.put(BEFORE_AFTER_MEAL, beforeAfterMeal)
        cv.put(HOUR_REMINDERS, hourReminders)
        cv.put(ID_MEDICINEONCE, iDMedicine)  //?
        cv.put(ID, iD)          //?

        val result= db.insert(MEDICINE_ONCE_TABLE_NAME, null, cv)

        return !result.equals(-1)
    }

    fun getAllTakeMedicineOccurs():Cursor{
        val db=this.writableDatabase
        return db.rawQuery("SELECT * FROM $MEDICINE_ONCE_TABLE_NAME", null)
    }

    fun getTakeMedicineOccursOnce(id:String):Cursor
    {
        val db=this.writableDatabase
        return db.rawQuery("SELECT * FROM $MEDICINE_TABLE_NAME WHERE ID=?", arrayOf(id), null)

    }

    fun removeTakeMedicineOccour(id: String):Int?
    {
        val db=this.writableDatabase
        return db.delete(MEDICINE_ONCE_TABLE_NAME, "ID=?", arrayOf(id))
    }




    /*
    GetAllMedicineTypes           ok
    AddMedicineType              ok
    UpdateMedicineTypeDoses      ok
    RemoveMedicineType            ok
    GetAllTakeMedicineOccurs  -------
    AddTakeMedicineOccour        ---
    RemoveTakeMedicineOccour    ------


     */

}