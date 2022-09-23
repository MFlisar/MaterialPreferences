package com.michaelflisar.materialpreferences.preferencescreen.slider.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base.BaseSliderViewHolder

class SliderIntViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    adapter: PreferenceAdapter
) : BaseSliderViewHolder<Int, SliderIntPreference>(inflater, parent, adapter) {
    override fun convertValueFromSlider(value: Float) = value.toInt()
    override fun convertValueToSlider(value: Int) = value.toFloat()
}