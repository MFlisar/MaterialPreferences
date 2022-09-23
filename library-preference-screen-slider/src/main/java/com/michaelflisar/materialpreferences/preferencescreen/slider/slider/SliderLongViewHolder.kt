package com.michaelflisar.materialpreferences.preferencescreen.slider.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base.BaseSliderViewHolder

class SliderLongViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    adapter: PreferenceAdapter
) : BaseSliderViewHolder<Long, SliderLongPreference>(inflater, parent, adapter) {
    override fun convertValueFromSlider(value: Float) = value.toLong()
    override fun convertValueToSlider(value: Long) = value.toFloat()
}