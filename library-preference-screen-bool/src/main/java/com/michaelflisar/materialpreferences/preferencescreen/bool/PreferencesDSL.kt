package com.michaelflisar.materialpreferences.preferencescreen.bool

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen

// -------------
// DSL
// -------------

inline fun Screen.Appendable.switch(setting: StorageSetting<Boolean>, block: com.michaelflisar.materialpreferences.preferencescreen.bool.switchpref.SwitchPreference.() -> Unit) =
        com.michaelflisar.materialpreferences.preferencescreen.bool.switchpref.SwitchPreference(setting)
                .apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.checkbox(setting: StorageSetting<Boolean>, block: com.michaelflisar.materialpreferences.preferencescreen.bool.checkbox.CheckboxPreference.() -> Unit) =
        com.michaelflisar.materialpreferences.preferencescreen.bool.checkbox.CheckboxPreference(
                setting
        ).apply(block).also(::addPreferenceItem)