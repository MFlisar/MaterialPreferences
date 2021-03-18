package com.michaelflisar.materialpreferences.preferencescreen.preferences

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.R
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.SwitchViewHolder
import com.michaelflisar.text.Text

class SwitchPreference(
        override var setting: StorageSetting<Boolean>,
        override var title: Text = Text.Empty,
        override var icon: Icon = Icon.Empty,
        override var summary: Text = Text.Empty,
        override var badge: Badge = Badge.Empty,
        override var enabled: Boolean = true,
        override var dependsOn: Dependency<*>? = null,
        override var canChange: (value: Boolean) -> Boolean = { true },
        override var onChanged: ((value: Boolean) -> Unit)? = null
) : PreferenceItem.Preference, PreferenceItem.PreferenceWithData<Boolean> {

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_switch
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                SwitchViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE

}