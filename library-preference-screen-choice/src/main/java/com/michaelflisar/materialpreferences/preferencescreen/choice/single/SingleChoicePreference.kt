package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.choice.ChoiceItem
import com.michaelflisar.materialpreferences.preferencescreen.choice.R
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.text.Text
import com.michaelflisar.text.asText

class SingleChoicePreference(
        override val setting: StorageSetting<Int>,
        override var title: Text = Text.Empty,
        override var icon: Icon = Icon.Empty,
        override var summary: Text = "%s".asText(),
        override var badge: Badge = Badge.Empty,
        override var enabled: Boolean = true,
        override var dependsOn: Dependency<*>? = null,
        override var canChange: (value: Int) -> Boolean = { true },
        override var onChanged: ((value: Int) -> Unit)? = null,
        var choices: List<ChoiceItem> = emptyList(),
        var showCheckBoxes: Boolean = false,
        var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet
) : PreferenceItem.Preference, PreferenceItem.PreferenceWithData<Int> {

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_choice_single
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                SingleChoiceViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}