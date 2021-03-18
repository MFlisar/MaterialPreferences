package com.michaelflisar.materialpreferences.preferencescreen.choice

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen
import com.michaelflisar.materialpreferences.preferencescreen.choice.multi.MultiChoicePreference
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreference

// -------------
// DSL
// -------------

inline fun Screen.Appendable.singleChoice(setting: StorageSetting<Int>, block: SingleChoicePreference.() -> Unit) =
        SingleChoicePreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.multiChoice(setting: StorageSetting<Set<Int>>, block: MultiChoicePreference.() -> Unit) =
        MultiChoicePreference(setting).apply(block).also(::addPreferenceItem)
