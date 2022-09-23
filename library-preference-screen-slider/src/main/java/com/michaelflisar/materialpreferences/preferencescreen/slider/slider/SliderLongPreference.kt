package com.michaelflisar.materialpreferences.preferencescreen.slider.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.preferencescreen.ViewHolderFactory
import com.michaelflisar.materialpreferences.preferencescreen.recyclerview.PreferenceAdapter
import com.michaelflisar.materialpreferences.preferencescreen.slider.R
import com.michaelflisar.materialpreferences.preferencescreen.slider.slider.base.BaseSliderPreference

class SliderLongPreference(
    setting: StorageSetting<Long>
) : BaseSliderPreference<Long>(setting) {

    override var min = Long.MIN_VALUE
    override var max = Long.MAX_VALUE
    override var stepSize: Long? = null

    override val defaultStepSize = 1L

    companion object : ViewHolderFactory.ViewHolderCreator {
        val TYPE = R.id.pref_seekbar_long
        override fun createViewHolder(
            adapter: PreferenceAdapter,
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = SliderLongViewHolder(
            layoutInflater,
            parent,
            adapter
        )
    }

    override val type = TYPE

}