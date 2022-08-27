package com.michaelflisar.materialpreferences.demo

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.michaelflisar.lumberjack.L
import com.michaelflisar.materialpreferences.demo.activities.CustomSettingsActivity
import com.michaelflisar.materialpreferences.demo.settings.DemoSettingsModel
import com.michaelflisar.materialpreferences.demo.settings.TestEnum
import com.michaelflisar.materialpreferences.preferencescreen.*
import com.michaelflisar.materialpreferences.preferencescreen.activity.SettingsActivity
import com.michaelflisar.materialpreferences.preferencescreen.choice.asChoiceListString
import com.michaelflisar.materialpreferences.preferencescreen.choice.multiChoice
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreference
import com.michaelflisar.materialpreferences.preferencescreen.choice.singleChoice
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.asBatch
import com.michaelflisar.materialpreferences.preferencescreen.classes.asIcon
import com.michaelflisar.materialpreferences.preferencescreen.color.color
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.asDependency
import com.michaelflisar.materialpreferences.preferencescreen.enums.NoIconVisibility
import com.michaelflisar.materialpreferences.preferencescreen.input.input
import com.michaelflisar.text.toText
import kotlinx.coroutines.flow.first
import kotlinx.parcelize.Parcelize

object DemoSettings {

    fun showDefaultSettingsActivity(activity: AppCompatActivity) {
        SettingsActivity.start(activity, ScreenCreator)
    }

    fun showCustomSettingsActivity(context: Context) {
        CustomSettingsActivity.start(context)
    }

    /*
     * use an object here to make sure that the class does not break the parcelable limit!
     * or use a custom activity...
     */
    @Parcelize
    object ScreenCreator : SettingsActivity.IScreenCreator {
        override fun createScreen(
            activity: AppCompatActivity,
            savedInstanceState: Bundle?,
            updateTitle: (title: String) -> Unit
        ): PreferenceScreen {
            return screen {

                // set up screen
                state = savedInstanceState
                onScreenChanged = { subScreenStack, stateRestored ->
                    val breadcrumbs =
                        subScreenStack.joinToString(" > ") { it.title.get(activity) }
                    L.d { "Preference Screen - level = ${subScreenStack.size} | $breadcrumbs | restored: $stateRestored" }
                    updateTitle(breadcrumbs)
                }

                // init theme - not necessary, its automatically derived from parent activity
                //AppCompatDelegate.setDefaultNightMode(if (DemoSettingsModel.darkTheme.value) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

                // -----------------
                // create settings screen
                // -----------------

                createSettingsScreen(this, activity)
            }

        }
    }

    // --------------------
    // Helper functions
    // --------------------

    fun createSettingsScreen(builder: Screen.Builder, activity: AppCompatActivity) {
        builder.apply {


            // -----------------
            // 1) test app settings (root level)
            // -----------------

            category {
                title = "Test App Style".toText()
            }

            switch(DemoSettingsModel.darkTheme) {
                title = "Dark Theme".toText()
                icon = R.drawable.ic_baseline_style_24.asIcon()
                summary = "This setting is applied to this demo app\n(enabled: %b)".toText()
                onChanged = {
                    L.d { "Dark Theme Settings Listener called: $it" }
                    //recreate()
                    AppCompatDelegate.setDefaultNightMode(if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            category {
                title = "Demos".toText()
            }

            // -----------------
            // 2) sub screens
            // -----------------

            subScreen {
                title = "Sub Screen Nesting".toText()
                summary = "Test nested screens (with any nesting hierarchy)".toText()
                icon = R.drawable.ic_baseline_double_arrow_24.asIcon()

                category {
                    title = "Sub Screens".toText()
                }

                subScreen {
                    title = "Sub Screen 1".toText()
                    icon = R.drawable.ic_baseline_double_arrow_24.asIcon()
                    category {
                        title = this@subScreen.title
                    }
                    button {
                        title = "Button 1.1".toText()
                        icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    }
                    button {
                        title = "Button 1.2".toText()
                        icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    }
                    subScreen {
                        title = "Sub Sub Screen 1".toText()
                        icon = R.drawable.ic_baseline_double_arrow_24.asIcon()
                        category {
                            title = this@subScreen.title
                        }
                        button {
                            title = "Button 1.3.1".toText()
                            icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                        }
                        button {
                            title = "Button 1.3.2".toText()
                            icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                        }
                    }
                }

                subScreen {
                    title = "Sub Screen 2".toText()
                    icon = R.drawable.ic_baseline_double_arrow_24.asIcon()
                    category {
                        title = this@subScreen.title
                    }
                    button {
                        title = "Button 2.1".toText()
                        icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    }
                    button {
                        title = "Button 2.2".toText()
                        icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    }
                    subScreen {
                        title = "Sub Sub Screen 2".toText()
                        icon = R.drawable.ic_baseline_double_arrow_24.asIcon()
                        category {
                            title = this@subScreen.title
                        }
                        button {
                            title = "Button 2.3.1".toText()
                            icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                        }
                        button {
                            title = "Button 2.3.2".toText()
                            icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                        }
                    }
                }
            }

            // -----------------
            // 3) sub screen booleans
            // -----------------

            subScreen {
                title = "Booleans".toText()
                icon = R.drawable.ic_baseline_check_box_24.asIcon()
                summary = "Switches / Checkboxes".toText()
                category {
                    title = "Switches".toText()
                }
                switch(DemoSettingsModel.enableFeature1) {
                    title = "Feature 1".toText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
                switch(DemoSettingsModel.enableFeature2) {
                    title = "Feature 2".toText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
                category {
                    title = "Checkboxes".toText()
                }
                checkbox(DemoSettingsModel.enableFeature3) {
                    title = "Feature 3".toText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
                checkbox(DemoSettingsModel.enableFeature4) {
                    title = "Feature 4".toText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
            }

            // -----------------
            // 4) sub screen inputs (text, int)
            // -----------------

            subScreen {
                title = "Inputs".toText()
                icon = R.drawable.ic_baseline_text_fields_24.asIcon()
                summary = "Texts / Numbers".toText()
                category {
                    title = "Inputs".toText()
                    summary = "Worked with int and string preferences!".toText()
                }
                input(DemoSettingsModel.text1) {
                    title = "Input 1".toText()
                    icon = R.drawable.ic_baseline_text_fields_24.asIcon()
                    summary = "Insert ANY text".toText()
                    hint = "Insert a value...".toText()
                }
                input(DemoSettingsModel.text2) {
                    title = "Input 2".toText()
                    icon = R.drawable.ic_baseline_text_fields_24.asIcon()
                    summary = "Insert NUMBERS only\n(value = %s)".toText()
                    textInputType = InputType.TYPE_CLASS_NUMBER
                    hint = "Insert a value...".toText()
                }
                input(DemoSettingsModel.number1) {
                    title = "Euros".toText()
                    icon = R.drawable.ic_baseline_attach_money_24.asIcon()
                    summary = "%d€".toText()
                    hint = "Insert an amount in $".toText()
                }
                input(DemoSettingsModel.number2) {
                    title = "Dollars".toText()
                    icon = R.drawable.ic_baseline_attach_money_24.asIcon()
                    summary = "%d$".toText()
                    hint = "Insert an amount in €".toText()
                }
            }

            // -----------------
            // 5) sub screen color
            // -----------------

            subScreen {
                title = "Colors".toText()
                icon = R.drawable.ic_baseline_color_lens_24.asIcon()
                category {
                    title = "Colors".toText()
                }
                color(DemoSettingsModel.color1) {
                    title = "Color 1".toText()
                    icon = R.drawable.ic_baseline_color_lens_24.asIcon()
                    summary = "This color SUPPORTS alpha values".toText()
                }
                color(DemoSettingsModel.color2) {
                    title = "Color 2".toText()
                    icon = R.drawable.ic_baseline_color_lens_24.asIcon()
                    summary = "This color DOES NOT SUPPORT alpha values".toText()
                    supportsAlpha = false
                }
                color(DemoSettingsModel.color3) {
                    title = "Color 3".toText()
                    icon = R.drawable.ic_baseline_color_lens_24.asIcon()
                    summary = "This color also has an alpha value by default".toText()
                }
            }

            // -----------------
            // 6) sub screen buttons
            // -----------------

            subScreen {
                title = "Buttons".toText()
                icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                summary = "Show messages / dialogs / ...".toText()
                category {
                    title = "Buttons".toText()
                }
                button {
                    title = "Button 1".toText()
                    icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    onClick = {
                        activity.showMessage("Button 1 clicked!")
                    }
                }
                button {
                    title = "Button 2".toText()
                    icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    onClick = {
                        activity.showMessage("Button 2 clicked!")
                    }
                }
                button {
                    title = "Button 3".toText()
                    icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    onClick = {
                        activity.showMessage("Button 3 clicked!")
                    }
                }
            }

            // -----------------
            // 7) sub screen dependencies
            // -----------------

            subScreen {
                title = "Dependencies".toText()
                icon = R.drawable.ic_baseline_supervisor_account_24.asIcon()
                summary = "Enable settings based on another setting".toText()
                category {
                    title = this@subScreen.title
                }
                switch(DemoSettingsModel.enableChild) {
                    title = "Enable children".toText()
                    icon = R.drawable.ic_baseline_supervisor_account_24.asIcon()
                    summary = "Enables children below".toText()
                    onChanged = {
                        activity.showMessage("Enable children changed: $it")
                    }
                }
                listOf(
                    DemoSettingsModel.childName1,
                    DemoSettingsModel.childName2,
                    DemoSettingsModel.childName3
                ).forEachIndexed { index, setting ->
                    input(setting) {
                        title = "Child ${index + 1}".toText()
                        icon = R.drawable.ic_baseline_person_24.asIcon()
                        dependsOn = DemoSettingsModel.enableChild.asDependency()
                    }
                }
                category {
                    title = "Custom Dependency".toText()
                }
                input(DemoSettingsModel.parentOfCustomDependency) {
                    title = "Parent".toText()
                    icon = R.drawable.ic_baseline_supervisor_account_24.asIcon()
                    summary =
                        "Must contain a string that is a valid number between [0, 100] to enable the next setting".toText()
                }
                button {
                    title = "Button with custom dependency on the setting above".toText()
                    icon = R.drawable.ic_baseline_person_24.asIcon()
                    onClick = {
                        activity.showMessage("Button clicked - parent must contain a string representing a number between [0, 100] now")
                    }
                    dependsOn = object : Dependency<String> {
                        override val setting = DemoSettingsModel.parentOfCustomDependency
                        override suspend fun isEnabled(): Boolean {
                            val value = setting.flow.first().toIntOrNull()
                            return value != null && value >= 0 && value <= 100
                        }
                    }
                }
            }

            // -----------------
            // 8) sub screen choices
            // -----------------

            val demoChoices = listOf(
                "Value 1",
                "Value 2",
                "Value 3",
                "Value 4",
                "Value 5"
            ).asChoiceListString()

            subScreen {
                title = "Choices".toText()
                summary = "Single / Multi Choices".toText()
                icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                category {
                    title = "Choices".toText()
                }
                singleChoice(DemoSettingsModel.choiceSingle, demoChoices) {
                    title = "Single Choice".toText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                    //showCheckBoxes = true
                }
                multiChoice(DemoSettingsModel.choiceMulti, demoChoices) {
                    title = "Multi Choice".toText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                    allowEmptySelection = true
                }
                singleChoice(
                    DemoSettingsModel.testEnum,
                    TestEnum.values(),
                    { "Enum: ${it.name}" }) {
                    title = "Single Choice Enum".toText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                    //showCheckBoxes = true
                }
                category {
                    title = "Dialog Styles".toText()
                }
                singleChoice(DemoSettingsModel.choiceSingle2, demoChoices) {
                    title = "Single Choice - Checkbox".toText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                    displayType = SingleChoicePreference.DisplayType.Checkbox
                }
                singleChoice(DemoSettingsModel.choiceSingle3, demoChoices) {
                    title = "Single Choice - Highlighted".toText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                    displayType = SingleChoicePreference.DisplayType.Highlighted(true, true)
                }
            }

            // -----------------
            // 9) various
            // -----------------

            subScreen {
                title = "Various".toText()
                icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                button {
                    title = "Button".toText()
                    summary = "Custom Badge Colors".toText()
                    icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    badge = Badge.Text("Test".toText(), Color.GREEN)
                }
                button {
                    title = "Button".toText()
                    summary = "No Icon Visibility INVISIBLE".toText()
                    noIconVisibility = NoIconVisibility.Invisible
                }
                button {
                    title = "Button".toText()
                    summary = "No Icon Visibility GONE".toText()
                    noIconVisibility = NoIconVisibility.Gone
                }
            }

            // -----------------
            // .) preferences inside root screen
            // -----------------

            category {
                title = "Root Level Preferences".toText()
            }

            switch(DemoSettingsModel.proFeature1) {
                title = "Pro Feature 1".toText()
                icon = R.drawable.ic_baseline_phone_android_24.asIcon()
                summary = "Enable a fancy pro feature".toText()
                badge = "PRO".asBatch()
                canChange = {
                    // we can't change this settings, it's enabled but will only work in the pro version
                    activity.showMessage(
                        "Changing this setting is disabled via 'canChange' function!",
                        Toast.LENGTH_LONG
                    )
                    false
                }
                onChanged = {
                    activity.showMessage("Pro feature changed (this should never be called!): $it")
                }
            }

            switch(DemoSettingsModel.proFeature2) {
                title = "Pro Feature 2".toText()
                icon = R.drawable.ic_baseline_phone_android_24.asIcon()
                summary = "This setting is always disabled".toText()
                badge = "PRO".asBatch()
                enabled = false
            }
        }
    }
}