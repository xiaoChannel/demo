package com.hiltoncn.demo

import android.app.Application
import com.hiltoncn.demo.api.HttpClient


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        HttpClient.initClient()
    }
}