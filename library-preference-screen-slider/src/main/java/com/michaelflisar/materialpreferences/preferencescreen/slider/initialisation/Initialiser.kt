package com.michaelflisar.materialpreferences.preferencescreen.slider.initialisation

import android.content.Context
import androidx.startup.Initializer
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.SliderDoublePreference
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.SliderFloatPreference
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.SliderIntPreference
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.SliderLongPreference

internal class Initialiser : Initializer<Unit> {

    override fun create(context: Context) {
        ViewHolderFactory.register(SliderIntPreference.TYPE, SliderIntPreference.Companion)
        ViewHolderFactory.register(SliderLongPreference.TYPE, SliderLongPreference.Companion)
        ViewHolderFactory.register(SliderFloatPreference.TYPE, SliderFloatPreference.Companion)
        ViewHolderFactory.register(SliderDoublePreference.TYPE, SliderDoublePreference.Companion)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
