package com.michaelflisar.materialpreferences.preferencescreen.bool.checkbox

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.R
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.BasePreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class CheckboxPreference(
    override val setting: StorageSetting<Boolean>
) : BasePreferenceItem(), PreferenceItem.PreferenceWithData<Boolean> {

    override var canChange: (value: Boolean) -> Boolean = { true }
    override var onChanged: ((value: Boolean) -> Unit)? = null

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_checkbox
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) =
            CheckboxViewHolder(
                layoutInflater,
                parent,
                adapter
            )
    }

    override val type = TYPE

}