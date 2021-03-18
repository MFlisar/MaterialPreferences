package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal class LongSetting(
        private val model: SettingsModel,
        override val defaultValue: Long,
        override val customKey: String?
) : AbstractSetting<Long>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val flow: Flow<Long> by lazy { model.storage.getLong(key, defaultValue) }
    override suspend fun update(value: Long) = model.storage.setLong(key, value)

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
    ): StorageSetting<Long> {
        init(property.name)
        return this
    }
}