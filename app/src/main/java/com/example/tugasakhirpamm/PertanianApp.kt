package com.example.tugasakhirpamm

import android.app.Application
import com.example.tugasakhirpamm.dependenciesinjection.AppContainer
import com.example.tugasakhirpamm.dependenciesinjection.PertanianContainer

class PertanianApp: Application(){
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = PertanianContainer()
    }
}