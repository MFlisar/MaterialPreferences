package com.michaelflisar.materialpreferences.preferencescreen.bool

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen
import com.michaelflisar.materialpreferences.preferencescreen.bool.checkbox.CheckboxPreference
import com.michaelflisar.materialpreferences.preferencescreen.bool.switches.SwitchCompactPreference
import com.michaelflisar.materialpreferences.preferencescreen.bool.switches.SwitchPreference

// -------------
// DSL
// -------------

inline fun Screen.Appendable.switch(
    setting: StorageSetting<Boolean>,
    block: SwitchPreference.() -> Unit
) = SwitchPreference(setting)
    .apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.switchCompact(
    setting: StorageSetting<Boolean>,
    block: SwitchCompactPreference.() -> Unit
) = SwitchCompactPreference(setting)
    .apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.checkbox(
    setting: StorageSetting<Boolean>,
    block: CheckboxPreference.() -> Unit
) = CheckboxPreference(
    setting
).apply(block).also(::addPreferenceItem)