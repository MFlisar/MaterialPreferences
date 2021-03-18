package com.michaelflisar.materialpreferences.preferencescreen.preferences

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.R
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.SimpleViewHolder
import com.michaelflisar.text.Text

class ButtonPreference(
        override var title: Text = Text.Empty,
        override var icon: Icon = Icon.Empty,
        override var summary: Text = Text.Empty,
        override var badge: Badge = Badge.Empty,
        override var enabled: Boolean = true,
        override var dependsOn: Dependency<*>? = null,
        override var onClick: (() -> Unit)? = null
) : PreferenceItem.Preference, PreferenceItem.ClickablePreference {

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_button
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                SimpleViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}