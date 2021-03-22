package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal class StringSetting(
        private val model: SettingsModel,
        override val defaultValue: String,
        override val customKey: String?
) : AbstractSetting<String>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val flow: Flow<String> by lazy { model.storage.getString(key, defaultValue) }
    override suspend fun update(value: String)  {
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
    ): StorageSetting<String> {
        init(property.name)
        return this
    }
}