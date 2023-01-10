package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal class FloatSetSetting(
    private val model: SettingsModel,
    override val defaultValue: Set<Float>,
    override val customKey: String?,
    override val cache: Boolean
) : AbstractSetting<Set<Float>>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getFloatSet(key, defaultValue)

    override suspend fun persistValue(value: Set<Float>) {
        model.storage.setFloatSet(key, value)
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
    ): StorageSetting<Set<Float>> {
        init(property.name)
        return this
    }
}