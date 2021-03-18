package com.michaelflisar.materialpreferences.preferencescreen.interfaces

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.text.Text

interface PreferenceItem {

    val type: Int

    var title: Text

    var icon: Icon
    var badge: Badge
    var summary: Text

    interface SubScreen : PreferenceItem

    interface Preference : PreferenceItem {
        var enabled: Boolean
        var dependsOn: Dependency<*>?
    }

    interface ClickablePreference {
        var onClick: (() -> Unit)?
    }

    interface PreferenceWithData<T : Any> {
        val setting: StorageSetting<T>
        var canChange: ((value: T) -> Boolean)
        var onChanged: ((value: T) -> Unit)?
    }
}