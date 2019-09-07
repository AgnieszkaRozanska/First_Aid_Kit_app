package com.example.agnieszka.ar_apteczka.sqlconnctor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.agnieszka.ar_apteczka.todaysMedicines.objectMedicinesToTake.MedicineToTake
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.MedicineType
import com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.notlification.NotificationAmountMed
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.TakeMedicineOccur
import com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder.Reminder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    const val MEDICIETYPE_NAME = "MEDICIETYPE_NAME"
    const val ID_MEDICINETYPE = "IDMedicieType"
    const val DOSE = "Dose"
    const val TIME_OF_DAY = "TimeOfDay"
    const val BEFORE_AFTER_MEAL = "BeforeAfterMeal"
    const val DATE_START = "DateStart"
    const val DATE_END = "DateEnd"

    const val NOTIFICATION_MED_COUNT_TABLE_NAME = "NotificationMedCount"
    const val ID_NOTIFICATION = "IDNotification"
    const val ID_MEDICINE_NOTIFICATION = "IDMedicineNotification"
    const val AMOUNT_MED_BELOW_TO_ALARM = "AmountMedBelowToAlarm"

    const val MEDICINES_TO_TAKE_TABLE_NAME = "MedicinesToTake"
    const val ID_MEDICINES_TO_TAKE = "IdMedicinesToTake"
    const val ID_TAKE_MED_OCCUR = "IdTakeMedOccur"
    const val ID_MEDICINE_TYPE = "IdMedicinesType"
    const val NAME_MED_TO_TAKE = "MedicinesToTakeName"
    const val DOSE_MED_TO_TAKE  = "DoseMTT"
    const val TIME_OF_DAY_MED_TO_TAKE = "TimeOfDayMTT"
    const val WHEN_MEAL_MED_TO_TAKE  = "AfterBeforeMealMTT"
    const val DATE_MED_TO_TAKE  = "DateMedToTake"
    const val IF_MED_WAS_TAKEN = "IfMedWasTaken"

    const val REMINDER_TABLE_NAME = "Reminder"
    const val REMINDER_ID= "ReminderID"
    const val MEDICINE_TO_TAKE_ID = "MedicineToTakeID"
    const val MEDICINE_OCCUR_ID = "MedicieOccurID"
    const val MEDICINE_ID = "MedicieID"
    const val MEDICINE_NAME = "MedicieName"
    const val REMINDER_DATE = "ReminderDate"
    const val REMINDER_TIME = "ReminderTime"


    const val SQL_CREATE_TABLE_MEDICINE = ("CREATE TABLE IF NOT EXISTS "  + MEDICINE_TABLE_NAME + " (" +
            ID_MEDICINE + " TEXT PRIMARY KEY," +
            NAME + " TEXT NOT NULL,"+
            MEDICINE_TYPE + " TEXT NOT NULL," +
            UNIT_IN_STOCK + " INTEGER NOT NULL," +
            DESCRIPTION + " TEXT," +
            ACTIVEDOSE + " TEXT);")


const val SQL_CREATE_TABLE_MEDICINE_ONE = ("CREATE TABLE IF NOT EXISTS "  + MEDICINE_ONCE_TABLE_NAME + " (" +
        ID_MEDICINEONCE + " TEXT PRIMARY KEY," +
        ID_MEDICINETYPE + " TEXT NOT NULL," +
        MEDICIETYPE_NAME + " TEXT NOT NULL," +
        DOSE + " INTEGER NOT NULL," +
        TIME_OF_DAY + " TEXT NOT NULL," +
        BEFORE_AFTER_MEAL +" TEXT NOT NULL," +
        DATE_START +" TEXT NOT NULL," +
        DATE_END + " TEXT NOT NULL);")


const val SQL_CREATE_TABLE_MED_NOTIFICATION = ("CREATE TABLE IF NOT EXISTS "  + NOTIFICATION_MED_COUNT_TABLE_NAME + " (" +
        ID_NOTIFICATION + " TEXT PRIMARY KEY," +
        ID_MEDICINE_NOTIFICATION + " TEXT NOT NULL,"+
        AMOUNT_MED_BELOW_TO_ALARM + " INTEGER NOT NULL);")

 const val SQL_CREATE_TABLE_MEDICINES_TO_TAKE = ("CREATE TABLE IF NOT EXISTS "  + MEDICINES_TO_TAKE_TABLE_NAME + " (" +
        ID_MEDICINES_TO_TAKE + " TEXT PRIMARY KEY," +
        ID_TAKE_MED_OCCUR + " TEXT NOT NULL," +
        ID_MEDICINE_TYPE + " TEXT NOT NULL," +
        NAME_MED_TO_TAKE + " TEXT NOT NULL," +
        DOSE_MED_TO_TAKE + " INTEGER NOT NULL," +
        TIME_OF_DAY_MED_TO_TAKE + " TEXT NOT NULL," +
        WHEN_MEAL_MED_TO_TAKE +" TEXT NOT NULL," +
        DATE_MED_TO_TAKE +" TEXT NOT NULL," +
        IF_MED_WAS_TAKEN + " TEXT NOT NULL);")


const val SQL_CREATE_TABLE_REMINDER = ("CREATE TABLE IF NOT EXISTS "  + REMINDER_TABLE_NAME + " (" +
        REMINDER_ID + " TEXT PRIMARY KEY," +
        MEDICINE_TO_TAKE_ID + " TEXT NOT NULL," +
        MEDICINE_OCCUR_ID + " TEXT NOT NULL," +
        MEDICINE_ID + " TEXT NOT NULL," +
        MEDICINE_NAME + " TEXT NOT NULL," +
        REMINDER_DATE + " TEXT NOT NULL," +
        REMINDER_TIME + " TEXT NOT NULL);")


const val SQL_DELETE_TABLE_MEDICINE = "DROP TABLE IF EXISTS $MEDICINE_TABLE_NAME"
const val SQL_DELETE_TABLE_MEDICINE_ONCE = "DROP TABLE IF EXISTS $MEDICINE_ONCE_TABLE_NAME"
const val SQL_DELETE_TABLE_MEDICINE_NOTIFICATION = "DROP TABLE IF EXISTS $NOTIFICATION_MED_COUNT_TABLE_NAME"
const val SQL_DELETE_TABLE_MEDICINE_TO_TAKE = "DROP TABLE IF EXISTS $MEDICINES_TO_TAKE_TABLE_NAME"
const val SQL_DELETE_TABLE_REMINDER = "DROP TABLE IF EXISTS $REMINDER_TABLE_NAME"

class SQLConector(context: Context):SQLiteOpenHelper(context,
    DATABASE_NAME, null, 1)
{
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MEDICINE)
        db.execSQL(SQL_CREATE_TABLE_MEDICINE_ONE)
        db.execSQL(SQL_CREATE_TABLE_MED_NOTIFICATION)
        db.execSQL(SQL_CREATE_TABLE_MEDICINES_TO_TAKE)
        db.execSQL(SQL_CREATE_TABLE_REMINDER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE_MEDICINE)
        db.execSQL(SQL_DELETE_TABLE_MEDICINE_ONCE)
        db.execSQL(SQL_DELETE_TABLE_MEDICINE_NOTIFICATION)
        db.execSQL(SQL_DELETE_TABLE_MEDICINE_TO_TAKE)
        db.execSQL(SQL_DELETE_TABLE_REMINDER)
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
                    val kind = cursor.getString(cursor.getColumnIndex(MEDICINE_TYPE))
                    val fullName= "$name $activeDose $kind"
                    medicineNameAllList.add(fullName)
                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        db.close()
        return medicineNameAllList
    }

    fun getMedicieID(choosenMedicine : String): String
    {
        var idResult : String = ""
        var MedicieTEMP = ""
        var medicineTypeList: ArrayList<MedicineType> = getAllMedicineTypes()
        for (i:MedicineType in medicineTypeList) {
            MedicieTEMP=i.name + " " + i.activedoses + " " + i.kindMedicineType
            if(MedicieTEMP==choosenMedicine) idResult = i.iDMedicine
        }
        return idResult
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

    fun canRemoveMedicine(idMedicineType : String) : Boolean{
        var listOfAllTakeMedicies = getAllTakeMedicinesTodayAllDrugs()
        for (i: MedicineToTake in listOfAllTakeMedicies) {
            if(i.iD_MedicineType ==idMedicineType) {
               return false
            }
            }
        return true
    }

    fun checkIfDrugAlreadyExists(name : String, activeDose : String) : Boolean{
        var result = false
        var medicineTypeList: ArrayList<MedicineType> = getAllMedicineTypes()
        for (i: MedicineType in medicineTypeList) {
            if(i.name == name){
                if(i.activedoses ==activeDose){
                    result = true
                }
            }
        }

        return result
    }

    fun addTakeMedicineOccur(takeMedOccur: TakeMedicineOccur):Boolean
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(ID_MEDICINEONCE, takeMedOccur.iD)
        cv.put(ID_MEDICINETYPE, takeMedOccur.iD_MedicineType)
        cv.put(MEDICIETYPE_NAME, takeMedOccur.medicineType_Name)
        cv.put(DOSE, takeMedOccur.dose)
        cv.put(TIME_OF_DAY, takeMedOccur.timeOfDay)
        cv.put(BEFORE_AFTER_MEAL, takeMedOccur.beforeAfterMeal)
        cv.put(DATE_START, takeMedOccur.dateStart)
        cv.put(DATE_END, takeMedOccur.dateEnd)

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
                    val idMedicineType = cursor.getString(cursor.getColumnIndex(ID_MEDICINETYPE))
                    val idmed = cursor.getString(cursor.getColumnIndex(MEDICIETYPE_NAME))
                    val dose = cursor.getString(cursor.getColumnIndex(DOSE))
                    val timeOfDay = cursor.getString(cursor.getColumnIndex(TIME_OF_DAY))
                    val beforeAfterMeal = cursor.getString(cursor.getColumnIndex(BEFORE_AFTER_MEAL))
                    val dateStart = cursor.getString(cursor.getColumnIndex(DATE_START))
                    val dateEnd = cursor.getString(cursor.getColumnIndex(DATE_END))

                    val current = LocalDateTime.now()
                    val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    var dateTodayString =current.format(formatDate)

                   var dateStartFormatDate = LocalDate.parse(dateStart, formatDate)
                   var dateEndFormatDate = LocalDate.parse(dateEnd, formatDate)
                   var dateToday = LocalDate.parse(dateTodayString, formatDate)
                   val compareDateStartWithCurretDate = dateStartFormatDate.compareTo(dateToday)
                   val compareDateEndWithCurrentDate = dateEndFormatDate.compareTo(dateToday)
                    if(timeOfDay==time &&  (compareDateEndWithCurrentDate>0 || compareDateEndWithCurrentDate==0))
                    {
                        //if(timeOfDay==time)
                        //{
                        val takMedOccur = TakeMedicineOccur(idtakeMedOccur,idMedicineType,idmed,dose.toInt(),timeOfDay,beforeAfterMeal,dateStart, dateEnd)
                        takeMedicineOccurAllList.add(takMedOccur)
                    }
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return takeMedicineOccurAllList
    }

    fun getAllTakeMedOccur():ArrayList<TakeMedicineOccur>{
        val takeMedicineOccurAllList= ArrayList<TakeMedicineOccur>()
        val db= readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $MEDICINE_ONCE_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do {
                    val idtakeMedOccur = cursor.getString(cursor.getColumnIndex(ID_MEDICINEONCE))
                    val idMedicineType = cursor.getString(cursor.getColumnIndex(ID_MEDICINETYPE))
                    val idmed = cursor.getString(cursor.getColumnIndex(MEDICIETYPE_NAME))
                    val dose = cursor.getString(cursor.getColumnIndex(DOSE))
                    val timeOfDay = cursor.getString(cursor.getColumnIndex(TIME_OF_DAY))
                    val beforeAfterMeal = cursor.getString(cursor.getColumnIndex(BEFORE_AFTER_MEAL))
                    val dateStart = cursor.getString(cursor.getColumnIndex(DATE_START))
                    val dateEnd = cursor.getString(cursor.getColumnIndex(DATE_END))

                    val current = LocalDateTime.now()
                    val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    var dateTodayString =current.format(formatDate)

                    var dateStartFormatDate = LocalDate.parse(dateStart, formatDate)
                    var dateEndFormatDate = LocalDate.parse(dateEnd, formatDate)
                    var dateToday = LocalDate.parse(dateTodayString, formatDate)
                    val compareDateEndWithCurrentDate = dateEndFormatDate.compareTo(dateToday)
                    if( compareDateEndWithCurrentDate>=0)
                    {
                        val takMedOccur = TakeMedicineOccur(idtakeMedOccur,idMedicineType,idmed,dose.toInt(),timeOfDay,beforeAfterMeal,dateStart, dateEnd)
                        takeMedicineOccurAllList.add(takMedOccur)
                    }
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return takeMedicineOccurAllList
    }

    fun getTakeMedicieOccur(idTakeMedOccur : String, timeOfDayParm : String, afterBeforeMealParm : String) : TakeMedicineOccur{
        var allTakeMedOccurList: ArrayList<TakeMedicineOccur> = getAllTakeMedicineOccur(timeOfDayParm)
        var idtakeMedOccur = ""
        var idMedicineType = ""
        var idmedName = ""
        var dose = 0
        var timeOfDay = ""
        var beforeAfterMeal = ""
        var dateStart = ""
        var dateEnd = ""
        for (i:TakeMedicineOccur in allTakeMedOccurList){
            if(i.iD == idTakeMedOccur && i.timeOfDay == timeOfDayParm && i.beforeAfterMeal == afterBeforeMealParm)
            idtakeMedOccur = i.iD
            idMedicineType = i.iD_MedicineType
            idmedName = i.medicineType_Name
            dose = i.dose
             timeOfDay = i.timeOfDay
            beforeAfterMeal = i.beforeAfterMeal
             dateStart = i.dateStart
             dateEnd = i.dateEnd

        }
        var takeMedOccur =  TakeMedicineOccur(idtakeMedOccur, idMedicineType, idmedName, dose, timeOfDay, beforeAfterMeal, dateStart, dateEnd)
            return  takeMedOccur
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

    fun updateTakeMedicineOccurDateEnd(id:String, dateEnd: String):Boolean
    {
        try {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(DATE_END, dateEnd)
            db.update(MEDICINE_ONCE_TABLE_NAME, cv, "IDMedicine =?", arrayOf(id))
            db.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    fun checkIfTakeMedOccurExists(name: String, timeOfDay : String, beforeAfterMeal : String) : Boolean{
        var result = false
        var takeMedOccurList: ArrayList<TakeMedicineOccur> = getAllTakeMedOccur()
        for (i: TakeMedicineOccur in takeMedOccurList) {
            if(i.medicineType_Name == name){
                if(i.timeOfDay == timeOfDay){
                    if(i.beforeAfterMeal == beforeAfterMeal){
                        result = true
                    }
                }
            }
        }

        return result
    }

    fun checkIfMedHaveReminder(idTakeMedOccur : String) : Boolean{
        var result = false
        var reminderList = getAllReminders()
        for (i: Reminder in reminderList) {
            if (i.idTakeMedOccur == idTakeMedOccur) {
                result = true
            }
        }
        return result
    }

    fun addNotification(notification: NotificationAmountMed):Boolean
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(ID_NOTIFICATION, notification.iDNotification)
        cv.put(ID_MEDICINE_NOTIFICATION, notification.iDMedicineNotification)
        cv.put(AMOUNT_MED_BELOW_TO_ALARM, notification.AmountBelowToAlarm)


        val result= db.insert(NOTIFICATION_MED_COUNT_TABLE_NAME, null, cv)
        db.close()
        return !result.equals(-1)
    }

    fun getAllNotificationsAboutAmount(): ArrayList<NotificationAmountMed>
    {
        val notificationsAllList= ArrayList<NotificationAmountMed>()
        val db= readableDatabase

        val cursor=db.rawQuery("SELECT * FROM $NOTIFICATION_MED_COUNT_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do{
                    val idNotification= cursor.getString(cursor.getColumnIndex(ID_NOTIFICATION))
                    val idMedicineNotification=cursor.getString(cursor.getColumnIndex(ID_MEDICINE_NOTIFICATION))
                    val amountNotification=cursor.getString(cursor.getColumnIndex(AMOUNT_MED_BELOW_TO_ALARM))
                    val notification=
                        NotificationAmountMed(
                            idNotification,
                            idMedicineNotification,
                            amountNotification.toInt()
                        )
                    notificationsAllList.add(notification)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return notificationsAllList
    }


    fun downloadMedsForLowAmountNotification(): ArrayList<String>{

        var listOfMed : ArrayList<String> = ArrayList()
        var medicineTypeList: ArrayList<MedicineType> = getAllMedicineTypes()
        var notificationslist : ArrayList<NotificationAmountMed> = getAllNotificationsAboutAmount()

        for (i: NotificationAmountMed in notificationslist){

            for (j:MedicineType in medicineTypeList){

                if(i.iDMedicineNotification == j.iDMedicine){
                    if(i.AmountBelowToAlarm>= j.unitInStock){

                        var med_Count = "\n"+ j.name+ " " + j.activedoses + ": liczba tabletek: " + j.unitInStock
                        listOfMed.add(med_Count)
                    }
                }else{
                    if(j.unitInStock<=0){
                        var med_Count = "\n" + j.name + " " + j.activedoses + ": liczba tabletek: " + j.unitInStock
                        listOfMed.add(med_Count)
                    }
                }
            }
        }
        return  listOfMed
    }

    fun removeNotificationAboutAmountMedicine(id: String): Boolean
    {
        try {
            val db=this.writableDatabase
            db.delete(NOTIFICATION_MED_COUNT_TABLE_NAME, "$ID_MEDICINE_NOTIFICATION=?", arrayOf(id))
            db.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun takeNotificatioMedCount(id: String) :String{

        val query = "SELECT * FROM $NOTIFICATION_MED_COUNT_TABLE_NAME WHERE $ID_MEDICINE_NOTIFICATION =  \"$id\""

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var amount: String =""

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
           amount = cursor.getString(cursor.getColumnIndex(AMOUNT_MED_BELOW_TO_ALARM))
            cursor.close()
        }

        db.close()
        return amount
    }

    fun updateNotificationAmount(id:String, amountMedToAlarm: Int):Boolean
    {
        try {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(AMOUNT_MED_BELOW_TO_ALARM, amountMedToAlarm)
            db.update(NOTIFICATION_MED_COUNT_TABLE_NAME, cv, "IDMedicineNotification =?", arrayOf(id))
            db.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }


    fun addMedicineToTake(medicineToTake: MedicineToTake):Boolean
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(ID_MEDICINES_TO_TAKE, medicineToTake.iD)
        cv.put(ID_TAKE_MED_OCCUR, medicineToTake.iDTakeMedOccur)
        cv.put(ID_MEDICINE_TYPE, medicineToTake.iD_MedicineType)
        cv.put(NAME_MED_TO_TAKE, medicineToTake.nameMedToTake)
        cv.put(DOSE_MED_TO_TAKE, medicineToTake.doseMedToTake)
        cv.put(TIME_OF_DAY_MED_TO_TAKE, medicineToTake.timeOfDayMedToTake)
        cv.put(WHEN_MEAL_MED_TO_TAKE, medicineToTake.WhenMedToTake)
        cv.put(DATE_MED_TO_TAKE, medicineToTake.dateSMedToTake)
        cv.put(IF_MED_WAS_TAKEN, medicineToTake.ifMedWasTaken)

        val result= db.insert(MEDICINES_TO_TAKE_TABLE_NAME, null, cv)
        db.close()
        return !result.equals(-1)
    }


    fun getAllTakeMedicinesToday(time : String, date : String):ArrayList<MedicineToTake>{
        val takeMedicineTodayAllList= ArrayList<MedicineToTake>()
        val db= readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $MEDICINES_TO_TAKE_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do {
                    val idTakeMedicinesToday = cursor.getString(cursor.getColumnIndex(ID_MEDICINES_TO_TAKE))
                    val idtakeMedOccur = cursor.getString(cursor.getColumnIndex(ID_TAKE_MED_OCCUR))
                    val idMedicineType = cursor.getString(cursor.getColumnIndex(ID_MEDICINE_TYPE))
                    val medName = cursor.getString(cursor.getColumnIndex(NAME_MED_TO_TAKE))
                    val dose = cursor.getString(cursor.getColumnIndex(DOSE_MED_TO_TAKE))
                    val timeOfDay = cursor.getString(cursor.getColumnIndex(TIME_OF_DAY_MED_TO_TAKE))
                    val whenbeforeAfterMeal = cursor.getString(cursor.getColumnIndex(WHEN_MEAL_MED_TO_TAKE))
                    val dataToTake = cursor.getString(cursor.getColumnIndex(DATE_MED_TO_TAKE))
                    val ifWasTaken = cursor.getString(cursor.getColumnIndex(IF_MED_WAS_TAKEN))

                    if(ifWasTaken == "No" && dataToTake == date && timeOfDay == time ) {

                        val medicineToTakeToday =
                            MedicineToTake(
                                idTakeMedicinesToday,
                                idtakeMedOccur,
                                idMedicineType,
                                medName,
                                dose.toInt(),
                                timeOfDay,
                                whenbeforeAfterMeal,
                                dataToTake,
                                ifWasTaken
                            )
                            takeMedicineTodayAllList.add(medicineToTakeToday)
                    }
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return takeMedicineTodayAllList
    }

    fun getAllTakeMedicinesTodayAllDrugs():ArrayList<MedicineToTake>{
        val takeMedicineTodayAllList= ArrayList<MedicineToTake>()
        val db= readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $MEDICINES_TO_TAKE_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do {
                    val idTakeMedicinesToday = cursor.getString(cursor.getColumnIndex(ID_MEDICINES_TO_TAKE))
                    val idtakeMedOccur = cursor.getString(cursor.getColumnIndex(ID_TAKE_MED_OCCUR))
                    val idMedicineType = cursor.getString(cursor.getColumnIndex(ID_MEDICINE_TYPE))
                    val medName = cursor.getString(cursor.getColumnIndex(NAME_MED_TO_TAKE))
                    val dose = cursor.getString(cursor.getColumnIndex(DOSE_MED_TO_TAKE))
                    val timeOfDay = cursor.getString(cursor.getColumnIndex(TIME_OF_DAY_MED_TO_TAKE))
                    val whenbeforeAfterMeal = cursor.getString(cursor.getColumnIndex(WHEN_MEAL_MED_TO_TAKE))
                    val dataToTake = cursor.getString(cursor.getColumnIndex(DATE_MED_TO_TAKE))
                    val ifWasTaken = cursor.getString(cursor.getColumnIndex(IF_MED_WAS_TAKEN))

                        val medicineToTakeToday =
                            MedicineToTake(
                                idTakeMedicinesToday,
                                idtakeMedOccur,
                                idMedicineType,
                                medName,
                                dose.toInt(),
                                timeOfDay,
                                whenbeforeAfterMeal,
                                dataToTake,
                                ifWasTaken
                            )
                        takeMedicineTodayAllList.add(medicineToTakeToday)

                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return takeMedicineTodayAllList
    }


    fun takingTheTodayMedicine(id:String):Boolean
    {
        try {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(IF_MED_WAS_TAKEN, "Yes")
            db.update(MEDICINES_TO_TAKE_TABLE_NAME, cv, "IdMedicinesToTake =?", arrayOf(id))
            db.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun getUnitInStock(idMedcieType : String) : Int{
        var resultUnitInStock : Int = 1
        var medicineTypeList: ArrayList<MedicineType> = getAllMedicineTypes()
        for (i:MedicineType in medicineTypeList) {
            if(i.iDMedicine==idMedcieType) resultUnitInStock=i.unitInStock
        }
        return resultUnitInStock

    }

    fun reduceAmountOfDrugMedicineTypeByTheTakenDose(idMedcieType : String, dose : Int) : Boolean{
        var currentAmountOfMedicine = getUnitInStock(idMedcieType)
        try {
            val db = this.writableDatabase
            val cv = ContentValues()
            var newUnitInStock= currentAmountOfMedicine - dose
            cv.put(UNIT_IN_STOCK, newUnitInStock)
            db.update(MEDICINE_TABLE_NAME, cv, "IDMedicine =?", arrayOf(idMedcieType))
            db.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun removeTakeTodayMedicie(idTakeMedOccur: String): Boolean {
        val allMediciesToTake= getAllTakeMedicinesTodayAllDrugs()
        for (i: MedicineToTake in allMediciesToTake) {

            if(i.iDTakeMedOccur == idTakeMedOccur)
            try {
                val db = this.writableDatabase
                db.delete(MEDICINES_TO_TAKE_TABLE_NAME, "$ID_TAKE_MED_OCCUR=?", arrayOf(idTakeMedOccur))
                db.close()
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
            return true
        }

    fun takeAllInstancesOfTheSameDrug(takeMedOccur : TakeMedicineOccur):ArrayList<MedicineToTake>{
        val takeMedicineTodayTheSameTypeList= ArrayList<MedicineToTake>()
        val db= readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $MEDICINES_TO_TAKE_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do {
                    val idTakeMedicinesToday = cursor.getString(cursor.getColumnIndex(ID_MEDICINES_TO_TAKE))
                    val idtakeMedOccur = cursor.getString(cursor.getColumnIndex(ID_TAKE_MED_OCCUR))
                    val idMedicineType = cursor.getString(cursor.getColumnIndex(ID_MEDICINE_TYPE))
                    val medName = cursor.getString(cursor.getColumnIndex(NAME_MED_TO_TAKE))
                    val dose = cursor.getString(cursor.getColumnIndex(DOSE_MED_TO_TAKE))
                    val timeOfDay = cursor.getString(cursor.getColumnIndex(TIME_OF_DAY_MED_TO_TAKE))
                    val whenbeforeAfterMeal = cursor.getString(cursor.getColumnIndex(WHEN_MEAL_MED_TO_TAKE))
                    val dataToTake = cursor.getString(cursor.getColumnIndex(DATE_MED_TO_TAKE))
                    val ifWasTaken = cursor.getString(cursor.getColumnIndex(IF_MED_WAS_TAKEN))

                    if(idtakeMedOccur == takeMedOccur.iD && medName == takeMedOccur.medicineType_Name && dose.toInt() == takeMedOccur.dose && timeOfDay == takeMedOccur.timeOfDay && whenbeforeAfterMeal == takeMedOccur.beforeAfterMeal && ifWasTaken == "No") {
                        val medicineToTakeToday =
                            MedicineToTake(
                                idTakeMedicinesToday,
                                idtakeMedOccur,
                                idMedicineType,
                                medName,
                                dose.toInt(),
                                timeOfDay,
                                whenbeforeAfterMeal,
                                dataToTake,
                                ifWasTaken
                            )
                        takeMedicineTodayTheSameTypeList.add(medicineToTakeToday)
                    }
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return takeMedicineTodayTheSameTypeList
    }

    fun removeSingleInstanceTakeTodayMedicie( newDateEnd : LocalDate, listOfTakeMedicineToday : ArrayList<MedicineToTake>): Boolean {
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        for (i: MedicineToTake in listOfTakeMedicineToday) {
            var dateToTakeString = i.dateSMedToTake
            var currentDate = LocalDate.parse(dateToTakeString, formatDate)
            var compareOldDateEndNewDateEnd = currentDate.compareTo(newDateEnd)
            if(compareOldDateEndNewDateEnd > 0)
                try {
                    val db = this.writableDatabase
                    db.delete(MEDICINES_TO_TAKE_TABLE_NAME, "$ID_MEDICINES_TO_TAKE=?", arrayOf(i.iD))
                    db.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return false
                }
        }
        return true
    }

    fun updateTakeTodayMedicineDoses(idTakeMedOccur:String, unitInStock: Int):Boolean
    {
        val allMediciesToTake= getAllTakeMedicinesTodayAllDrugs()
        for (i: MedicineToTake in allMediciesToTake) {
            if (i.iDTakeMedOccur == idTakeMedOccur)
                try {
                    val db = this.writableDatabase
                    val cv = ContentValues()
                    cv.put(DOSE_MED_TO_TAKE, unitInStock)
                    db.update(MEDICINES_TO_TAKE_TABLE_NAME, cv, "IdTakeMedOccur =?", arrayOf(idTakeMedOccur))
                    db.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return false
                }
        }
        return true
    }

    fun addReminder(reminder: Reminder ):Boolean
    {
        val db=this.writableDatabase
        val cv = ContentValues()
        cv.put(REMINDER_ID, reminder.idReminder)
        cv.put(MEDICINE_TO_TAKE_ID, reminder.idTakeMedToday)
        cv.put(MEDICINE_OCCUR_ID, reminder.idTakeMedOccur)
        cv.put(MEDICINE_ID, reminder.idMedicineType)
        cv.put(MEDICINE_NAME, reminder.medicineName)
        cv.put(REMINDER_DATE, reminder.reminderDate)
        cv.put(REMINDER_TIME, reminder.ReminderTime)

        val result= db.insert(REMINDER_TABLE_NAME, null, cv)
        db.close()
        return !result.equals(-1)
    }

    fun getAllReminders(): ArrayList<Reminder>{
        val allRemindersList = ArrayList<Reminder>()
        val db= readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $REMINDER_TABLE_NAME", null)
        if(cursor!= null)
        {
            if(cursor.moveToNext())
            {
                do {
                    val idReminder = cursor.getString(cursor.getColumnIndex(REMINDER_ID))
                    val idTakeMedToday = cursor.getString(cursor.getColumnIndex(MEDICINE_TO_TAKE_ID))
                    val idTakeMedOccur = cursor.getString(cursor.getColumnIndex(MEDICINE_OCCUR_ID))
                    val idMedicineType = cursor.getString(cursor.getColumnIndex(MEDICINE_ID))
                    val medicineName = cursor.getString(cursor.getColumnIndex(MEDICINE_NAME))
                    val reminderDate = cursor.getString(cursor.getColumnIndex(REMINDER_DATE))
                    val reminderTime = cursor.getString(cursor.getColumnIndex(REMINDER_TIME))

                    val reminder = Reminder(
                        idReminder,
                        idTakeMedToday,
                        idTakeMedOccur,
                        idMedicineType,
                        medicineName,
                        reminderDate,
                        reminderTime
                    )
                    allRemindersList.add(reminder)

                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allRemindersList
    }

    fun removeOldReminders(todayDate : String)
    {
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val allRemindersList = getAllReminders()
        var dateToday = LocalDate.parse(todayDate, formatDate)
        for (i: Reminder in allRemindersList) {
            var dateReminder = LocalDate.parse(i.reminderDate, formatDate)
            val compare = dateToday.compareTo(dateReminder)
            if(compare>0){
                try {
                    val db=this.writableDatabase
                    db.delete(REMINDER_TABLE_NAME, "$REMINDER_ID=?", arrayOf(i.idReminder))
                    db.close()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}