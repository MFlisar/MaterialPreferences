package com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.BasePreferenceItem

abstract class BaseSliderPreference<T: Number>(
    override val setting: StorageSetting<T>
) : BasePreferenceItem(), PreferenceItem.PreferenceWithData<T> {

    abstract var min: T
    abstract var max: T
    abstract var stepSize: T?
    open var discrete: Boolean = false

    abstract val defaultStepSize: T?

    open var labelFormatter: ((value: Float) -> String)? = null
    override var canChange: (value: T) -> Boolean = { true }
    override var onChanged: ((value: T) -> Unit)? = null
}