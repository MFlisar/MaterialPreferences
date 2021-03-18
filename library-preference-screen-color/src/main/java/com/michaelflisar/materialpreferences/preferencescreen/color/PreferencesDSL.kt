package com.michaelflisar.materialpreferences.preferencescreen.color

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen

// -------------
// DSL
// -------------

inline fun Screen.Appendable.color(setting: StorageSetting<Int>, block: ColorPreference.() -> Unit): ColorPreference {
    return ColorPreference(setting).apply(block).also(::addPreferenceItem)
}