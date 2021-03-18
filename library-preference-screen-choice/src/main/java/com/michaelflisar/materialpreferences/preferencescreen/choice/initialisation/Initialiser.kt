package com.michaelflisar.materialpreferences.preferencescreen.choice.initialisation

import android.content.Context
import androidx.startup.Initializer
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.choice.multi.MultiChoicePreference
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreference

internal class Initialiser : Initializer<Unit> {
    override fun create(context: Context) {
        ViewHolderFactory.register(SingleChoicePreference.TYPE, SingleChoicePreference.Companion)
        ViewHolderFactory.register(MultiChoicePreference.TYPE, MultiChoicePreference.Companion)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
