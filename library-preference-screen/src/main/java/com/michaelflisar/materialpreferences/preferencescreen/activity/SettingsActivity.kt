package com.michaelflisar.materialpreferences.preferencescreen.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreen
import com.michaelflisar.materialpreferences.preferencescreen.databinding.PreferenceActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    companion object {

        private const val KEY_SCREEN_PROVIDER = "KEY_SCREEN_PROVIDER"

        fun start(context: Context, screenProvider: IScreenCreator) {
            val data = Data(screenProvider)
            val intent = Intent(context, SettingsActivity::class.java).apply {
                data.addToIntent(this)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    lateinit var binding: PreferenceActivitySettingsBinding
    lateinit var preferenceScreen: PreferenceScreen
    lateinit var data: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = Data.create(intent)
        //AppCompatDelegate.setDefaultNightMode(if (data.dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        binding = PreferenceActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferenceScreen = data.screenProvider.createScreen(this, savedInstanceState) {
            supportActionBar?.subtitle = it
        }
        preferenceScreen.bind(binding.rvSettings)
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

    // ----------------
    // Classes / Interface
    // ----------------

    data class Data(
        val screenProvider: IScreenCreator
    ) {

        private val KEY_SCREEN_PROVIDER = "KEY_SCREEN_PROVIDER"

        companion object {
            fun create(intent: Intent): Data {
                val screenProvider =
                    intent.getParcelableExtra<IScreenCreator>(KEY_SCREEN_PROVIDER)!!
                return Data(screenProvider)
            }
        }

        fun addToIntent(intent: Intent) {
            intent.apply {
                putExtra(KEY_SCREEN_PROVIDER, screenProvider)
            }
        }
    }

    interface IScreenCreator : Parcelable {
        fun createScreen(
            activity: AppCompatActivity,
            savedInstanceState: Bundle?,
            updateTitle: (title: String) -> Unit
        ): PreferenceScreen
    }
}