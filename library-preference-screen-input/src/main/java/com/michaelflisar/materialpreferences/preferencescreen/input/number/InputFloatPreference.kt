package com.michaelflisar.materialpreferences.preferencescreen.input.number

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.input.R
import com.michaelflisar.materialpreferences.preferencescreen.input.number.base.InputNumberPreference
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class InputFloatPreference(
    setting: StorageSetting<Float>,
) : InputNumberPreference<Float>(setting) {

    override var defaultValue = 0f
    override var min = Float.MIN_VALUE
    override var max = Float.MAX_VALUE

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_input_float
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = InputFloatViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}