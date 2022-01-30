package com.michaelflisar.materialpreferences.demo.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.michaelflisar.lumberjack.L
import com.michaelflisar.materialpreferences.demo.DemoSettings
import com.michaelflisar.materialpreferences.demo.databinding.ActivityDemoBinding
import com.michaelflisar.materialpreferences.demo.settings.DemoSettingsModel
import com.michaelflisar.materialpreferences.preferencescreen.activity.SettingsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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
        setSupportActionBar(binding.toolbar)

        binding.btSettingsDefault.setOnClickListener {
            DemoSettings.showDefaultSettingsActivity(this)
        }
        binding.btSettingsCustom.setOnClickListener {
            DemoSettings.showCustomSettingsActivity(this)
        }

        // -----------------------
        // OBSERVE RELEVANT SETTINGS
        // -----------------------

        // we drop the first item (the current state)
        DemoSettingsModel.darkTheme.observe(lifecycleScope, transformer = { it.drop(1) }) {
            recreate = true
            L.d { "darkTheme changed" }
        }

        // -----------------------
        // OBSERVE ALL / MULTIPLE SETTINGS
        // -----------------------

        DemoSettingsModel.changes.onEach {
            L.d { "[ALL SETTINGS OBSERVER] Setting '${it.setting.key}' changed its value to '${it.value}'" }
        }.launchIn(lifecycleScope)

        DemoSettingsModel.changes
                .filter {
                    it.setting == DemoSettingsModel.darkTheme ||
                            it.setting == DemoSettingsModel.testBool
                }.onEach {
                    L.d { "[SOME SETTINGS OBSERVER] Setting '${it.setting.key}' changed its value to '${it.value}'" }
                }.launchIn(lifecycleScope)

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
            val name2 = DemoSettingsModel.childName2.flow.first()
            val name3 = DemoSettingsModel.childName3.flow.first()
            val names = listOf(name1, name2, name3)
            withContext(Dispatchers.Main) {
                binding.tvText1.text = binding.tvText1.text.toString() + "\n[SUSPENDED FLOW READ] Childrens: ${names.joinToString(", ")}"
            }
        }

        // -----------------------
        // Test 5 - nullable / not nullable
        // -----------------------

        lifecycleScope.launch(Dispatchers.IO) {
            DemoSettingsModel.nullableString.update("nullable value value 1")
            DemoSettingsModel.nullableString.update(null)
            DemoSettingsModel.nullableString.update("nullable value value 2")

            // won't compile!
            // DemoSettingsModel.nonNullableString.update(null)
            DemoSettingsModel.nonNullableString.update("non nullable value 1")
            DemoSettingsModel.nonNullableString.update("non nullable value 2")

            DemoSettingsModel.nullableInt.update(1)
            DemoSettingsModel.nullableInt.update(null)
            DemoSettingsModel.nullableInt.update(2)
            DemoSettingsModel.nonNullableInt.update(1)
            DemoSettingsModel.nonNullableInt.update(2)

            DemoSettingsModel.nullableFloat.update(1f)
            DemoSettingsModel.nullableFloat.update(null)
            DemoSettingsModel.nullableFloat.update(2f)
            DemoSettingsModel.nonNullableFloat.update(1f)
            DemoSettingsModel.nonNullableFloat.update(2f)

            DemoSettingsModel.nullableDouble.update(1.0)
            DemoSettingsModel.nullableDouble.update(null)
            DemoSettingsModel.nullableDouble.update(2.0)
            DemoSettingsModel.nonNullableDouble.update(1.0)
            DemoSettingsModel.nonNullableDouble.update(2.0)

            DemoSettingsModel.nullableLong.update(1L)
            DemoSettingsModel.nullableLong.update(null)
            DemoSettingsModel.nullableLong.update(2L)
            DemoSettingsModel.nonNullableLong.update(1L)
            DemoSettingsModel.nonNullableLong.update(2L)

            DemoSettingsModel.nullableBool.update(true)
            DemoSettingsModel.nullableBool.update(null)
            DemoSettingsModel.nullableBool.update(false)
            DemoSettingsModel.nonNullableBool.update(true)
            DemoSettingsModel.nonNullableBool.update(false)
        }

        // -----------------------
        // ...
        // -----------------------

        lifecycleScope.launch(Dispatchers.IO) {
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