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
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.viewholders.CategoryViewHolder
import com.michaelflisar.text.Text

class CategoryHeaderPreference(
        override var title: Text = Text.Empty,
        override var enabled: Boolean = true
) : PreferenceItem.Preference {

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_category
        override fun createViewHolder(adapter: PreferenceAdapter, layoutInflater: LayoutInflater, parent: ViewGroup) =
                CategoryViewHolder(layoutInflater, parent)
    }

    override val type = TYPE

    override var icon: Icon = Icon.Empty
    override var badge: Badge = Badge.Empty
    override var summary: Text = Text.Empty

    override var dependsOn: Dependency<*>? = null
}