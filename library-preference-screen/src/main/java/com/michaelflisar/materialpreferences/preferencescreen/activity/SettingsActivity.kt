package com.michaelflisar.materialpreferences.preferencescreen.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.michaelflisar.materialpreferences.preferencescreen.PreferenceScreen
import com.michaelflisar.materialpreferences.preferencescreen.interfaces.IScreenCreator

class SettingsActivity : BaseSettingsActivity() {

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

    lateinit var data: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        data = Data.create(intent)
        super.onCreate(savedInstanceState)
        data.screenProvider.onCreate(this, savedInstanceState)
        finishCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        data.screenProvider.onResume(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        data.screenProvider.onSaveInstanceState(this, outState)
    }

    override fun onDestroy() {
        data.screenProvider.onDestroy(this)
        super.onDestroy()
    }

    override fun createScreen(
        savedInstanceState: Bundle?
    ): PreferenceScreen {
        return data.screenProvider.createScreen(this, savedInstanceState)
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
}