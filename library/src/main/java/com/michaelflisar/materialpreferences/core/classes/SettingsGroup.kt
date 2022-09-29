package com.michaelflisar.materialpreferences.core.classes

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

class SettingsGroup<T>(
    private vararg val settings: StorageSetting<*>,
    private val distinctUntilChanged: Boolean = true,
    private val converter: (List<Any?>) -> T
) {
    val flow by lazy {
        val f = combine(settings.map { it.flow }) {
            converter(it.toList())
        }
        if (distinctUntilChanged)
            f.distinctUntilChanged()
        else
            f
    }

    val value: T
        get() = converter(settings.map { it.value })

    suspend fun reset(): List<StorageSetting<*>> {
        val settingsToReset = ArrayList<StorageSetting<*>>()
        settings.forEach {
            if (it.reset())
                settingsToReset.add(it)
        }
        return settingsToReset
    }
}