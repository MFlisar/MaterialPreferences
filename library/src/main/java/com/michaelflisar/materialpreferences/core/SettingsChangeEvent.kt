package com.michaelflisar.materialpreferences.core

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting

class SettingsChangeEvent<T : Any?>(
        val setting: StorageSetting<T>,
        val value: T
)