package com.michaelflisar.materialpreferences.preferencescreen.slider.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.slider.R
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base.BaseSliderPreference

class SliderFloatPreference(
    setting: StorageSetting<Float>
) : BaseSliderPreference<Float>(setting) {

    override var min = Float.MIN_VALUE
    override var max = Float.MAX_VALUE
    override var stepSize: Float? = null

    override val defaultStepSize: Float? = null

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_seekbar_float
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = SliderFloatViewHolder(
            layoutInflater,
            parent,
            adapter
        )
    }

    override val type = TYPE

}