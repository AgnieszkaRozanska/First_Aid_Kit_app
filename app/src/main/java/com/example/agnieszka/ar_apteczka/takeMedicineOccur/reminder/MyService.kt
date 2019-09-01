package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class MyService {
    companion object {
        val TAG = "Info"

        private fun newIntent(context: Context): Intent {
            return Intent(context, MyBroadcastReceiver::class.java)
        }

        fun setServiceAlarm(context: Context, isOn: Boolean, cal: Calendar?) {
            val i =
                newIntent(
                    context
                )

            i.putExtra("ID", 12345678)
            val pi = PendingIntent.getBroadcast(
                context,
                cal!!.timeInMillis.toInt(),
                i,
                PendingIntent.FLAG_UPDATE_CURRENT)


            val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (isOn)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal!!.timeInMillis,
                    60000,
                    pi
                )
            else {
                alarmManager.cancel(pi)
                pi.cancel()
            }

        }
    }


}