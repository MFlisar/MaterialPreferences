package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal abstract class BaseBoolSetting<T : Boolean?>(
    private val model: SettingsModel
) : AbstractSetting<T>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getBool(key, defaultValue)

    override suspend fun persistValue(value: T) {
        model.storage.setBool(key, value)
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

internal class BoolSetting(
    model: SettingsModel,
    override val defaultValue: Boolean,
    override val customKey: String?,
    override val cache: Boolean
) : BaseBoolSetting<Boolean>(model)

internal class NullableBoolSetting(
    model: SettingsModel,
    override val defaultValue: Boolean?,
    override val customKey: String?,
    override val cache: Boolean
) : BaseBoolSetting<Boolean?>(model)