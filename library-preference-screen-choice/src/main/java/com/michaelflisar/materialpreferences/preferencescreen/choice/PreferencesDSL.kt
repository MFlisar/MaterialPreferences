package com.michaelflisar.materialpreferences.preferencescreen.choice

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen
import com.michaelflisar.materialpreferences.preferencescreen.choice.multi.MultiChoicePreference
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreferenceEnum
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreferenceInt

// -------------
// DSL
// -------------

inline fun Screen.Appendable.singleChoice(setting: StorageSetting<Int>, choices: List<ChoiceItem>, block: SingleChoicePreferenceInt.() -> Unit) =
        SingleChoicePreferenceInt(setting, choices).apply(block).also(::addPreferenceItem)

inline fun <E : Enum<*>> Screen.Appendable.singleChoice(setting: StorageSetting<E>, values: Array<E>, noinline labelProvider: ((E) -> String) = { it.name }, block: SingleChoicePreferenceEnum<E>.() -> Unit) =
        SingleChoicePreferenceEnum(setting, values, labelProvider).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.multiChoice(setting: StorageSetting<Set<Int>>, choices: List<ChoiceItem>, block: MultiChoicePreference.() -> Unit) =
        MultiChoicePreference(setting, choices).apply(block).also(::addPreferenceItem)
