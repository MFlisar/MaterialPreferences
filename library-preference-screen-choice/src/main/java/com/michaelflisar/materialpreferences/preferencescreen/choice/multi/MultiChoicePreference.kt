package com.michaelflisar.materialpreferences.preferencescreen.choice.multi

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

class MultiChoicePreference(
        override val setting: StorageSetting<Set<Int>>,
        override var title: Text = Text.Empty,
        override var icon: Icon = Icon.Empty,
        override var summary: Text = "%s".asText(),
        override var badge: Badge = Badge.Empty,
        override var enabled: Boolean = true,
        override var dependsOn: Dependency<*>? = null,
        override var canChange: (value: Set<Int>) -> Boolean = { true },
        override var onChanged: ((value: Set<Int>) -> Unit)? = null,
        var choices: List<ChoiceItem> = emptyList(),
        var allowEmptySelection: Boolean = true,
        var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet
) : PreferenceItem.Preference, PreferenceItem.PreferenceWithData<Set<Int>> {

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_choice_multi
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                MultiChoiceViewHolder(layoutInflater, parent)
    }

    override val type = TYPE

}