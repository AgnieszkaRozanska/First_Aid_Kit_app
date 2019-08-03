package com.example.agnieszka.ar_apteczka.todaysMedicines

class MedicineToTake {


    var iD:String
    var iDTakeMedOccur:String
    var iD_MedicineType : String
    var nameMedToTake: String
    var doseMedToTake:Int
    var timeOfDayMedToTake: String
    var WhenMedToTake: String
    var dateSMedToTake: String
    var ifMedWasTaken: String

    constructor(id: String, idMedOccur : String, idMedicineType:String, nameMedtoTake: String, dose:Int, timeOfDay: String,  whenMedToTake: String,  date:String, ifMedWasTaken:String )
    {
        this.iD=id
        this.iDTakeMedOccur = idMedOccur
        this.iD_MedicineType=idMedicineType
        this.nameMedToTake=nameMedtoTake
        this.doseMedToTake=dose
        this.timeOfDayMedToTake=timeOfDay
        this.WhenMedToTake=whenMedToTake
        this.dateSMedToTake=date
        this.ifMedWasTaken=ifMedWasTaken

    }


}