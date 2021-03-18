package com.michaelflisar.materialpreferences.core.initialisation

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object SettingSetup {

    lateinit var context: Context
        private set

    fun init(context: Context) {
        SettingSetup.context = context
    }
}