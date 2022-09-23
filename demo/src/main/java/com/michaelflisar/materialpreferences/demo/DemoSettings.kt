package com.michaelflisar.materialpreferences.demo

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.michaelflisar.lumberjack.L
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.demo.activities.CustomSettingsActivity
import com.michaelflisar.materialpreferences.demo.settings.DemoSettingsModel
import com.michaelflisar.materialpreferences.demo.settings.TestEnum
import com.michaelflisar.materialpreferences.preferencescreen.*
import com.michaelflisar.materialpreferences.preferencescreen.activity.SettingsActivity
import com.michaelflisar.materialpreferences.preferencescreen.bool.checkbox
import com.michaelflisar.materialpreferences.preferencescreen.bool.switch
import com.michaelflisar.materialpreferences.preferencescreen.bool.switchCompact
import com.michaelflisar.materialpreferences.preferencescreen.choice.asChoiceListString
import com.michaelflisar.materialpreferences.preferencescreen.choice.multiChoice
import com.michaelflisar.materialpreferences.preferencescreen.choice.singleChoice
import com.michaelflisar.materialpreferences.preferencescreen.classes.Badge
import com.michaelflisar.materialpreferences.preferencescreen.classes.asBatch
import com.michaelflisar.materialpreferences.preferencescreen.classes.asIcon
import com.michaelflisar.materialpreferences.preferencescreen.color.color
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.Dependency
import com.michaelflisar.materialpreferences.preferencescreen.dependencies.asDependency
import com.michaelflisar.materialpreferences.preferencescreen.enums.NoIconVisibility
import com.michaelflisar.materialpreferences.preferencescreen.input.input
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.IScreenCreator
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider
import com.michaelflisar.text.asText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    object ScreenCreator : IScreenCreator {
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
                title = "Test App Style".asText()
            }

            switch(DemoSettingsModel.darkTheme) {
                title = "Dark Theme".asText()
                icon = R.drawable.ic_baseline_style_24.asIcon()
                summary = "This setting is applied to this demo app\n(enabled: %b)".asText()
                onChanged = {
                    L.d { "Dark Theme Settings Listener called: $it" }
                    //recreate()
                    AppCompatDelegate.setDefaultNightMode(if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            category {
                title = "Demos".asText()
            }

            // -----------------
            // 2) sub screens
            // -----------------

            subScreen {
                title = "Sub Screen Nesting".asText()
                summary = "Test nested screens (with any nesting hierarchy)".asText()
                icon = R.drawable.ic_baseline_double_arrow_24.asIcon()

                category {
                    title = "Sub Screens".asText()
                }

                subScreen {
                    title = "Sub Screen 1".asText()
                    icon = R.drawable.ic_baseline_double_arrow_24.asIcon()
                    category {
                        title = this@subScreen.title
                    }
                    button {
                        title = "Button 1.1".asText()
                        icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    }
                    button {
                        title = "Button 1.2".asText()
                        icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    }
                    subScreen {
                        title = "Sub Sub Screen 1".asText()
                        icon = R.drawable.ic_baseline_double_arrow_24.asIcon()
                        category {
                            title = this@subScreen.title
                        }
                        button {
                            title = "Button 1.3.1".asText()
                            icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                        }
                        button {
                            title = "Button 1.3.2".asText()
                            icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                        }
                    }
                }

                subScreen {
                    title = "Sub Screen 2".asText()
                    icon = R.drawable.ic_baseline_double_arrow_24.asIcon()
                    category {
                        title = this@subScreen.title
                    }
                    button {
                        title = "Button 2.1".asText()
                        icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    }
                    button {
                        title = "Button 2.2".asText()
                        icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    }
                    subScreen {
                        title = "Sub Sub Screen 2".asText()
                        icon = R.drawable.ic_baseline_double_arrow_24.asIcon()
                        category {
                            title = this@subScreen.title
                        }
                        button {
                            title = "Button 2.3.1".asText()
                            icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                        }
                        button {
                            title = "Button 2.3.2".asText()
                            icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                        }
                    }
                }
            }

            // -----------------
            // 3) sub screen booleans
            // -----------------

            subScreen {
                title = "Booleans".asText()
                icon = R.drawable.ic_baseline_check_box_24.asIcon()
                summary = "Switches / Checkboxes".asText()
                category {
                    title = "Switches (Material 3)".asText()
                }
                switch(DemoSettingsModel.enableFeature1) {
                    title = "Feature 1".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
                switch(DemoSettingsModel.enableFeature2) {
                    title = "Feature 2".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
                category {
                    title = "Switches (Material 2 - compact)".asText()
                }
                switchCompact(DemoSettingsModel.enableFeature3) {
                    title = "Feature 3".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
                switchCompact(DemoSettingsModel.enableFeature4) {
                    title = "Feature 4".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
                category {
                    title = "Checkboxes".asText()
                }
                checkbox(DemoSettingsModel.enableFeature5) {
                    title = "Feature 5".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
                checkbox(DemoSettingsModel.enableFeature6) {
                    title = "Feature 6".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                }
            }

            // -----------------
            // 4) sub screen inputs (text + numbers)
            // -----------------

            subScreen {
                title = "Inputs".asText()
                icon = R.drawable.ic_baseline_text_fields_24.asIcon()
                summary = "Works with int/long/float/double and string preferences!".asText()
                category {
                    title = "Inputs".asText()
                }
                input(DemoSettingsModel.text1) {
                    title = "Input 1".asText()
                    icon = R.drawable.ic_baseline_text_fields_24.asIcon()
                    summary = "Insert ANY text\n(value = %s)".asText()
                    hint = "Insert a value...".asText()
                }
                input(DemoSettingsModel.text2) {
                    title = "Input 2".asText()
                    icon = R.drawable.ic_baseline_text_fields_24.asIcon()
                    summary = "Insert NUMBERS only\n(value = %s)".asText()
                    textInputType = InputType.TYPE_CLASS_NUMBER
                    hint = "Insert a value...".asText()
                }
                input(DemoSettingsModel.number1) {
                    title = "Euros (Int)".asText()
                    icon = R.drawable.ic_baseline_attach_money_24.asIcon()
                    summary = "%d€".asText()
                    hint = "Insert an amount in $".asText()
                }
                input(DemoSettingsModel.number2) {
                    title = "Dollars (Int + Range [0, 100000] + show as bottom sheet)".asText()
                    icon = R.drawable.ic_baseline_attach_money_24.asIcon()
                    summary = "%d$".asText()
                    hint = "Insert an amount in $".asText()
                    min = 0
                    max = 100000
                    bottomSheet = true
                }
                category {
                    title = "Number Types".asText()
                }
                input(DemoSettingsModel.numberFloat) {
                    title = "Float".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                    summary = "%s".asText()
                    hint = "Float".asText()
                }
                input(DemoSettingsModel.numberDouble) {
                    title = "Double".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                    summary = "%s".asText()
                    hint = "Double".asText()
                }
                input(DemoSettingsModel.numberLong) {
                    title = "Long".asText()
                    icon = R.drawable.ic_baseline_keyboard_arrow_right_24.asIcon()
                    summary = "%d".asText()
                    hint = "Long".asText()
                }
            }

            subScreen {
                title = "Seekbars".asText()
                icon = R.drawable.ic_baseline_numbers_24.asIcon()
                summary = "Works with int/long/float/double preferences!".asText()
                category {
                    title = "Seekbars".asText()
                }
                slider(DemoSettingsModel.numberSeekbarInt) {
                    title = "Seekbar Int".asText()
                    icon = R.drawable.ic_baseline_numbers_24.asIcon()
                    summary = "Selected: %d".asText()
                    badge = Badge.Text("0 - 100".asText(), Color.BLUE)
                    min = 0
                    max = 100
                }
                slider(DemoSettingsModel.numberSeekbarLong) {
                    title = "Seekbar Long".asText()
                    icon = R.drawable.ic_baseline_numbers_24.asIcon()
                    summary = "Selected: %d".asText()
                    min = 0
                    max = 100
                }
                slider(DemoSettingsModel.numberSeekbarFloat) {
                    title = "Seekbar Float".asText()
                    icon = R.drawable.ic_baseline_numbers_24.asIcon()
                    summary = "Selected: %f".asText()
                    min = 0f
                    max = 100f
                    stepSize = 0.5f
                    labelFormatter = { String.format("%.1f", it)}
                }
                slider(DemoSettingsModel.numberSeekbarDouble) {
                    title = "Seekbar Double".asText()
                    icon = R.drawable.ic_baseline_numbers_24.asIcon()
                    summary = "Selected: %f".asText()
                    min = 0.0
                    max = 100.0
                    stepSize = 0.5
                    labelFormatter = { String.format("%.1f", it)}
                }
            }

            // -----------------
            // 5) sub screen color
            // -----------------

            subScreen {
                title = "Colors".asText()
                icon = R.drawable.ic_baseline_color_lens_24.asIcon()
                summary = "Alpha support is optional".asText()
                category {
                    title = "Colors".asText()
                }
                color(DemoSettingsModel.color1) {
                    title = "Color 1".asText()
                    icon = R.drawable.ic_baseline_color_lens_24.asIcon()
                    summary = "This color SUPPORTS alpha values".asText()
                }
                color(DemoSettingsModel.color2) {
                    title = "Color 2".asText()
                    icon = R.drawable.ic_baseline_color_lens_24.asIcon()
                    summary = "This color DOES NOT SUPPORT alpha values".asText()
                    supportsAlpha = false
                }
                color(DemoSettingsModel.color3) {
                    title = "Color 3".asText()
                    icon = R.drawable.ic_baseline_color_lens_24.asIcon()
                    summary = "This color also has an alpha value by default".asText()
                }
            }

            // -----------------
            // 6) sub screen buttons
            // -----------------

            var buttonIconIndex = 0

            subScreen {
                title = "Buttons".asText()
                icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                summary = "Show messages / dialogs / ...".asText()
                category {
                    title = "Buttons".asText()
                }
                button {
                    title = "Button 1".asText()
                    icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    onClick = {
                        activity.showMessage("Button 1 clicked!")
                        false
                    }
                }
                button {
                    title = "Button 2".asText()
                    icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    onClick = {
                        activity.showMessage("Button 2 clicked!")
                        false
                    }
                }
                button {
                    title = "Button 3".asText()
                    summary = "Button with a value".asText()
                    icon = arrayOf(
                        R.drawable.ic_baseline_touch_app_24.asIcon(),
                        R.drawable.ic_baseline_attach_money_24.asIcon(),
                        R.drawable.ic_baseline_check_box_24.asIcon(),
                    )[buttonIconIndex % 3]
                    onClick = {
                        buttonIconIndex++
                        icon = arrayOf(
                            R.drawable.ic_baseline_touch_app_24.asIcon(),
                            R.drawable.ic_baseline_attach_money_24.asIcon(),
                            R.drawable.ic_baseline_check_box_24.asIcon(),
                        )[buttonIconIndex % 3]
                        summary = "Button with a value (clicked: $buttonIconIndex)".asText()
                        // this items needs to be updated, as we have just changed its icon
                        this@apply.notifyItemChanged(this)
                    }
                }
            }

            // -----------------
            // 7) sub screen dependencies
            // -----------------

            subScreen {
                title = "Dependencies".asText()
                icon = R.drawable.ic_baseline_supervisor_account_24.asIcon()
                summary = "Enable/Show settings based on another setting".asText()

                category {
                    title = "Dependencies - Enabled/Disabled".asText()
                }
                switch(DemoSettingsModel.enableChild) {
                    title = "Enable children".asText()
                    icon = R.drawable.ic_baseline_supervisor_account_24.asIcon()
                    summary = "Enables children below".asText()
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
                        title = "Child ${index + 1}".asText()
                        icon = R.drawable.ic_baseline_person_24.asIcon()
                        enabledDependsOn = DemoSettingsModel.enableChild.asDependency()
                    }
                }

                category {
                    title = "Dependencies - Show/Hide".asText()
                }
                switch(DemoSettingsModel.showChild) {
                    title = "Show children".asText()
                    icon = R.drawable.ic_baseline_supervisor_account_24.asIcon()
                    summary = "Show children below".asText()
                    onChanged = {
                        activity.showMessage("Show children changed: $it")
                    }
                }
                listOf(
                    DemoSettingsModel.showChildName1,
                    DemoSettingsModel.showChildName2,
                    DemoSettingsModel.showChildName3
                ).forEachIndexed { index, setting ->
                    input(setting) {
                        title = "Child ${index + 1}".asText()
                        icon = R.drawable.ic_baseline_person_24.asIcon()
                        visibilityDependsOn = DemoSettingsModel.showChild.asDependency()
                    }
                }

                category {
                    title = "Custom Dependency".asText()
                }
                input(DemoSettingsModel.parentOfCustomDependency) {
                    title = "Parent".asText()
                    icon = R.drawable.ic_baseline_supervisor_account_24.asIcon()
                    summary =
                        "Must contain a string that is a valid number between [0, 100] to enable the next setting".asText()
                }
                button {
                    title = "Button with custom enable dependency on the setting above".asText()
                    icon = R.drawable.ic_baseline_person_24.asIcon()
                    onClick = {
                        activity.showMessage("Button clicked - parent must contain a string representing a number between [0, 100] now")
                        false
                    }
                    enabledDependsOn = object : Dependency<String> {
                        override val setting = DemoSettingsModel.parentOfCustomDependency
                        override suspend fun state(): Boolean {
                            val value = setting.flow.first().toIntOrNull()
                            return value != null && value >= 0 && value <= 100
                        }
                    }
                }
                button {
                    title = "Button with custom visibility dependency on the setting above".asText()
                    icon = R.drawable.ic_baseline_person_24.asIcon()
                    onClick = {
                        activity.showMessage("Button clicked - parent must contain a string representing a number between [0, 100] now")
                        false
                    }
                    visibilityDependsOn = object : Dependency<String> {
                        override val setting = DemoSettingsModel.parentOfCustomDependency
                        override suspend fun state(): Boolean {
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
                title = "Choices".asText()
                summary = "Single / Multi Choices".asText()
                icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                category {
                    title = "Choices".asText()
                }
                singleChoice(DemoSettingsModel.choiceSingle, demoChoices) {
                    title = "Single Choice".asText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                    //showCheckBoxes = true
                }
                multiChoice(DemoSettingsModel.choiceMulti, demoChoices) {
                    title = "Multi Choice".asText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                    allowEmptySelection = true
                }
                singleChoice(
                    DemoSettingsModel.testEnum,
                    TestEnum.values(),
                    { "Enum: ${it.name}" }) {
                    title = "Single Choice Enum".asText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                }
                singleChoice(DemoSettingsModel.choiceSingle3, demoChoices) {
                    title = "Single Choice + Close on click".asText()
                    icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                    closeOnSelect = true
                }
            }

            // -----------------
            // 9) various
            // -----------------

            subScreen {
                title = "Various".asText()
                icon = R.drawable.ic_baseline_format_list_bulleted_24.asIcon()
                button {
                    title = "Button".asText()
                    summary = "Custom Badge Colors".asText()
                    icon = R.drawable.ic_baseline_touch_app_24.asIcon()
                    badge = Badge.Text("Test".asText(), Color.GREEN)
                }
                button {
                    title = "Button".asText()
                    summary = "No Icon Visibility INVISIBLE".asText()
                    noIconVisibility = NoIconVisibility.Invisible
                }
                button {
                    title = "Button".asText()
                    summary = "No Icon Visibility GONE".asText()
                    noIconVisibility = NoIconVisibility.Gone
                }
            }

            // -----------------
            // .) preferences inside root screen
            // -----------------

            category {
                title = "Root Level Preferences".asText()
            }

            switch(DemoSettingsModel.proFeature1) {
                title = "Pro Feature 1".asText()
                icon = R.drawable.ic_baseline_phone_android_24.asIcon()
                summary = "Enable a fancy pro feature".asText()
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
                title = "Pro Feature 2".asText()
                icon = R.drawable.ic_baseline_phone_android_24.asIcon()
                summary = "This setting is always disabled".asText()
                badge = "PRO".asBatch()
                enabled = false
            }
        }
    }
}