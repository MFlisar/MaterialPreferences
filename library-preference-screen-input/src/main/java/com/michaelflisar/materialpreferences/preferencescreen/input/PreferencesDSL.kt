package com.michaelflisar.materialpreferences.preferencescreen.input

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen
import com.michaelflisar.materialpreferences.preferencescreen.input.number.InputDoublePreference
import com.michaelflisar.materialpreferences.preferencescreen.input.number.InputFloatPreference
import com.michaelflisar.materialpreferences.preferencescreen.input.number.InputIntPreference
import com.michaelflisar.materialpreferences.preferencescreen.input.number.InputLongPreference
import com.michaelflisar.materialpreferences.preferencescreen.input.string.InputStringPreference

// -------------
// DSL
// -------------

inline fun Screen.Appendable.input(setting: StorageSetting<String>, block: InputStringPreference.() -> Unit) =
        InputStringPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.input(setting: StorageSetting<Int>, block: InputIntPreference.() -> Unit) =
        InputIntPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.input(setting: StorageSetting<Long>, block: InputLongPreference.() -> Unit) =
        InputLongPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.input(setting: StorageSetting<Float>, block: InputFloatPreference.() -> Unit) =
        InputFloatPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.input(setting: StorageSetting<Double>, block: InputDoublePreference.() -> Unit) =
        InputDoublePreference(setting).apply(block).also(::addPreferenceItem)