package com.michaelflisar.materialpreferences.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.michaelflisar.materialpreferences.demo.databinding.ActivityDemoBinding
import com.michaelflisar.materialpreferences.demo.settings.DemoSettingsModel
import com.michaelflisar.lumberjack.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.fixedRateTimer

class DemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityDemoBinding

    private var recreate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(if (DemoSettingsModel.darkTheme.value) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityDemoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btSettings.setOnClickListener { SettingsActivity.start(this) }

        // -----------------------
        // OBSERVE RELEVANT SETTINGS
        // -----------------------

        // we drop the first item (the current state)
        DemoSettingsModel.darkTheme.observe(lifecycleScope, transformer = { it.drop(1) }) {
            recreate = true
            L.d { "darkTheme changed" }
        }

        // -----------------------
        // NOT RECOMMENDED - only use this if really necessary
        // Test 1 - read single setting in a blocking way
        // -----------------------

        val test = DemoSettingsModel.test.value
        binding.tvText1.text = "[BLOCKING READ] test = $test"

        // -----------------------
        // Test 2 - read setting ONCE in a non blocking way
        // -----------------------

        DemoSettingsModel.test.observeOnce(lifecycleScope) {
            binding.tvText1.text = binding.tvText1.text.toString() + "\n[NON BLOCKING READ] test = $it"
        }

        // -----------------------
        // Test 3 - observe setting
        // -----------------------

        DemoSettingsModel.test.observe(lifecycleScope) {
            binding.tvText2.text = binding.tvText2.text.toString() + "\n[FLOW READ] test = $it"
        }

        // -----------------------
        // Test 4 - read from flows directly
        // -----------------------

        lifecycleScope.launch(Dispatchers.IO) {
            val name1 = DemoSettingsModel.childName1.flow.first()
            val name2 = DemoSettingsModel.childName1.flow.first()
            val name3 = DemoSettingsModel.childName1.flow.first()
            val names = listOf(name1, name2, name3)
            withContext(Dispatchers.Main) {
                binding.tvText1.text = binding.tvText1.text.toString() + "\nChildrens: ${names.joinToString(", ")}"
            }
        }

        // -----------------------
        // ...
        // -----------------------

        lifecycleScope.launch {
            DemoSettingsModel.testBool.update(true)
            DemoSettingsModel.testBool.update(false)
            DemoSettingsModel.testBool.update(true)
        }

        startTestUpdater()
    }

    override fun onResume() {
        super.onResume()
        if (recreate) {
            recreate = false
            recreate()
        }
    }

    private fun startTestUpdater() {
        var counter = 0
        fixedRateTimer("timer", false, 0L, 5 * 1000 /* 5 seconds */) {
            counter++
            lifecycleScope.launch {
                DemoSettingsModel.test.update("Timer: $counter")
            }
        }
    }
}