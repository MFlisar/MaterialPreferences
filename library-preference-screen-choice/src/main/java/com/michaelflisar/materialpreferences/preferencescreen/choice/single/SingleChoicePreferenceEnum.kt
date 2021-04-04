package com.michaelflisar.materialpreferences.preferencescreen.choice.single

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.choice.R
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter

class SingleChoicePreferenceEnum<E : Enum<*>>(
        setting: StorageSetting<E>,
        private val values: Array<E>,
        private val labelProvider: ((E) -> String) = { it.name }
) : SingleChoicePreference<E>(setting) {

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_choice_single_enum
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                SingleChoiceViewHolder<Enum<*>>(layoutInflater, parent, adapter)
    }

    override val type = TYPE

    override fun getChoiceDisplayValue(context: Context, item: E): String = item.let { labelProvider.invoke(it) }
    override fun getChoiceLabels(context: Context): List<String> = values.map { labelProvider.invoke(it) }
    override fun getChoiceValue(index: Int): E = values[index]
}