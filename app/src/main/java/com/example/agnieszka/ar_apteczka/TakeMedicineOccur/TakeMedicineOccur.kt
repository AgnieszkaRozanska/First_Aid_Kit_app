package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.MedicineType
import java.util.*

class TakeMedicineOccur {


    //public DateTime DataTime { get; }
    //var day: Date
    var day: String
    //var timeOfDay: TimeOfDay
    var timeOfDay: String
    var dose:Int
    //var beforeAfterMeal: BeforeAfterMeal
    var beforeAfterMeal: String
    var hourReminders:String
    var descriptionReminder:String
    //var medicineType: MedicineType
    var medicineType: String
    var iD:String;

 /*   enum class TimeOfDay
    {
        rano,
        poludnie,
        wieczor

    }

    enum class BeforeAfterMeal
    {
        przed_Posilkiem,
        w_Trakcie_Posilku,
        po_Posilku;

    }
*/

    constructor(id:String, medicineType: String,dose:Int, timeOfDay: String,  beforeAfterMeal: String,  day:String,  hourReminders:String, descriptionReminder:String )
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

 /* constructor(day:Date, timeOfDay: TimeOfDay, dose:Int, beforeAfterMeal: BeforeAfterMeal, hourReminders:Date, descriptionReminder:String, medicineType: MedicineType)
          :this(day, timeOfDay, dose, beforeAfterMeal, hourReminders, descriptionReminder, medicineType, UUID.randomUUID().toString())
*/
}