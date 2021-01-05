package com.grosalex.weathercompose

import android.app.Application

class WeatherComposeApp :Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        lateinit var app: WeatherComposeApp
    }
}