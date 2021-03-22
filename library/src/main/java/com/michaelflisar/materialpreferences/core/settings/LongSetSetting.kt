package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal class LongSetSetting(
        private val model: SettingsModel,
        override val defaultValue: Set<Long>,
        override val customKey: String?
) : AbstractSetting<Set<Long>>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val flow: Flow<Set<Long>> by lazy { model.storage.getLongSet(key, defaultValue) }
    override suspend fun update(value: Set<Long>) {
        model.storage.setLongSet(key, value)
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
    ): StorageSetting<Set<Long>> {
        init(property.name)
        return this
    }
}