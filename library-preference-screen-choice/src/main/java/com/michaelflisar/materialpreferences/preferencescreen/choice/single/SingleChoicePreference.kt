package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.choice.ChoiceItem
import com.michaelflisar.materialpreferences.preferencescreen.choice.R
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.BasePreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class SingleChoicePreference(
        override val setting: StorageSetting<Int>
) : BasePreferenceItem(), PreferenceItem.PreferenceWithData<Int> {

    override var canChange: (value: Int) -> Boolean = { true }
    override var onChanged: ((value: Int) -> Unit)? = null
    var choices: List<ChoiceItem> = emptyList()
    var showCheckBoxes: Boolean = false
    var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_choice_single
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                SingleChoiceViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}