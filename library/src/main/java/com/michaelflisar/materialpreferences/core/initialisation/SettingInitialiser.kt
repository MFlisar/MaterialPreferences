package com.michaelflisar.materialpreferences.core.initialisation

import android.content.Context
import androidx.startup.Initializer

class SettingInitialiser : Initializer<SettingSetup> {
    override fun create(context: Context): SettingSetup {
        SettingSetup.init(context)
        return SettingSetup
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
