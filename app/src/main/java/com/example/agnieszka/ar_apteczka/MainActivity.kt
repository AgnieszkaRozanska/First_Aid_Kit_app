package com.example.agnieszka.ar_apteczka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Button_Start.setOnClickListener {
            // tworzymy aktywnosc ze przeskoczy do drugiego okna


            // Stetho //
            Stetho.initializeWithDefaults(this)
            OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()

            var Activity1: Intent = Intent(applicationContext, Menu::class.java)
            startActivity(Activity1)
        }


    }
}
