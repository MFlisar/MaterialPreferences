package com.michaelflisar.materialpreferences.preferencescreen.input.initialisation

import android.content.Context
import androidx.startup.Initializer
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.input.integer.InputIntPreference
import com.michaelflisar.materialpreferences.preferencescreen.input.string.InputStringPreference

internal class Initialiser : Initializer<Unit> {
    override fun create(context: Context) {
        ViewHolderFactory.register(InputStringPreference.TYPE, InputStringPreference.Companion)
        ViewHolderFactory.register(InputIntPreference.TYPE, InputIntPreference.Companion)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
