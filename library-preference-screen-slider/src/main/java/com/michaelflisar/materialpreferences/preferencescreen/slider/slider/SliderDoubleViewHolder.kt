package com.michaelflisar.materialpreferences.preferencescreen.slider.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base.BaseSliderViewHolder

class SliderDoubleViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    adapter: PreferenceAdapter
) : BaseSliderViewHolder<Double, SliderDoublePreference>(inflater, parent, adapter) {
    override fun convertValueFromSlider(value: Float) = value.toDouble()
    override fun convertValueToSlider(value: Double) = value.toFloat()
}