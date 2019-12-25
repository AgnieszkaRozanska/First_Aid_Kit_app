package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat.startActivity
import com.example.agnieszka.ar_apteczka.R
import com.example.agnieszka.ar_apteczka.sqlconnctor.SQLConector
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MyService  {
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
                PendingIntent.FLAG_UPDATE_CURRENT
            )


            val alarmManager: AlarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (isOn)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal!!.time.time,
                    2000,
                    pi
                )
            else {
                alarmManager.cancel(pi)
                pi.cancel()
            }

        }

         fun takeTodayDate():String{
            val current = LocalDateTime.now()
            val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            var dateResult = current.format(formatDate).toString()
            return  dateResult
        }

         fun takeTimeNow() : String{
            val current = LocalDateTime.now()
            val formatTime = DateTimeFormatter.ofPattern("HH:mm")
            var timeResult = current.format(formatTime).toString()
            return  timeResult

        }
    }
}


