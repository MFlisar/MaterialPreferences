package com.michaelflisar.materialpreferences.preferencescreen.input.number

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.input.R
import com.michaelflisar.materialpreferences.preferencescreen.input.number.base.InputNumberPreference
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class InputLongPreference(
    setting: StorageSetting<Long>,
) : InputNumberPreference<Long>(setting) {

    override var defaultValue = 0L
    override var min = Long.MIN_VALUE
    override var max = Long.MAX_VALUE

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_input_long
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = InputLongViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}