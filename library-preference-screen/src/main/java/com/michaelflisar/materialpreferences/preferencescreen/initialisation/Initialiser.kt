package com.michaelflisar.materialpreferences.preferencescreen.initialisation

import android.content.Context
import androidx.startup.Initializer
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory

internal class Initialiser : Initializer<Unit> {
    override fun create(context: Context) {
        ViewHolderFactory.init()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
