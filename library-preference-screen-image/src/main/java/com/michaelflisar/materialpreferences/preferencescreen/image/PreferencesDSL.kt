package com.michaelflisar.materialpreferences.preferencescreen.image

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.Screen

// -------------
// DSL
// -------------

inline fun <T : Any> Screen.Appendable.image(
    setting: StorageSetting<T>,
    manager: ImagePreferenceHandler<T>,
    block: ImagePreference<T>.() -> Unit
): ImagePreference<T> {
    return ImagePreference(setting, manager)
        .apply(block)
        .also(::addPreferenceItem)
}