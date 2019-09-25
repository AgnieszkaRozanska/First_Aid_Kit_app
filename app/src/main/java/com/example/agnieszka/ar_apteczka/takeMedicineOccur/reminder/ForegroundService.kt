package com.example.agnieszka.ar_apteczka.takeMedicineOccur.reminder

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.example.agnieszka.ar_apteczka.Menu
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.notificationManager
import org.jetbrains.anko.uiThread

class ForegroundService : Service() {
    companion object {
        val CHANNEL_ID = "ForegroundServiceChannel"
        val CHANNEL_ID_CHILD = "ForegroundServiceChannelCHILD"
        private var  isRunning = false
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getIntExtra("time",15)
        createNotificationChannel()
        val notificationIntent = Intent(this, Menu::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("content")
            .setSmallIcon(R.drawable.btn_minus)
            //.setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        isRunning = true
        val context = this
        doAsync {
            while(isRunning)
            {
                //SystemClock.sleep(input * 10_000L)
                SystemClock.sleep(60000)
                uiThread {
                    if(isRunning) {
                        val notification = NotificationCompat.Builder(context, CHANNEL_ID_CHILD)
                            .setContentTitle("child")
                            .setContentText("something")
                            .setSmallIcon(R.drawable.btn_plus)
                            //.setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build()
                        with(NotificationManagerCompat.from(context)) {
                            notificationManager.notify(2, notification)
                        }
                    }
                }
            }
        }

        //do heavy work on a background thread


        //stopSelf();

        return START_NOT_STICKY
    }




    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val serviceChannel2 = NotificationChannel(
                CHANNEL_ID_CHILD,
                "Foreground Service ChannelChild ",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
            manager.createNotificationChannel(serviceChannel2)
        }
    }



}