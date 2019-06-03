package com.example.agnieszka.ar_apteczka.firstAidKitAllYourMedicines

class MedicineType {
    var iDMedicine:String
    var kindMedicineType:String
    var name:String
    var description:String
    var unitInStock:Int
    var activedoses:String

    constructor(iDMedicine: String, name: String,kind: String , description: String, unitInStock: Int, activedose:String){
        this.iDMedicine = iDMedicine
        this.name = name
        this.kindMedicineType = kind
        this.description = description
        this.unitInStock = unitInStock
        this.activedoses = activedose
    }
}