package com.example.agnieszka.ar_apteczka

import android.provider.ContactsContract
import java.util.*

class TakeMedicineOccur {


    //public DateTime DataTime { get; }
    var day: Date
    var timeOfDay:TimeOfDay
    var dose:Int
    var beforeAfterMeal:BeforeAfterMeal
    var hourReminders:Date
    var descriptionReminder:String
    var medicineType:MedicineType
    var iD:String;

    enum class TimeOfDay
    {
        rano,
        poludnie,
        wieczor,
        noc
    }

    enum class BeforeAfterMeal
    {
        przed_Posilkiem,
        w_Trakcie_Posilku,
        po_Posilku;

    }


    constructor(day:Date , timeOfDay:TimeOfDay , dose:Int, beforeAfterMeal:BeforeAfterMeal , hourReminders:Date,descriptionReminder:String , medicineType:MedicineType, id:String )
    {
        this.day=day
        this.timeOfDay=timeOfDay
        this.dose=dose
        this.beforeAfterMeal=beforeAfterMeal
        this.hourReminders=hourReminders
        this.descriptionReminder=descriptionReminder
        this.medicineType=medicineType;
        this.iD=id

    }

  constructor(day:Date , timeOfDay:TimeOfDay , dose:Int, beforeAfterMeal:BeforeAfterMeal , hourReminders:Date,descriptionReminder:String , medicineType:MedicineType )
          :this(day, timeOfDay, dose, beforeAfterMeal, hourReminders, descriptionReminder, medicineType, UUID.randomUUID().toString())

}