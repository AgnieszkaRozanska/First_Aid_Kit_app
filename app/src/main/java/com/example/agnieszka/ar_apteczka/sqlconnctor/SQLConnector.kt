package com.example.agnieszka.ar_apteczka.sqlconnctor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.MedicineType
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.TakeMedicineOccur

  const  val DATABASE_NAME = "FirstAidKit.db"

    const val MEDICINE_TABLE_NAME = "Medicine"
    const val NAME = "Name"
    const val MEDICINE_TYPE = "MedicineType"
    const val UNIT_IN_STOCK = "UnitInStock"
    const val DESCRIPTION = "Description"
    const val ID_MEDICINE = "IDMedicine"
    const val ACTIVEDOSE = "ActiveDose"

    const val MEDICINE_ONCE_TABLE_NAME = "MedicineOnce"
    const val ID_MEDICINEONCE = "IDMedicine"
    const val ID = "ID"
    const val DOSE = "Dose"
    const val TIME_OF_DAY = "TimeOfDay"
    const val BEFORE_AFTER_MEAL = "BeforeAfterMeal"
    const val DAY = "Day"
    const val HOUR_REMINDERS = "HourReminders"
    const val DESCRIPTION_REMINDER = "DescriptionReminder"


    const val SQL_CREATE_TABLE_MEDICINE = ("CREATE TABLE IF NOT EXISTS "  + MEDICINE_TABLE_NAME + " (" +
            ID_MEDICINE + " TEXT PRIMARY KEY," +
            NAME + " TEXT NOT NULL,"+
            MEDICINE_TYPE + " TEXT NOT NULL," +
            UNIT_IN_STOCK + " INTEGER NOT NULL," +
            DESCRIPTION + " TEXT," +
            ACTIVEDOSE + " TEXT);")


const val SQL_CREATE_TABLE_MEDICINE_ONE = ("CREATE TABLE IF NOT EXISTS "  + MEDICINE_ONCE_TABLE_NAME + " (" +
        ID_MEDICINEONCE + " TEXT PRIMARY KEY," +
        ID + " TEXT NOT NULL," +
        DOSE + " INTEGER NOT NULL," +
        TIME_OF_DAY + " TEXT NOT NULL," +
        BEFORE_AFTER_MEAL +" TEXT NOT NULL," +
        DAY + " TEXT," +
        HOUR_REMINDERS + " TEXT," +
        DESCRIPTION_REMINDER + " TEXT);")

const val SQL_DELETE_TABLE_MEDICINE = "DROP TABLE IF EXISTS $MEDICINE_TABLE_NAME"

const val SQL_DELETE_TABLE_MEDICINE_ONCE = "DROP TABLE IF EXISTS $MEDICINE_ONCE_TABLE_NAME"

class SQLConector(context: Context):SQLiteOpenHelper(context,
    DATABASE_NAME, null, 1)
{
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MEDICINE)
        db.execSQL(SQL_CREATE_TABLE_MEDICINE_ONE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE_MEDICINE)
        db.execSQL(SQL_DELETE_TABLE_MEDICINE_ONCE)
        onCreate(db)
    }


    fun addMedicine(medicine: MedicineType ):Boolean
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(ID_MEDICINE, medicine.iDMedicine)
        cv.put(NAME, medicine.name)
        cv.put(MEDICINE_TYPE, medicine.kindMedicineType)
        cv.put(UNIT_IN_STOCK, medicine.unitInStock)
        cv.put(DESCRIPTION, medicine.description)
        cv.put(ACTIVEDOSE, medicine.activedoses)

        val result= db.insert(MEDICINE_TABLE_NAME, null, cv)
        db.close()
        return !result.equals(-1)
    }

    fun getAllMedicineTypes(): ArrayList<MedicineType>
    {
        val medicineAllList= ArrayList<MedicineType>()
        val db= readableDatabase

        val cursor=db.rawQuery("SELECT * FROM $MEDICINE_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do{
                    val id= cursor.getString(cursor.getColumnIndex(ID_MEDICINE))
                    val name=cursor.getString(cursor.getColumnIndex(NAME))
                    val medicineType=cursor.getString(cursor.getColumnIndex(MEDICINE_TYPE))
                    val unitInStock=cursor.getString(cursor.getColumnIndex(UNIT_IN_STOCK))
                    val description=cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                    val activeDose = cursor.getString(cursor.getColumnIndex(ACTIVEDOSE))

                    val med= MedicineType(id,name,medicineType,description,unitInStock.toInt(),activeDose)
                medicineAllList.add(med)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return medicineAllList
    }

    fun getMedListOfName(): ArrayList<String>
    {
        val medicineNameAllList= ArrayList<String>()
        val db= readableDatabase

        val cursor=db.rawQuery("SELECT * FROM $MEDICINE_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do{
                    val name=cursor.getString(cursor.getColumnIndex(NAME))
                    val activeDose = cursor.getString(cursor.getColumnIndex(ACTIVEDOSE))
                    val fullName= "$name $activeDose"
                    medicineNameAllList.add(fullName)
                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        db.close()
        return medicineNameAllList
    }

    fun updateMedicineTypeDoses(id:String, unitInStock: Int):Boolean
    {
        try {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(UNIT_IN_STOCK, unitInStock)

            db.update(MEDICINE_TABLE_NAME, cv, "IDMedicine =?", arrayOf(id))
            db.close()
        }
        catch (e: Exception) {
           e.printStackTrace()
           return false
        }

        return true
    }

    fun removeMedicineType(id: String): Boolean
    {
        try {
            val db=this.writableDatabase
            db.delete(MEDICINE_TABLE_NAME, "$ID_MEDICINE=?", arrayOf(id))
            db.close()
        }
        catch (e: Exception) {
             e.printStackTrace()
             return false
        }
        return true
    }

    fun addTakeMedicineOccur(takeMedOccur: TakeMedicineOccur):Boolean
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(ID_MEDICINEONCE, takeMedOccur.iD)
        cv.put(ID, takeMedOccur.medicineType)
        cv.put(DOSE, takeMedOccur.dose)
        cv.put(TIME_OF_DAY, takeMedOccur.timeOfDay)
        cv.put(BEFORE_AFTER_MEAL, takeMedOccur.beforeAfterMeal)
        cv.put(DAY, takeMedOccur.day)
        cv.put(HOUR_REMINDERS, takeMedOccur.hourReminders)
        cv.put(DESCRIPTION_REMINDER,takeMedOccur.descriptionReminder )


        val result= db.insert(MEDICINE_ONCE_TABLE_NAME, null, cv)
        db.close()
        return !result.equals(-1)
    }


    fun getAllTakeMedicineOccur(time : String):ArrayList<TakeMedicineOccur>{

        val takeMedicineOccurAllList= ArrayList<TakeMedicineOccur>()
        val db= readableDatabase

        val cursor=db.rawQuery("SELECT * FROM $MEDICINE_ONCE_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do {
                    val idtakeMedOccur = cursor.getString(cursor.getColumnIndex(ID_MEDICINEONCE))
                    val idmed = cursor.getString(cursor.getColumnIndex(ID))
                    val dose = cursor.getString(cursor.getColumnIndex(DOSE))
                    val timeOfDay = cursor.getString(cursor.getColumnIndex(TIME_OF_DAY))
                    val beforeAfterMeal = cursor.getString(cursor.getColumnIndex(BEFORE_AFTER_MEAL))
                    val day = cursor.getString(cursor.getColumnIndex(DAY))
                    val hourReminder = cursor.getString(cursor.getColumnIndex(HOUR_REMINDERS))
                    val description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_REMINDER))

                    if(timeOfDay==time)
                    {
                        val takMedOccur = TakeMedicineOccur(idtakeMedOccur,idmed,dose.toInt(),timeOfDay,beforeAfterMeal,day,hourReminder, description)
                        takeMedicineOccurAllList.add(takMedOccur)
                    }
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return takeMedicineOccurAllList

    }

    fun removeTakeMedicineOccur(id: String): Boolean
    {
        try {
            val db=this.writableDatabase
            db.delete(MEDICINE_ONCE_TABLE_NAME, "$ID_MEDICINE=?", arrayOf(id))
            db.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun updateTakeMedicineOccurDoses(id:String, unitInStock: Int):Boolean
    {
        try {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(DOSE, unitInStock)
            db.update(MEDICINE_ONCE_TABLE_NAME, cv, "IDMedicine =?", arrayOf(id))
            db.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }
}