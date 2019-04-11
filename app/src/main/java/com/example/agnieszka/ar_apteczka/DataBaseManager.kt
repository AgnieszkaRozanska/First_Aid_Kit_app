package com.example.agnieszka.ar_apteczka

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.MedicineType

public class DataBaseManager {

    constructor(context : Context) {
        sQLiteConnector = SQLConector(context)
    }

    var sQLiteConnector : SQLConector
     val adress= "BazaDanych_1lek.db"


    fun DataBaseManager() {

        //sQLiteConnector.Initialize(adress);
        //sQLiteConnector.Open();

    }

    fun Dispose() {

        //sQLiteConnector.Close();

    }

    fun GetAllMedicineTypes() {

        // funkcja na razie jest w SQLConector

//
    }


    fun AddMedicineType() {

// funkcja na razie jest w SQLConector

//
    }

    fun UpdateMedicineTypeDoses(id:String, unitInStock:Int) {

// funkcja na razie jest w SQLConector
        sQLiteConnector.updateMedicineTypeDoses(id, unitInStock)


    }


    fun RemoveMedicineType(medicinetoRemove:MedicineType) {

        sQLiteConnector.removeMedicineType(medicinetoRemove.iDMedicine)
    }

    fun GetAllTakeMedicineOccurs() {

// // funkcja na razie jest w SQLConector
    }


    fun AddTakeMedicineOccour() {

// funkcja na razie jest w SQLConector

    }

    fun RemoveTakeMedicineOccour() {

        // funkcja na razie jest w SQLConector

    }


    class Converter {

        class FromDatabase {

            fun GetTakeMedicineOccourConverter() {

            }

            fun GetDateTime() {

            }

            fun GetReminder() {

            }
        }

        class ToDatabase {


            fun GetDateTime(){}

        }

        fun GetReminderDateTime(){

        }

        fun GetReminderDescription(){

        }



    }


    class Tables{



    }
}

