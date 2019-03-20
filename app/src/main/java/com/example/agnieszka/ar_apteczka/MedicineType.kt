package com.example.agnieszka.ar_apteczka

import java.util.*

class MedicineType {


    enum class KindMedicineType{

        tabletka_okragła,
        tabletka_owalna,
        kapsułka,
        krople,
        maść,
        inhalator,
        zastrzyk,
        czopek

    }

    var iDMedicine:String
    var kindMedicineType: KindMedicineType
    var name:String
    var description:String
    var unitInStrck:Int

    constructor(iDMedicine: String, name: String,kind: KindMedicineType , description: String, unitInStock: Int){

        this.iDMedicine=iDMedicine;
        this.name=name;
        this.kindMedicineType=kind;
        this.description=description;
        this.unitInStrck=unitInStock;


    }
    constructor( name: String,kind: KindMedicineType , description: String, unitInStock: Int) : this(UUID.randomUUID().toString(), name, kind, description, unitInStock){

    }


}