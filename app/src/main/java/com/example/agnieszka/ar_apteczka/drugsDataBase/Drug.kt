package com.example.agnieszka.ar_apteczka.drugsDataBase

class Drug {
    var idDrug : String
    var nameDrug : String
    var power : String
    var kind : String
    var activeDose : String

    constructor(id: String, name: String,power: String , kind : String, activeDose: String){
        this.idDrug = id
        this.nameDrug = name
        this.power = power
        this.kind = kind
        this.activeDose = activeDose
    }
}