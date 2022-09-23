package com.michaelflisar.materialpreferences.preferencescreen.slider.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.classes.Icon
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.slider.R
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base.BaseSliderPreference

class SliderDoublePreference(
    setting: StorageSetting<Double>
) : BaseSliderPreference<Double>(setting) {

    override var min = Double.MIN_VALUE
    override var max = Double.MAX_VALUE
    override var stepSize: Double? = null

    override val defaultStepSize: Double? = null

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_seekbar_double
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = SliderDoubleViewHolder(
            layoutInflater,
            parent,
            adapter
        )
    }

    override val type = TYPE

}