package com.michaelflisar.materialpreferences.preferencescreen.color

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.BasePreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class ColorPreference(
        override val setting: StorageSetting<Int>
) : BasePreferenceItem(), PreferenceItem.PreferenceWithData<Int> {

    override var canChange: (value: Int) -> Boolean = { true }
    override var onChanged: ((value: Int) -> Unit)? = null
    var supportsAlpha: Boolean = true
    var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_color
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                ColorViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}