package com.michaelflisar.materialpreferences.preferencescreen.input.number

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.input.R
import com.michaelflisar.materialpreferences.preferencescreen.input.number.base.InputNumberPreference
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class InputIntPreference(
    setting: StorageSetting<Int>,
) : InputNumberPreference<Int>(setting) {

    override var defaultValue = 0
    override var min = Int.MIN_VALUE
    override var max = Int.MAX_VALUE

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_input_int
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = InputIntViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}