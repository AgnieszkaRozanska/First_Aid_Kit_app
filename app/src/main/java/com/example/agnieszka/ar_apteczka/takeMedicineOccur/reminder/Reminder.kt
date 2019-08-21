package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.widget.TimePicker
import java.time.LocalDateTime

class Reminder {


    var idReminder : String
    var idTakeMedToday : String
    var idTakeMedOccur : String
    var idMedicineType : String
    var reminderDate : String
    var ReminderTime : String


    constructor(id : String, idTakeMedToday : String, idTakeMedOccur : String, idMedType : String, date : String,  hour : String)
    {
        this.idReminder = id
        this.idTakeMedToday = idTakeMedToday
        this.idTakeMedOccur = idTakeMedOccur
        this.idMedicineType = idMedType
        this.reminderDate = date
        this.ReminderTime = hour

    }
}