package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal class StringSetSetting(
        private val model: SettingsModel,
        override val defaultValue: Set<String>,
        override val customKey: String?
) : AbstractSetting<Set<String>>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val flow: Flow<Set<String>> by lazy { model.storage.getStringSet(key, defaultValue) }
    override suspend fun update(value: Set<String>) {
        model.storage.setStringSet(key, value)
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
    ): StorageSetting<Set<String>> {
        init(property.name)
        return this
    }
}