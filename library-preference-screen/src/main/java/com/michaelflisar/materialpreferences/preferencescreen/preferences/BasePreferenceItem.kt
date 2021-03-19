package com.michaelflisar.materialpreferences.preferencescreen.preferences

import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreenConfig
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.text.Text

abstract class BasePreferenceItem : PreferenceItem.Preference, PreferenceItem.PreferenceWithIcon, PreferenceItem.PreferenceWithBadge, PreferenceItem.PreferenceWithSummary {
    override var title: Text = Text.Empty
    override var icon: Icon = Icon.Empty
    override var noIconVisibility = PreferenceScreenConfig.noIconVisibility
    override var badge: Badge = Badge.Empty
    override var summary: Text = Text.Empty
    override var enabled: Boolean = true
    override var dependsOn: Dependency<*>? = null
}