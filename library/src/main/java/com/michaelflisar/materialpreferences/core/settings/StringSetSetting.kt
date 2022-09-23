package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal class StringSetSetting(
    private val model: SettingsModel,
    override val defaultValue: Set<String>,
    override val customKey: String?,
    override val cache: Boolean
) : AbstractSetting<Set<String>>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getStringSet(key, defaultValue)

    override suspend fun persistValue(value: Set<String>) {
        model.storage.setStringSet(key, value)
    }

    private fun init(name: String) {
        if (this.name == null) {
            this.name = name
            model.internalProperties[key] = this
        }
    }

    /* Delegate */
    override fun getValue(
        thisRef: SettingsModel,
        property: KProperty<*>
    ): StorageSetting<Set<String>> {
        init(property.name)
        return this
    }
}