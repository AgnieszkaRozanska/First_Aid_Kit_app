package com.example.agnieszka.ar_apteczka.takeMedicineOccur

class TakeMedicineOccur {

    var iD:String
    var iD_MedicineType : String
    var medicineType_Name: String
    var dose:Int
    var timeOfDay: String
    var beforeAfterMeal: String
    var dateStart: String
    var dateEnd: String

    constructor(id: String, idMedicineType:String, medicineType: String, dose:Int, timeOfDay: String,  beforeAfterMeal: String,  dateStart:String, dateEnd:String )
    {
        this.iD=id
        this.iD_MedicineType = idMedicineType
        this.medicineType_Name=medicineType
        this.dose=dose
        this.timeOfDay=timeOfDay
        this.beforeAfterMeal=beforeAfterMeal
        this.dateStart=dateStart
        this.dateEnd=dateEnd
    }
}