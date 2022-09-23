package com.michaelflisar.materialpreferences.preferencescreen.slider.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base.BaseSliderViewHolder

class SliderFloatViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    adapter: PreferenceAdapter
) : BaseSliderViewHolder<Float, SliderFloatPreference>(inflater, parent, adapter) {
    override fun convertValueFromSlider(value: Float) = value
    override fun convertValueToSlider(value: Float) = value
}