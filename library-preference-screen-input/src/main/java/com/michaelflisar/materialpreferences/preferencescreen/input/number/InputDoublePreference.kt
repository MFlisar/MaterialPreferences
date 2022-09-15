package com.michaelflisar.materialpreferences.preferencescreen.input.number

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.input.R
import com.michaelflisar.materialpreferences.preferencescreen.input.number.base.InputNumberPreference
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class InputDoublePreference(
    setting: StorageSetting<Double>,
) : InputNumberPreference<Double>(setting) {

    override var defaultValue = 0.0
    override var min = Double.MIN_VALUE
    override var max = Double.MAX_VALUE

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_input_double
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = InputDoubleViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}