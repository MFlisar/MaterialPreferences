package com.michaelflisar.materialpreferences.preferencescreen.preferences

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.R
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.SimpleViewHolder

class ButtonPreference : BasePreferenceItem(), PreferenceItem.ClickablePreference {

    override var onClick: (() -> Unit)? = null

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_button
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                SimpleViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}