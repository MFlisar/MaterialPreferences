package com.michaelflisar.materialpreferences.preferencescreen

import android.os.Bundle
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
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

inline fun Screen.Appendable.button(block: ButtonPreference.() -> Unit) = ButtonPreference().apply(block).also(::addPreferenceItem)

inline fun Screen.Appendable.info(block: InfoPreference.() -> Unit) = InfoPreference().apply(block).also(::addPreferenceItem)

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
        var enabledDependsOn: Dependency<*>? = null
        var visibilityDependsOn: Dependency<*>? = null

        protected val preferences: MutableList<PreferenceItem> = ArrayList()

        override fun addPreferenceItem(p: PreferenceItem) {
            preferences.add(p)
        }
    }

    @PreferenceMarker
    class Builder : Appendable {

        private val preferences: MutableList<PreferenceItem> = ArrayList()
        private var screen: PreferenceScreen? = null

        var state: Bundle? = null
        var onScreenChanged: ScreenChangedListener? = null

        fun notifyItemChanged(item: PreferenceItem) {
            screen?.notifyItemChanged(item)
        }

        fun notifyItemChanged(item: StorageSetting<*>) {
            screen?.notifyItemChanged(item)
        }

        fun build(): PreferenceScreen {
            screen = PreferenceScreen(preferences, state, onScreenChanged)
            return screen!!
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
                enabledDependsOn = this@SubBuilder.enabledDependsOn
                visibilityDependsOn = this@SubBuilder.visibilityDependsOn
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
