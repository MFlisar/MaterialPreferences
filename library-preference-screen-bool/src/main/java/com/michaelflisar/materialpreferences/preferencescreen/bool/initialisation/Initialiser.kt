package com.michaelflisar.materialpreferences.preferencescreen.bool.initialisation

import android.content.Context
import androidx.startup.Initializer
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.bool.checkbox.CheckboxPreference
import com.michaelflisar.materialpreferences.preferencescreen.bool.switchb.SwitchPreference

internal class Initialiser : Initializer<Unit> {
    override fun create(context: Context) {
        ViewHolderFactory.register(CheckboxPreference.TYPE, CheckboxPreference.Companion)
        ViewHolderFactory.register(SwitchPreference.TYPE, SwitchPreference.Companion)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
