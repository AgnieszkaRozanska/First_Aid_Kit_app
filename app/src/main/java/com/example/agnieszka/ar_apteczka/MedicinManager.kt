package com.example.agnieszka.ar_apteczka

import android.app.Application
import com.example.agnieszka.ar_apteczka.FirstAidKitAllYoursMedicines.MedicineType

class MedicinManager : Application {
    private var databaseManager : DataBaseManager

    //private var instance : MedicineType

     constructor() {
        databaseManager = DataBaseManager(applicationContext)
    }

    companion object {
        val instance = MedicinManager()
    }


    /*
          public static MedicineManager GetInstance()  //
            {

            }
            /*
            GetInstance() - służy do odwoływania się do tego(jednego) obiektu
           W przypadku, gdy ten obiekt jest pobierany pierwszy raz, jest tworzony i przypisywany do zmiennej "instance"
            Później zwraca się po prostu zawsze ten sam obiekt
            */

            fun addMedicine(medType:MedicineType )   // metoda do dodania leku do bazy
            {

            }

            fun addTakeMedicineOccur( takeMedicineOccur: TakeMedicineOccur)  //metoda do odania wystąpienia wziecia leku do bazy danych
            {

            }
*/

            fun deleteMedicineType( medTypetoDelete: MedicineType)  // Metoda usuwa lek z bazy danych
            {

            }

/*
            fun deleteTakeMedicineOccur( takeMedicineOccurtoDelete: TakeMedicineOccur) // Metoda usuwa wystąpienie brania leku
            {

            }

            fun List<MedicineType> GetMedicineTypesToShow() //Lista nazw leków (do wyświetlenia)
            {
            }

            fun List<TakeMedicineOccur> GetTakeMedicineOccursToShow() // Lista wystapien leku - zażywanie leku (Do wyświetlania)
            {
            }

            fun isMedTypeUsed( medicineTypeList:MedicineType):Boolean
            {

            }


            fun showReminder(message:String)
            {


            }
            fun isPossibleToTakeMedicine( takeMedicineOccur:TakeMedicineOccur):Boolean
            {

            }

            fun takeMedicine( takeMedicineOccur:TakeMedicineOccur)
            {

            }
    */
        fun addDosesToMedicine( medType:MedicineType,  unotInStock:Int)
        {
            databaseManager.UpdateMedicineTypeDoses(medType.iDMedicine, unotInStock )
        }






}