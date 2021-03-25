package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal abstract class BaseStringSetting<T : String?>(
        private val model: SettingsModel
) : AbstractSetting<T>() {
    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val flow: Flow<T> by lazy {
        model.storage.getString(key, defaultValue)
    }

    override suspend fun update(value: T) {
        model.storage.setString(key, value)
        model.storage.onValueChanged(this, value)
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
        override val customKey: String?
) : BaseStringSetting<String>(model)

internal class NullableStringSetting(
        model: SettingsModel,
        override val defaultValue: String?,
        override val customKey: String?
) : BaseStringSetting<String?>(model)