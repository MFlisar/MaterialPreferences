package com.michaelflisar.materialpreferences.preferencescreen.preferences

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.R
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.enums.NoIconVisibility
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.SimpleViewHolder
import com.michaelflisar.text.Text

class SubScreen(
        override var title: Text,
        val preferences: List<PreferenceItem>
) : PreferenceItem.SubScreen {

    override var icon: Icon = Icon.Empty
    override var noIconVisibility: NoIconVisibility = PreferenceScreenConfig.noIconVisibility
    override var summary: Text = Text.Empty
    override var badge: Badge = Badge.Empty

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_screen
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                SimpleViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}