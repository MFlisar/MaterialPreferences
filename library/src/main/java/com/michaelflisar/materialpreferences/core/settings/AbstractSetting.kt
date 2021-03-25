package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting

abstract class AbstractSetting<T : Any?> : StorageSetting<T> {
    abstract val customKey: String?
}