package com.michaelflisar.materialpreferences.preferencescreen.input

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen
import com.michaelflisar.materialpreferences.preferencescreen.input.integer.InputIntPreference
import com.michaelflisar.materialpreferences.preferencescreen.input.string.InputStringPreference
import com.michaelflisar.materialpreferences.preferencescreen.preferences.*

// -------------
// DSL
// -------------

inline fun Screen.Appendable.input(setting: StorageSetting<String>, block: InputStringPreference.() -> Unit) =
        InputStringPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.input(setting: StorageSetting<Int>, block: InputIntPreference.() -> Unit) =
        InputIntPreference(setting).apply(block).also(::addPreferenceItem)