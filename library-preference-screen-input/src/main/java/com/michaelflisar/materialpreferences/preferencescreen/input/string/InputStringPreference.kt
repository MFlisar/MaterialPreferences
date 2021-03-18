package com.michaelflisar.materialpreferences.preferencescreen.input.string

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.materialpreferences.preferencescreen.input.R
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.text.Text
import com.michaelflisar.text.asText

class InputStringPreference(
        override val setting: StorageSetting<String>,
        override var title: Text = Text.Empty,
        override var icon: Icon = Icon.Empty,
        override var summary: Text = "%s".asText(),
        override var badge: Badge = Badge.Empty,
        override var enabled: Boolean = true,
        override var dependsOn: Dependency<*>? = null,
        var textInputType: Int? = null,
        var hint: Text = "".asText(),
        var allowEmpty: Boolean = true,
        var bottomSheet: Boolean = PreferenceScreenConfig.bottomSheet,
        override var canChange: (value: String) -> Boolean = { true },
        override var onChanged: ((value: String) -> Unit)? = null
) : PreferenceItem.Preference, PreferenceItem.PreferenceWithData<String> {

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_input_string
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                InputStringViewHolder(layoutInflater, parent, adapter)
    }

    override val type = TYPE
}