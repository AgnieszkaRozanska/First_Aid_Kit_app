package com.example.agnieszka.ar_apteczka

class DataBaseManager {

    //var sQLiteConnector = SQLiteConnector()
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

    fun UpdateMedicineTypeDoses() {

// funkcja na razie jest w SQLConector
    }


    fun RemoveMedicineType() {

// funkcja na razie jest w SQLConector
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

