package com.michaelflisar.materialpreferences.preferencescreen.input.number.base

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.BasePreferenceItem
import com.michaelflisar.text.Text
import com.michaelflisar.text.asText

abstract class InputNumberPreference<T : Number>(
    override val setting: StorageSetting<T>,
) : BasePreferenceItem(), PreferenceItem.PreferenceWithData<T>, PreferenceItem.PreferenceDialog<T> {
    override var summary: Text = "%s".asText()
    var hint: Text = "".asText()
    var selectAllOnFocus: Boolean = false
    abstract var defaultValue: T
    abstract var min: T
    abstract var max: T
    override var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet
    override var canChange: (value: T) -> Boolean = { true }
    override var onChanged: ((value: T) -> Unit)? = null
}