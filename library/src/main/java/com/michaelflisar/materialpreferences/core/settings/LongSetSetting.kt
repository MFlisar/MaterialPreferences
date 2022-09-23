package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal class LongSetSetting(
    private val model: SettingsModel,
    override val defaultValue: Set<Long>,
    override val customKey: String?,
    override val cache: Boolean
) : AbstractSetting<Set<Long>>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getLongSet(key, defaultValue)

    override suspend fun persistValue(value: Set<Long>) {
        model.storage.setLongSet(key, value)
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
    ): StorageSetting<Set<Long>> {
        init(property.name)
        return this
    }
}