package com.example.agnieszka.ar_apteczka.TakeMedicineOccur

import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.MedicineType
import java.util.*

class TakeMedicineOccur {


    var day: String
    var timeOfDay: String
    var dose:Int
    var beforeAfterMeal: String
    var hourReminders:String
    var descriptionReminder:String
    var medicineType: String
    var iD:String;


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


}