package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal abstract class BaseStringSetting<T : String?>(
    private val model: SettingsModel
) : AbstractSetting<T>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow(): Flow<T> = model.storage.getString(key, defaultValue)

    override suspend fun persistValue(value: T) {
        model.storage.setString(key, value)
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

internal class StringSetting(
    model: SettingsModel,
    override val defaultValue: String,
    override val customKey: String?,
    override val cache: Boolean
) : BaseStringSetting<String>(model)

internal class NullableStringSetting(
    model: SettingsModel,
    override val defaultValue: String?,
    override val customKey: String?,
    override val cache: Boolean
) : BaseStringSetting<String?>(model)