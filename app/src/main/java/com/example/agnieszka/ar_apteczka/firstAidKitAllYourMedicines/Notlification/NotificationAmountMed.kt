package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines.Notlification

class NotificationAmountMed {

    var iDNotification : String
    var iDMedicineNotification : String
    var AmountBelowToAlarm : Int

    constructor(iDNotification: String, iDMedicineNotification: String,AmountBelowToAlarm: Int){
        this.iDNotification = iDNotification
        this.iDMedicineNotification = iDMedicineNotification
        this.AmountBelowToAlarm = AmountBelowToAlarm
    }

}