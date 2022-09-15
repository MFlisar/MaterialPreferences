package com.michaelflisar.materialpreferences.preferencescreen.interfaces

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.materialpreferences.preferencescreen.enums.NoIconVisibility
import com.michaelflisar.text.Text

interface PreferenceItem {

    val type: Int
    var title: Text

    interface SubScreen : PreferenceItem, PreferenceWithIcon, PreferenceWithSummary,
        PreferenceWithBadge, PreferenceWithDependencies

    interface Category : PreferenceItem, PreferenceWithDependencies
    interface Preference : PreferenceItem, PreferenceWithIcon, PreferenceWithSummary,
        PreferenceWithBadge {
        var enabled: Boolean
        var visible: Boolean
        var enabledDependsOn: Dependency<*>?
        var visibilityDependsOn: Dependency<*>?
    }

    interface ClickablePreference : Preference {
        var onClick: (() -> Unit)?
    }

    interface PreferenceWithData<T : Any> : Preference {
        val setting: StorageSetting<T>
        var canChange: ((value: T) -> Boolean)
        var onChanged: ((value: T) -> Unit)?
    }

    interface PreferenceDialog<T : Any> : PreferenceWithData<T> {
        var bottomSheet: Boolean
    }

    // "internal" interface

    interface PreferenceWithDependencies: PreferenceItem {
        var enabledDependsOn: Dependency<*>?
        var visibilityDependsOn: Dependency<*>?
    }

    interface PreferenceWithIcon : PreferenceItem {
        var icon: Icon
        var noIconVisibility: NoIconVisibility
    }

    interface PreferenceWithBadge : PreferenceItem {
        var badge: Badge
    }

    interface PreferenceWithSummary : PreferenceItem {
        var summary: Text
    }
}