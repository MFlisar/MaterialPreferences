package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal class IntSetting(
        private val model: SettingsModel,
        override val defaultValue: Int,
        override val customKey: String?
) : AbstractSetting<Int>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val flow: Flow<Int> by lazy { model.storage.getInt(key, defaultValue) }
    override suspend fun update(value: Int) = model.storage.setInt(key, value)

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
    ): StorageSetting<Int> {
        init(property.name)
        return this
    }
}