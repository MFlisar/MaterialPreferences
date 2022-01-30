package com.michaelflisar.materialpreferences.demo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.michaelflisar.lumberjack.L
import com.michaelflisar.materialpreferences.demo.DemoSettings
import com.michaelflisar.materialpreferences.demo.settings.DemoSettingsModel
import com.michaelflisar.materialpreferences.preferencescreen.*
import com.michaelflisar.materialpreferences.preferencescreen.databinding.PreferenceActivitySettingsBinding
import com.michaelflisar.materialpreferences.preferencescreen.enums.NoIconVisibility

class CustomSettingsActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CustomSettingsActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    lateinit var binding: PreferenceActivitySettingsBinding
    lateinit var preferenceScreen: PreferenceScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(if (DemoSettingsModel.darkTheme.value) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        binding = PreferenceActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ---------------
        // set up settings
        // ---------------

        preferenceScreen = initSettings(savedInstanceState)
    }

    private fun initSettings(savedInstanceState: Bundle?): PreferenceScreen {

        // global settings to avoid
        // INFO:
        // some global settings can be overwritten per preference (e.g. bottomSheet yes/no)
        // other global settings can only be changed globally

        // following is optional!
        PreferenceScreenConfig.apply {
            bottomSheet = false                             // default: false
            maxLinesTitle = 1                               // default: 1
            maxLinesSummary = 3                             // default: 3
            noIconVisibility = NoIconVisibility.Invisible   // default: Invisible
            alignIconsWithBackArrow = false                 // default: false
        }

        // -----------------
        // 1) create screen(s)
        // -----------------

        val screen = screen {

            // set up screen
            state = savedInstanceState
            onScreenChanged = { subScreenStack, stateRestored ->
                val breadcrumbs =
                    subScreenStack.joinToString(" > ") { it.title.get(this@CustomSettingsActivity) }
                L.d { "Preference Screen - level = ${subScreenStack.size} | $breadcrumbs | restored: $stateRestored" }
                supportActionBar?.subtitle = breadcrumbs
            }

            // -----------------
            // create settings screen
            // -----------------

            DemoSettings.createSettingsScreen(this, this@CustomSettingsActivity)

        }
        screen.bind(binding.rvSettings)
        return screen
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        preferenceScreen.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (preferenceScreen.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!preferenceScreen.onBackPressed()) {
            finish()
        }
        return true
    }
}