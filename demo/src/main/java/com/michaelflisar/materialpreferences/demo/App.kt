package com.michaelflisar.materialpreferences.demo

import android.app.Application
import com.michaelflisar.lumberjack.L
import com.michaelflisar.materialpreferences.demo.settings.DemoSettingsModel
import timber.log.ConsoleTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        L.plant(ConsoleTree())

        L.d { "DemoSettingsModel: ${DemoSettingsModel.settings.size}" }
    }
}