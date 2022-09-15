package com.michaelflisar.materialpreferences.preferencescreen.input.initialisation

import android.content.Context
import androidx.startup.Initializer
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.input.number.InputDoublePreference
import com.michaelflisar.materialpreferences.preferencescreen.input.number.InputFloatPreference
import com.michaelflisar.materialpreferences.preferencescreen.input.number.InputIntPreference
import com.michaelflisar.materialpreferences.preferencescreen.input.number.InputLongPreference
import com.michaelflisar.materialpreferences.preferencescreen.input.string.InputStringPreference

internal class Initialiser : Initializer<Unit> {
    override fun create(context: Context) {
        ViewHolderFactory.register(InputStringPreference.TYPE, InputStringPreference.Companion)
        ViewHolderFactory.register(InputIntPreference.TYPE, InputIntPreference.Companion)
        ViewHolderFactory.register(InputLongPreference.TYPE, InputLongPreference.Companion)
        ViewHolderFactory.register(InputFloatPreference.TYPE, InputFloatPreference.Companion)
        ViewHolderFactory.register(InputDoublePreference.TYPE, InputDoublePreference.Companion)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
