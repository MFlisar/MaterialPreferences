package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.content.Context
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.BasePreferenceItem
import com.michaelflisar.text.Text
import com.michaelflisar.text.asText

abstract class SingleChoicePreference<T : Any>(
        override val setting: StorageSetting<T>
) : BasePreferenceItem(), PreferenceItem.PreferenceWithData<T> {

    override var summary: Text = "%s".asText()
    override var canChange: (value: T) -> Boolean = { true }
    override var onChanged: ((value: T) -> Unit)? = null
    var showCheckBoxes: Boolean = false
    var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet

    abstract fun getChoiceLabels(context: Context): List<String>
    abstract fun getChoiceValue(index: Int): T
    abstract fun getChoiceDisplayValue(context: Context, item: T): String
}