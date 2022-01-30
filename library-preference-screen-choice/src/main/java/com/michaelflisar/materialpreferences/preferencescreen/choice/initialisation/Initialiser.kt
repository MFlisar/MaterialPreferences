package com.michaelflisar.materialpreferences.preferencescreen.choice.initialisation

import android.content.Context
import androidx.startup.Initializer
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.choice.multi.MultiChoicePreference
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreference
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreferenceEnum
import com.michaelflisar.materialpreferences.preferencescreen.choice.single.SingleChoicePreferenceInt

internal class Initialiser : Initializer<Unit> {

    override fun create(context: Context) {
        ViewHolderFactory.register(SingleChoicePreferenceInt.TYPE, SingleChoicePreferenceInt.Companion)
        ViewHolderFactory.register(SingleChoicePreferenceEnum.TYPE, SingleChoicePreferenceEnum.Companion)
        ViewHolderFactory.register(MultiChoicePreference.TYPE, MultiChoicePreference.Companion)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
