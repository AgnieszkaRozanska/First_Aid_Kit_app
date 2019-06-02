package com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines

import java.util.*

class MedicineType {

    var iDMedicine:String
    var kindMedicineType:String
    var name:String
    var description:String
    var unitInStock:Int

    constructor(iDMedicine: String, name: String,kind: String , description: String, unitInStock: Int){

        this.iDMedicine=iDMedicine;
        this.name=name;
        this.kindMedicineType=kind;
        this.description=description;
        this.unitInStock=unitInStock;


    }
}