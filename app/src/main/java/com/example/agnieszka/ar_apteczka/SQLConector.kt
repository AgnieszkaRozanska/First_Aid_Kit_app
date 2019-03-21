package com.example.agnieszka.ar_apteczka

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

//-----------------------Opis tabeli----------------

    val DATABASE_NAME = "FirstAidKit.db"
    val TABLE_NAME = "Medicine"
    val COL_1_1 = "Name"
    val COL_2_1 = "MedicineType"
    val COL_3_1 = "UnitInStock"
    val COL_4_1 = "Description"
    val COL_5_1 = "IDMedicine"

    val TABLE_NAME2 = "MedicineOnce"
    val COL_1_2 = "Day"
    val COL_2_2 = "TimeOfDay" //pora dnia
    val COL_3_2 = "Dose"
    val COL_4_2 = "BeforeAfterMeal"
    val COL_5_2 = "HourReminders"
    val COL_6_2 = "DescriptionReminder"
    val COL_7_2 = "IDMedicine"
    val COL_8_2 = "ID"


//Klasa ta będzie służyła do tworznia bazy danych i niezbędnych tabel
class SQLConector(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, 1)
{


    //-----------------------Podstawowe komendy SQL-------

    //metoda tworzy nam naszą baze danych z tabelą Medicine i MedicineOnce
    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ( Name TEXT NOT NULL, MedicineType INTEGER NOT NULL,UnitInStock INTEGER NOT NULL, description TEXT, iDMedicine INTEGER PRIMARY KEY AUTOINCREMENT )")
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME2 (Day TEXT NOT NULL, TimeOfDay INTEGER NOT NULL, Dose INTEGER NOT NULL, BeforeAfterMeal INTEGER NOT NULL, HourReminders TEXT, DescriptionReminder TEXT, IDMedicine INTEGER, ID INTEGER PRIMARY KEY AUTOINCREMENT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME ")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2 ")
        onCreate(db)
    }

    //-------------------------------------------------------

    fun addMedicine(name: String, medicineType: String, unitInStock: Int, description: String ):Boolean?
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_1_1, name)
        cv.put(COL_2_1, medicineType)
        cv.put(COL_3_1, unitInStock)
        cv.put(COL_4_1, description)

        val result= db.insert(TABLE_NAME, null, cv)

        return !result.equals(-1)
    }

    fun getAllMedicineTypes():Cursor{
        val db=this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getMedicineOnce(id:String):Cursor
    {
        val db=this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE ID=?", arrayOf(id), null)

    }

    fun updateMedicineTypeDoses(id:String, unitInStock: Int):Boolean
    {
        val db=this.writableDatabase
        val cv= ContentValues()
        cv.put(COL_3_1, unitInStock)

        db.update(TABLE_NAME, cv, "ID=?", arrayOf(id))
        return  true
    }

    fun removeMedicineType(id: String):Int?
    {
        val db=this.writableDatabase
        return db.delete(TABLE_NAME, "ID=?", arrayOf(id))
    }



    fun addTakeMedicineOccour(day: String, timeOfDay: String, dose: Int, beforeAfterMeal: String, hourReminders : String, iDMedicine: String, iD: String ):Boolean?
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_1_2, day)
        cv.put(COL_2_2, timeOfDay)
        cv.put(COL_3_2, dose)
        cv.put(COL_4_2, beforeAfterMeal)
        cv.put(COL_5_2, hourReminders)
        cv.put(COL_6_2, iDMedicine)  //?
        cv.put(COL_7_2, iD)          //?

        val result= db.insert(TABLE_NAME2, null, cv)

        return !result.equals(-1)
    }

    fun getAllTakeMedicineOccurs():Cursor{
        val db=this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME2", null)
    }

    fun getTakeMedicineOccursOnce(id:String):Cursor
    {
        val db=this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE ID=?", arrayOf(id), null)

    }

    fun removeTakeMedicineOccour(id: String):Int?
    {
        val db=this.writableDatabase
        return db.delete(TABLE_NAME2, "ID=?", arrayOf(id))
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