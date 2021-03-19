package com.michaelflisar.materialpreferences.preferencescreen

import android.os.Bundle
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.PreferenceItem
import com.michaelflisar.materialpreferences.preferencescreen.preferences.*
import com.michaelflisar.text.Text
import com.michaelflisar.text.asText

@DslMarker
@Retention(AnnotationRetention.SOURCE)
annotation class PreferenceMarker

// -------------
// DSL
// -------------

inline fun screen(block: Screen.Builder.() -> Unit) =
        Screen.Builder().apply(block).build()

inline fun Screen.Appendable.subScreen(block: Screen.SubBuilder.() -> Unit) =
        Screen.SubBuilder().apply(block).build().also(::addPreferenceItem)

inline fun Screen.Appendable.category(block: CategoryHeaderPreference.() -> Unit) =
        CategoryHeaderPreference().apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.switch(setting: StorageSetting<Boolean>, block: SwitchPreference.() -> Unit) =
        SwitchPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.checkbox(setting: StorageSetting<Boolean>, block: CheckboxPreference.() -> Unit) =
        CheckboxPreference(setting).apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.button(block: ButtonPreference.() -> Unit) = ButtonPreference().apply(block).also(::addPreferenceItem)

// -------------
// BUILDER
// -------------

class Screen {

    @PreferenceMarker
    open class BaseBaseBuilder {

        var title: Text = "".asText()

    }

    @PreferenceMarker
    open class BaseBuilder : BaseBaseBuilder(), Appendable {

        var icon: Icon = Icon.Empty
        var summary: Text = Text.Empty
        var badge: Badge = Badge.Empty

        protected val preferences: MutableList<PreferenceItem> = ArrayList()

        override fun addPreferenceItem(p: PreferenceItem) {
            preferences.add(p)
        }
    }

    @PreferenceMarker
    class Builder : Appendable {

        private val preferences: MutableList<PreferenceItem> = ArrayList()

        var state: Bundle? = null
        var onScreenChanged: ScreenChangedListener? = null

        fun build(): PreferenceScreen {
            return PreferenceScreen(preferences, state, onScreenChanged)
        }

        override fun addPreferenceItem(p: PreferenceItem) {
            preferences.add(p)
        }
    }

    @PreferenceMarker
    class SubBuilder : BaseBuilder() {
        fun build(): SubScreen {
            return SubScreen(title, preferences).apply {
                icon = this@SubBuilder.icon
                badge = this@SubBuilder.badge
                summary = this@SubBuilder.summary
            }
        }
    }

    fun interface Appendable {
        fun addPreferenceItem(p: PreferenceItem)

        operator fun plusAssign(p: PreferenceItem) {
            addPreferenceItem(p)
        }

        operator fun <T : PreferenceItem> T.unaryPlus(): T {
            addPreferenceItem(this)
            return this
        }
    }
}
