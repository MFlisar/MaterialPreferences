package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal class IntSetSetting(
    private val model: SettingsModel,
    override val defaultValue: Set<Int>,
    override val customKey: String?,
    override val cache: Boolean
) : AbstractSetting<Set<Int>>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getIntSet(key, defaultValue)

    override suspend fun persistValue(value: Set<Int>) {
        model.storage.setIntSet(key, value)
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
    ): StorageSetting<Set<Int>> {
        init(property.name)
        return this
    }
}