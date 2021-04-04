package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.choice.ChoiceItem
import com.michaelflisar.materialpreferences.preferencescreen.choice.R
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class SingleChoicePreferenceInt(
        setting: StorageSetting<Int>,
        private val choices: List<ChoiceItem>
) : SingleChoicePreference<Int>(setting) {

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_choice_single
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                SingleChoiceViewHolder<Int>(layoutInflater, parent, adapter)
    }

    override val type = TYPE

    override fun getChoiceDisplayValue(context: Context, item: Int): String = choices[item].label.get(context)
    override fun getChoiceLabels(context: Context) = choices.map { it.label.get(context) }
    override fun getChoiceValue(index: Int): Int = choices[index].id
}