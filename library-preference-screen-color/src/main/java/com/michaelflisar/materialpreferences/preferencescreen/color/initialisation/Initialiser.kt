package com.michaelflisar.materialpreferences.preferencescreen.color.initialisation

import android.content.Context
import androidx.startup.Initializer
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.color.ColorPreference

internal class Initialiser : Initializer<Unit> {
    override fun create(context: Context) {
        ViewHolderFactory.register(ColorPreference.TYPE, ColorPreference.Companion)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
