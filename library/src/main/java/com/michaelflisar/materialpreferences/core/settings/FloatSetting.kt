package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal abstract class BaseFloatSetting<T : Float?>(
    private val model: SettingsModel
) : AbstractSetting<T>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getFloat(key, defaultValue)

    override suspend fun persistValue(value: T) {
        model.storage.setFloat(key, value)
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
    ): StorageSetting<T> {
        init(property.name)
        return this
    }
}

internal class FloatSetting(
    model: SettingsModel,
    override val defaultValue: Float,
    override val customKey: String?,
    override val cache: Boolean
) : BaseFloatSetting<Float>(model)

internal class NullableFloatSetting(
    model: SettingsModel,
    override val defaultValue: Float?,
    override val customKey: String?,
    override val cache: Boolean
) : BaseFloatSetting<Float?>(model)