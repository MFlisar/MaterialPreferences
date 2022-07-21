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

    /*
     * caching can be enabled for a Storage and for each setting individually
     *
     * the provided value here is just the default value for each
     */
    var ENABLE_CACHING = false
}