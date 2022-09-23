package com.michaelflisar.materialpreferences.preferencescreen.slider

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.SliderDoublePreference
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.SliderFloatPreference
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.SliderIntPreference
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.SliderLongPreference

// -------------
// DSL
// -------------

inline fun Screen.Appendable.slider(
    setting: StorageSetting<Int>,
    block: SliderIntPreference.() -> Unit
) = SliderIntPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.slider(
    setting: StorageSetting<Long>,
    block: SliderLongPreference.() -> Unit
) = SliderLongPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.slider(
    setting: StorageSetting<Float>,
    block: SliderFloatPreference.() -> Unit
) = SliderFloatPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.slider(
    setting: StorageSetting<Double>,
    block: SliderDoublePreference.() -> Unit
) = SliderDoublePreference(setting).apply(block).also(::addPreferenceItem)