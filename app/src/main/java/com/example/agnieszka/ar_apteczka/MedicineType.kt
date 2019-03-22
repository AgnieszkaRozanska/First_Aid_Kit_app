package com.example.agnieszka.ar_apteczka

import java.util.*

class MedicineType {


    enum class KindMedicineType{

        tabletka_okragla,
        tabletka_owalna,
        kapsułka,
        krople,
        masc,
        inhalator,
        zastrzyk,
        czopek

    }

    var iDMedicine:String
    //var kindMedicineType: KindMedicineType
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
    constructor( name: String,kind: String , description: String, unitInStock: Int) : this(UUID.randomUUID().toString(), name, kind, description, unitInStock){

    }


}