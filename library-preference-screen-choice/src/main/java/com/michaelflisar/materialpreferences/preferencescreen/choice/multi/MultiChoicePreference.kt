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
import com.michaelflisar.materialpreferences.preferencescreen.enums.NoIconVisibility
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.BasePreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.text.Text
import com.michaelflisar.text.asText

class MultiChoicePreference(
        override val setting: StorageSetting<Set<Int>>,
        internal val choices: List<ChoiceItem>
) : BasePreferenceItem(), PreferenceItem.PreferenceWithData<Set<Int>> {

    override var summary: Text = "%s".asText()
    override var canChange: (value: Set<Int>) -> Boolean = { true }
    override var onChanged: ((value: Set<Int>) -> Unit)? = null
    var allowEmptySelection: Boolean = true
    var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_choice_multi
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                MultiChoiceViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE

}