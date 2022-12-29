package com.michaelflisar.materialpreferences.preferencescreen.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreen
import com.michaelflisar.materialpreferences.preferencescreen.databinding.PreferenceActivitySettingsBinding

abstract class BaseSettingsActivity : AppCompatActivity() {

    abstract fun createScreen(
        savedInstanceState: Bundle?
    ): PreferenceScreen

    lateinit var binding: PreferenceActivitySettingsBinding
    lateinit var preferenceScreen: PreferenceScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen = createScreen(savedInstanceState)
    }

    fun finishCreate(savedInstanceState: Bundle?) {
        binding = PreferenceActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        preferenceScreen.bind(binding.rvSettings, this)
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