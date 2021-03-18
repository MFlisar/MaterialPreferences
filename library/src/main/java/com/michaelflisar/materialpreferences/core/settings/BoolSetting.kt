package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal class BoolSetting(
        private val model: SettingsModel,
        override val defaultValue: Boolean,
        override val customKey: String?
) : AbstractSetting<Boolean>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val flow: Flow<Boolean> by lazy { model.storage.getBool(key, defaultValue) }
    override suspend fun update(value: Boolean) = model.storage.setBool(key, value)

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
    ): StorageSetting<Boolean> {
        init(property.name)
        return this
    }
}