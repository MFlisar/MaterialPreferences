package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

internal class FloatSetting(
        private val model: SettingsModel,
        override val defaultValue: Float,
        override val customKey: String?
) : AbstractSetting<Float>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val flow: Flow<Float> by lazy { model.storage.getFloat(key, defaultValue) }
    override suspend fun update(value: Float) {
        model.storage.setFloat(key, value)
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
    ): StorageSetting<Float> {
        init(property.name)
        return this
    }
}